package com.crm.guard.filter;

import org.hibernate.criterion.Order;

import java.util.ArrayList;
import java.util.List;

public class SearchFields extends ArrayList<SearchField> {

    public SearchFields add(String field, SearchType restriction, Object value) {
        this.add(new SearchField(field, restriction, value));
        return this;
    }
}
