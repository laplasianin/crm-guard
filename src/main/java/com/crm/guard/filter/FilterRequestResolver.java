package com.crm.guard.filter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Iterator;

public class FilterRequestResolver implements HandlerMethodArgumentResolver {

    private static final String OFFSET = "offset";
    private static final String LIMIT = "limit";
    private static final String SORT = "sort";
    private static final String ORDER = "order";
    private static final String SEARCH = "search";
    private static final String FILTER = "filter";

    @Override
    public boolean supportsParameter(MethodParameter param) {
        FilterRequest tableParamAnnotation = param.getParameterAnnotation(FilterRequest.class);
        return tableParamAnnotation != null;
    }

    @Override
    public Object resolveArgument(MethodParameter param, ModelAndViewContainer container, NativeWebRequest request, WebDataBinderFactory factory) throws Exception {
        FilterRequest tableParamAnnotation = param.getParameterAnnotation(FilterRequest.class);
        if (tableParamAnnotation != null) {
            HttpServletRequest httpRequest = (HttpServletRequest) request.getNativeRequest();

            int offset = Integer.parseInt(httpRequest.getParameter(OFFSET));
            int limit = Integer.parseInt(httpRequest.getParameter(LIMIT));
            String sort = httpRequest.getParameter(SORT);
            String order = httpRequest.getParameter(ORDER);
            String search = httpRequest.getParameter(SEARCH);
            String filterData = httpRequest.getParameter(FILTER);

            SearchFields searchFields = new SearchFields();
            parseSearches(search, searchFields);
            parseSearches(filterData, searchFields);
            SortFields sortFields = parseSorts(sort, order);

            final Filter filter = new Filter();
            filter.setLimit(limit);
            filter.setOffset(offset);
            filter.setSortFields(sortFields);
            filter.setSearchFields(searchFields);
            return filter;
        }

        return WebArgumentResolver.UNRESOLVED;
    }

    private SortFields parseSorts(String sort, String order) {
        SortFields sortFields = new SortFields();
        if (StringUtils.isNotEmpty(sort) && StringUtils.isNotEmpty(order)) {
            sortFields.add(sort, SortType.valueOfCaseInsensitive(order));
        }
        return sortFields;
    }

    private void parseSearches(String search, SearchFields searchFields) throws IOException {

        if (StringUtils.isEmpty(search)) {
            return;
        }

        JsonNode json;
        ObjectMapper mapper = new ObjectMapper();
        try {
            json = mapper.readTree(search);
        } catch (Exception e) {
            searchFields.add("_ALL_", SearchType.LIKE, search);
            return;
        }

        if (!json.fieldNames().hasNext()) {
            searchFields.add("_ALL_", SearchType.LIKE, search);
            return;
        }

        Iterator<String> fields = json.fieldNames();
        while (fields.hasNext()) {
            String field = fields.next();

            JsonNode searchNodes = json.findValue(field);
            Iterator<String> searchesNames = searchNodes.fieldNames();
            while (searchesNames.hasNext()) {
                SearchType searchType = null;
                Object searchValue = null;
                String searchTypeString = searchesNames.next();
                if ("lte".equals(searchTypeString)) {
                    searchType = SearchType.LESS_THAN_OR_EQUAL_TO;
                } else if ("gte".equals(searchTypeString)) {
                    searchType = SearchType.GREATER_THAN_OR_EQUAL_TO;
                } else if ("eq".equals(searchTypeString)) {
                    searchType = SearchType.EQUAL;
                } else if ("cnt".equals(searchTypeString)) {
                    searchType = SearchType.LIKE_CASE_INSENSITIVE;
                } else if ("in".equals(searchTypeString)) {
                    searchType = SearchType.IN;
                } else if ("neq".equals(searchTypeString)) {
                    searchType = SearchType.NOT_EQUAL;
                } else if ("nn".equals(searchTypeString)) {
                    searchType = SearchType.NOT_NULL;
                } else if ("null".equals(searchTypeString)) {
                    searchType = SearchType.NULL;
                }

                JsonNode value = searchNodes.findValue(searchTypeString);
                if (!"_values".equals(searchTypeString)) {
                    try {
                        searchValue = value.textValue();
                    } catch (NumberFormatException e) {}
                } else {
                    if (value.isArray()) {
                        for (JsonNode jsonNode : value) {
                            if (jsonNode.isTextual()) {
                                String val = jsonNode.textValue();
                                if (val.equals("ept")) {
                                    searchFields.add(field, SearchType.NULL, "ept");
                                } else if (val.equals("in")) {
                                    searchFields.add(field, SearchType.IN, val);
                                } else if (val.equals("nept")) {
                                    searchFields.add(field, SearchType.NOT_NULL, "nept");
                                } else {
                                    searchFields.add(field, SearchType.LIKE_CASE_INSENSITIVE, val);
                                }
                            }
                        }
                    } else {
                        String val = value.textValue();
                        if (val.equals("in")) {
                            searchFields.add(field, SearchType.IN, val);
                        } else {
                            searchFields.add(field, SearchType.LIKE_CASE_INSENSITIVE, val);
                        }
                    }
                }

                if (StringUtils.isNotEmpty(field) && searchType != null && searchValue != null) {
                    searchFields.add(field, searchType, searchValue);
                }
            }
        }
    }
}
