package com.crm.guard.service.delivery.parse;

public enum DeliveryPatterns {

    INN("client_inn", "ИНН"),
    ClientShortName("client_shortName", "Краткое наименование организации"),
    ContactFullName("contact_fullName", "ФИО ответственного лица");

    private final String path;
    private final String description;

    DeliveryPatterns(String path, String name) {
        this.path = path;
        this.description = name;
    }

    public String getPath() {
        return path;
    }

    public String getDescription() {
        return description;
    }

    public DeliveryEntity getEntityType() {
        return DeliveryEntity.valueOf(this.path.split("_")[0]);
    }

    public String getEntityField() {
        return this.path.split("_")[1];
    }

}
