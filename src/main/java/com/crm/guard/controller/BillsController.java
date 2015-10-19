package com.crm.guard.controller;

import com.crm.guard.service.api.BillService;
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
@RequestMapping("/bill")
public class BillsController {

    @Autowired
    private BillService billService;

    @PreAuthorize("hasAnyRole('ROLE_OPERATOR', 'ROLE_ADMIN')")
    @RequestMapping(value = "invoice", method = RequestMethod.GET)
    public String getByClient(@RequestParam(value = "id") Long invoiceId, ModelMap model) {
        model.addAttribute("bills", billService.findByInvoiceId(invoiceId));
        return "client_bills";
    }

}
