package com.crm.guard.filter;

public class Filter {

    private int limit = Integer.MAX_VALUE;

    private int offset = 0;

    private SearchFields searchFields;

    private SortFields sortFields;

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public SearchFields getSearchFields() {
        return searchFields;
    }

    public void setSearchFields(SearchFields searchFields) {
        this.searchFields = searchFields;
    }

    public SortFields getSortFields() {
        return sortFields;
    }

    public void setSortFields(SortFields sortFields) {
        this.sortFields = sortFields;
    }
}
