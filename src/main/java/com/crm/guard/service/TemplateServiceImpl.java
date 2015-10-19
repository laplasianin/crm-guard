package com.crm.guard.service;

import com.crm.guard.dao.TemplateDAO;
import com.crm.guard.entity.Template;
import com.crm.guard.filter.Filter;
import com.crm.guard.form.template.TemplateSaveOrUpdateFORM;
import com.crm.guard.service.api.TemplateService;
import com.crm.guard.webresult.WebResultTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TemplateServiceImpl implements TemplateService {

    @Autowired
    private TemplateDAO templateDAO;

    @Override
    @Transactional(readOnly = true)
    public WebResultTable<Template> findAllWebResult(Filter filter) {
        List<Template> templates = templateDAO.find(filter);
        return new WebResultTable<Template>(templates, templateDAO.count(filter));
    }

    @Override
    @Transactional(readOnly = true)
    public Template findById(Long id) {
        return templateDAO.get(id);
    }

    @Override
    @Transactional
    public Template saveOrUpdate(TemplateSaveOrUpdateFORM form) {
        Template template = form.getEntity();
        if (template == null) {
            template = new Template();
        }

        template.setName(form.getName());
        template.setSubject(form.getSubject());
        template.setTemplate(form.getTemplate());

        if (template.getId() == null) {
            template.setType(form.getType());
        }
        templateDAO.saveOrUpdate(template);
        return template;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Template> finAll() {
        return templateDAO.getAll();
    }
}
