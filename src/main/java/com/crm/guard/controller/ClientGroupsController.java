package com.crm.guard.controller;

import com.crm.guard.binder.ClientEditor;
import com.crm.guard.binder.ClientGroupEditor;
import com.crm.guard.entity.Client;
import com.crm.guard.entity.ClientGroup;
import com.crm.guard.filter.Validation;
import com.crm.guard.service.api.ClientGroupService;
import com.crm.guard.service.api.ClientService;
import com.crm.guard.validator.base.Messages;
import com.crm.guard.validator.clientgroup.*;
import com.crm.guard.webresult.WebResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@Secured(value = {"ROLE_USER"})
@Controller
@RequestMapping("/groups")
public class ClientGroupsController {

    @Autowired
    private ClientGroupService clientGroupService;

    @Autowired
    private ClientGroupSaveValidator clientGroupSaveValidator;

    @Autowired
    private ClientGroupUpdateValidator clientGroupUpdateValidator;

    @Autowired
    private ClientGroupRemoveValidator clientGroupRemoveValidator;

    @Autowired
    private ClientGroupAddClientValidator clientGroupAddClientValidator;

    @Autowired
    private ClientGroupRemoveClientValidator clientGroupRemoveClientValidator;

    @Autowired
    private ClientService clientService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(ClientGroup.class, new ClientGroupEditor(clientGroupService));
        binder.registerCustomEditor(Client.class, new ClientEditor(clientService));
    }

    @PreAuthorize("hasAnyRole('ROLE_OPERATOR', 'ROLE_ADMIN')")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String groups(Model model) {
        model.addAttribute("groups", clientGroupService.findAll());
        return "client_groups/groups";
    }

    @PreAuthorize("hasAnyRole('ROLE_OPERATOR', 'ROLE_ADMIN')")
    @RequestMapping(value = "show", method = RequestMethod.GET)
    public String show(@RequestParam String code, Model model) {
        ClientGroup withClients = clientGroupService.findWithClients(code);
        model.addAttribute("group", withClients);
        model.addAttribute("clients", clientService.findAll());
        return "client_groups/group";
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public WebResult save(ClientGroup form, @Validation Messages messages) {
        WebResult wr = new WebResult();

        clientGroupSaveValidator.validate(form, messages);
        if (messages.noErrors()) {
            clientGroupService.saveOrUpdate(form);
            wr.success();
        }
        wr.setMessages(messages);

        return wr;
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    public WebResult update(@RequestParam("code") String code, @RequestParam("description") String description,
                            @Validation Messages messages) {
        WebResult wr = new WebResult();

        ClientGroup updating = clientGroupService.findByCode(code);
        clientGroupUpdateValidator.validate(updating, description, messages);

        if (messages.noErrors()) {
            updating.setDescription(description);
            clientGroupService.saveOrUpdate(updating);
            wr.success();
        }
        wr.setMessages(messages);

        return wr;
    }

    @RequestMapping(value = "remove", method = RequestMethod.POST)
    @ResponseBody
    public WebResult remove(@RequestParam("code") ClientGroup form, @Validation Messages messages) {
        WebResult wr = new WebResult();

        clientGroupRemoveValidator.validate(form, messages);
        if (messages.noErrors()) {
            clientGroupService.remove(form);
            wr.success();
        }
        wr.setMessages(messages);

        return wr;
    }

    @RequestMapping(value = "add_client", method = RequestMethod.POST)
    @ResponseBody
    public WebResult addClient(@RequestParam("client") Client client, @RequestParam("group") ClientGroup group, @Validation Messages messages) {
        WebResult wr = new WebResult();

        clientGroupAddClientValidator.validate(group, client, messages);
        if (messages.noErrors()) {
            boolean added = clientGroupService.addClient(clientGroupService.findWithClients(group.getCode()), client);
            if (!added) {
                messages.error("Клиент уже находится в группе");
            } else {
                wr.success();
            }
        }
        wr.setMessages(messages);

        return wr;
    }

    @RequestMapping(value = "remove_client", method = RequestMethod.POST)
    @ResponseBody
    public WebResult removeClient(@RequestParam("client") Client client, @RequestParam("group") ClientGroup group, @Validation Messages messages) {
        WebResult wr = new WebResult();

        clientGroupRemoveClientValidator.validate(group, client, messages);
        if (messages.noErrors()) {
            boolean added = clientGroupService.removeClient(clientGroupService.findWithClients(group.getCode()), client);
            if (!added) {
                messages.error("Клиент и так не в группе");
            } else {
                wr.success();
            }
        }
        wr.setMessages(messages);

        return wr;
    }

}
