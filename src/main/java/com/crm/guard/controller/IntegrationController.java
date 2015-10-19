package com.crm.guard.controller;

import com.crm.guard.exception.IntegrationValidationException;
import com.crm.guard.service.api.IntegrationAuditService;
import com.crm.guard.service.integration1c.IntegrationFacade;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static com.crm.guard.utils.PrincipalUtils.principal;

//@Secured(value = {"ROLE_ADMIN"})
@Controller
@RequestMapping("/integration")
public class IntegrationController {

    @Autowired
    private IntegrationFacade integration;

    @Autowired
    private IntegrationAuditService integrationService;

    @RequestMapping(value = "run", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void integrationProcess() throws IOException, InvalidFormatException, IntegrationValidationException {
        integration.integrationProcess(principal());
    }

    @RequestMapping(value = "clients", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void clients() throws IOException, InvalidFormatException, IntegrationValidationException {
        integration.clients(principal());
    }

    @RequestMapping(value = "contracts", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void contracts() throws IOException, InvalidFormatException, IntegrationValidationException {
        integration.contracts(principal());
    }

    @RequestMapping(value = "payments", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void payments() throws IOException, InvalidFormatException, IntegrationValidationException {
        integration.payments(principal());
    }

    @RequestMapping(value = "total_invoices", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void totalInvoices() throws IOException, InvalidFormatException, IntegrationValidationException {
        integration.totalInvoices(principal());
    }

    @RequestMapping(value = "invoices", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void invoices() throws IOException, InvalidFormatException, IntegrationValidationException {
        integration.invoices(principal());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String handleNoPermissionException(Exception ex) {
        return ex.getMessage();
    }

    @RequestMapping(value = "check_uncompleted")
    @ResponseBody
    public boolean checkUncompleted() throws IOException, InvalidFormatException, IntegrationValidationException {
        return integrationService.hasUncompleted();
    }

    @RequestMapping(value = "check_last_completed")
    @ResponseBody
    public void checkLast() throws IOException, InvalidFormatException, IntegrationValidationException {
        integrationService.getLastCcompleted();
    }
}
