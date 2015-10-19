package com.crm.guard.form.template;

import com.crm.guard.entity.Template;
import com.crm.guard.entity.TemplateType;
import com.crm.guard.form.base.FORM;

public class TemplateSaveOrUpdateFORM implements FORM {

    private Template entity = new Template();
    private String name;
    private TemplateType type;
    private String template;
    private String subject;

    public Template getEntity() {
        return entity;
    }

    public void setEntity(Template entity) {
        this.entity = entity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TemplateType getType() {
        return type;
    }

    public void setType(TemplateType type) {
        this.type = type;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
