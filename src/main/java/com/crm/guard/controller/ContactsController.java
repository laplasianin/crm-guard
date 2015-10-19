package com.crm.guard.controller;

import com.crm.guard.entity.Contact;
import com.crm.guard.filter.Filter;
import com.crm.guard.filter.FilterRequest;
import com.crm.guard.service.api.ContactService;
import com.crm.guard.webresult.WebResultTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Secured(value = {"ROLE_USER"})
@Controller
@RequestMapping("/contacts")
public class ContactsController {

    @Autowired
    private ContactService contactService;

    @PreAuthorize("hasAnyRole('ROLE_OPERATOR', 'ROLE_ADMIN')")
    @RequestMapping(value = "table/data", method = RequestMethod.GET)
    @ResponseBody
    public WebResultTable<Contact> dataTable(@FilterRequest Filter filter, @RequestParam(value = "id") String id) {
        return contactService.findWebResultById(filter, id);
    }

}
