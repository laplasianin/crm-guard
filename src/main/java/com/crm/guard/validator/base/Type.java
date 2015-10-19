package com.crm.guard.validator.base;

public enum Type {

    error("Ошибка"),
    warning("Предупреждение"),
    info("Информация"),
    success("Успех");

    private final String description;

    Type(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
