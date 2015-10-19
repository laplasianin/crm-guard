package com.crm.guard.controller;

import com.crm.guard.service.api.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Secured(value = {"ROLE_USER"})
@Controller
@RequestMapping("/invoices")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @PreAuthorize("hasAnyRole('ROLE_OPERATOR', 'ROLE_ADMIN')")
    @RequestMapping(value = "contract", method = RequestMethod.GET)
    public String getByContract(@RequestParam(value = "id") String contractId, ModelMap model) {
        model.addAttribute("invoices", invoiceService.findByContract(contractId));
        return "client_invoices";
    }

}
