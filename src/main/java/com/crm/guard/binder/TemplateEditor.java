package com.crm.guard.binder;

import com.crm.guard.entity.Template;
import com.crm.guard.service.api.TemplateService;
import org.apache.commons.lang3.StringUtils;

import java.beans.PropertyEditorSupport;

public class TemplateEditor extends PropertyEditorSupport {

    private TemplateService templateService;

    public TemplateEditor(TemplateService templateService) {
        this.templateService = templateService;
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (StringUtils.isEmpty(text)) {
            setValue(null);
            return;
        }
        setValue(templateService.findById(Long.parseLong(text)));
    }

    @Override
    public String getAsText() {
        Template template = (Template) getValue();
        if (template == null) {
            return null;
        } else {
            return String.valueOf(template.getId());
        }
    }
}
