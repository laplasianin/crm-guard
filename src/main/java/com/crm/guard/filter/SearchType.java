package com.crm.guard.filter;

import org.hibernate.criterion.Restrictions;

public enum SearchType {

    NULL, NOT_NULL,
    EQUAL, NOT_EQUAL,
    GREATER_THAN, GREATER_THAN_OR_EQUAL_TO,
    LESS_THAN, LESS_THAN_OR_EQUAL_TO,
    LIKE, LIKE_CASE_INSENSITIVE,
    IN, NOT_IN

}