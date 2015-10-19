package com.crm.guard.filter;

import org.hibernate.criterion.Restrictions;

public class SearchField {

    private SearchType restriction;

    private String field;

    private Object value;

    public SearchField(String field, SearchType restriction, Object value) {
        this.restriction = restriction;
        this.field = field;
        this.value = value;
    }

    public SearchType getRestriction() {
        return restriction;
    }

    public void setRestriction(SearchType restriction) {
        this.restriction = restriction;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
