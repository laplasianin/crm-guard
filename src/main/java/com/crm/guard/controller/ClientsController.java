package com.crm.guard.controller;

import com.crm.guard.entity.Client;
import com.crm.guard.filter.Filter;
import com.crm.guard.filter.FilterRequest;
import com.crm.guard.filter.Validation;
import com.crm.guard.service.api.ClientService;
import com.crm.guard.service.integration1c.integrator.ClientsIntegration;
import com.crm.guard.service.integration1c.extractor.xls.ClientsXlsExtractorImpl;
import com.crm.guard.validator.base.Messages;
import com.crm.guard.webresult.WebResult;
import com.crm.guard.webresult.WebResultTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Secured(value = {"ROLE_USER"})
@Controller
@RequestMapping("/clients")
public class ClientsController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private ClientsIntegration clientsIntegration;

    @Autowired
    private ClientsXlsExtractorImpl clientsExtractor;

    @PreAuthorize("hasAnyRole('ROLE_OPERATOR', 'ROLE_ADMIN')")
    @RequestMapping(value = "table/data", method = RequestMethod.GET)
    @ResponseBody
    public WebResultTable<Client> dataTable(@FilterRequest Filter filter) {
        return clientService.findAllWebResult(filter);
    }

    @PreAuthorize("hasAnyRole('ROLE_OPERATOR', 'ROLE_ADMIN')")
    @RequestMapping(value = "event", method = RequestMethod.POST)
    @ResponseBody
    public WebResult event(@Validation Messages messages) {
        WebResult wr = new WebResult();
        wr.success();
        messages.success("Действие успешно выполнено");

        // TODO event

        wr.setMessages(messages);
        return wr;
    }
}
