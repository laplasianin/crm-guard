package com.crm.guard.filter;

public enum SortType {

    ASC("asc"), DESC("desc");

    private final String direction;

    SortType(String direction) {
        this.direction = direction;
    }

    public String getDirection() {
        return direction;
    }

    public static SortType valueOfCaseInsensitive(String value) {
        String valueUpper = value.toUpperCase();
        return SortType.valueOf(valueUpper);
    }
}