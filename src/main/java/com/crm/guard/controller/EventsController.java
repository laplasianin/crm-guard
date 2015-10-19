package com.crm.guard.controller;

import com.crm.guard.binder.ClientEditor;
import com.crm.guard.binder.EventTypeEditor;
import com.crm.guard.entity.Client;
import com.crm.guard.entity.EventType;
import com.crm.guard.entity.User;
import com.crm.guard.filter.Validation;
import com.crm.guard.form.event.UserEventFORM;
import com.crm.guard.service.api.ClientService;
import com.crm.guard.service.api.EventTypeService;
import com.crm.guard.service.api.UserEventService;
import com.crm.guard.utils.PrincipalUtils;
import com.crm.guard.validator.base.Messages;
import com.crm.guard.validator.event.UserEventCreateValidator;
import com.crm.guard.webresult.WebResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@Secured(value = {"ROLE_USER"})
@Controller
@RequestMapping("/event")
public class EventsController {

    @Autowired
    private UserEventService userEventService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private EventTypeService eventTypeService;

    @Autowired
    private UserEventCreateValidator eventCreateValidator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(EventType.class, new EventTypeEditor(eventTypeService));
        binder.registerCustomEditor(Client.class, new ClientEditor(clientService));
    }

    @PreAuthorize("hasAnyRole('ROLE_OPERATOR', 'ROLE_ADMIN')")
    @RequestMapping(value = "client", method = RequestMethod.GET)
    @ResponseBody
    public java.util.List<com.crm.guard.entity.UserEvent> getByClient(
            @RequestParam(value = "id") String clientId) {
        return userEventService.getHistory(clientId);
    }

    @PreAuthorize("hasAnyRole('ROLE_OPERATOR', 'ROLE_ADMIN')")
    @RequestMapping(value = "history/client", method = RequestMethod.GET)
    public String tableHistory(@RequestParam(value = "id") String clientId, ModelMap model) {
        model.addAttribute("history", userEventService.getHistory(clientId));
        return "client_history";
    }

    @PreAuthorize("hasAnyRole('ROLE_OPERATOR', 'ROLE_ADMIN')")
    @RequestMapping(value = "new", method = RequestMethod.GET)
    @ResponseBody
    public WebResult newEvent(UserEventFORM form, @Validation Messages messages) {
        WebResult wr = new WebResult();

        final User user = PrincipalUtils.principal();
        form.setUser(user);

        eventCreateValidator.validate(form, messages);

        if (messages.noErrors()) {
            userEventService.newEvent(form);
            wr.success();
        }

        return wr;
    }

}
