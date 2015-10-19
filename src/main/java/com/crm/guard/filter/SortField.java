package com.crm.guard.filter;

import org.hibernate.annotations.Sort;
import org.hibernate.criterion.Order;

public class SortField {

    private String field;

    private SortType sort;

    public SortField(String field, SortType sort) {
        this.field = field;
        this.sort = sort;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public SortType getSort() {
        return sort;
    }

    public void setSort(SortType sort) {
        this.sort = sort;
    }
}
