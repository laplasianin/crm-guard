package com.crm.guard.controller;

import com.crm.guard.service.api.ClientGroupService;
import com.crm.guard.service.api.EntityService;
import com.crm.guard.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @Autowired
    private EntityService entityService;

    @Autowired
    private ClientGroupService clientGroupService;

    @Autowired
    private UserService userService;

	@RequestMapping("/")
	public String showClients(ModelMap model) {
        model.addAttribute("entities", entityService.findAll());
        model.addAttribute("groups", clientGroupService.findAll());
        model.addAttribute("operators", userService.findAll());
		return "index";
	}
}
