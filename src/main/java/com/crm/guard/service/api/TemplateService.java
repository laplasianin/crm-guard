package com.crm.guard.service.api;


import com.crm.guard.entity.Template;
import com.crm.guard.filter.Filter;
import com.crm.guard.form.template.TemplateSaveOrUpdateFORM;
import com.crm.guard.webresult.WebResultTable;

import java.util.List;

public interface TemplateService {

    WebResultTable<Template> findAllWebResult(Filter filter);

    Template findById(Long id);

    Template saveOrUpdate(TemplateSaveOrUpdateFORM form);

    List<Template> finAll();
}
