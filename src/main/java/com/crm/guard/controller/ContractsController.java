package com.crm.guard.controller;

import com.crm.guard.service.api.ContractService;
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
@RequestMapping("/contracts")
public class ContractsController {

    @Autowired
    private ContractService contractService;

    @PreAuthorize("hasAnyRole('ROLE_OPERATOR', 'ROLE_ADMIN')")
    @RequestMapping(value = "client", method = RequestMethod.GET)
    public String getByClient(@RequestParam(value = "id") String clientId, ModelMap model) {
        model.addAttribute("contracts", contractService.getContractsByClient(clientId));
        return "client_contracts";
    }

}
