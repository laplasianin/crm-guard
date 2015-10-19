package com.crm.guard.controller;

import com.crm.guard.binder.TemplateEditor;
import com.crm.guard.entity.Template;
import com.crm.guard.entity.TemplateType;
import com.crm.guard.filter.Filter;
import com.crm.guard.filter.FilterRequest;
import com.crm.guard.filter.Validation;
import com.crm.guard.form.template.TemplateSaveOrUpdateFORM;
import com.crm.guard.service.api.TemplateService;
import com.crm.guard.service.delivery.parse.DeliveryPatterns;
import com.crm.guard.validator.base.Messages;
import com.crm.guard.webresult.WebResult;
import com.crm.guard.webresult.WebResultTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Secured(value = {"ROLE_USER"})
@Controller
@RequestMapping("/templates")
public class TemplateController {

    @Autowired
    private TemplateService templateService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Template.class, new TemplateEditor(templateService));
    }

    // TODO удаление шаблона

    @PreAuthorize("hasAnyRole('ROLE_OPERATOR', 'ROLE_ADMIN')")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public void templates() {}

    @PreAuthorize("hasAnyRole('ROLE_OPERATOR', 'ROLE_ADMIN')")
    @RequestMapping(value = "table/data", method = RequestMethod.GET)
    @ResponseBody
    public WebResultTable<Template> dataTable(@FilterRequest Filter filter) {
        return templateService.findAllWebResult(filter);  // TODO поиск не работает, сортировки нету
    }

    @PreAuthorize("hasAnyRole('ROLE_OPERATOR', 'ROLE_ADMIN')")
    @RequestMapping(value = "all", method = RequestMethod.GET)
    @ResponseBody
    public List<Template> dataTable() {
        return templateService.finAll();
    }

    @PreAuthorize("hasAnyRole('ROLE_OPERATOR', 'ROLE_ADMIN')")
    @RequestMapping(value = "template", method = RequestMethod.GET)
    public void template(@RequestParam(value = "id", required = false) Long id, Model model) {
        model.addAttribute("template", templateService.findById(id));
        model.addAttribute("types", TemplateType.values());
        model.addAttribute("patterns", DeliveryPatterns.values());
    }

    @PreAuthorize("hasAnyRole('ROLE_OPERATOR', 'ROLE_ADMIN')")
    @RequestMapping(value = "save_or_update", method = RequestMethod.POST)
    @ResponseBody
    public WebResult saveOrUpdate(TemplateSaveOrUpdateFORM form, @Validation Messages messages) {
        WebResult wr = new WebResult();
        // TODO validate
        if (messages.noErrors()) {
            Template template = templateService.saveOrUpdate(form);
            wr.success();
            if (form.getEntity() == null) {
                wr.setData(template.getId());
            }
        }
        wr.setMessages(messages);

        return wr;
    }

}
