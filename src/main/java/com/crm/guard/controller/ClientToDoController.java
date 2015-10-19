package com.crm.guard.controller;

import com.crm.guard.binder.ClientEditor;
import com.crm.guard.binder.ClientToDoEditor;
import com.crm.guard.binder.TimestampEditor;
import com.crm.guard.entity.Client;
import com.crm.guard.entity.ClientToDo;
import com.crm.guard.filter.Validation;
import com.crm.guard.form.clienttodo.NewToDoFORM;
import com.crm.guard.form.clienttodo.UpdateDatesToDoFORM;
import com.crm.guard.form.clienttodo.UpdateTextToDoFORM;
import com.crm.guard.service.api.ClientService;
import com.crm.guard.service.api.ClientToDoService;
import com.crm.guard.validator.base.Messages;
import com.crm.guard.webresult.WebResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@Controller
@RequestMapping("client/calendar")
public class ClientToDoController {

    @Autowired
    private ClientToDoService clientToDoService;

    @Autowired
    private ClientService clientService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Timestamp.class, new TimestampEditor());
        binder.registerCustomEditor(Client.class, new ClientEditor(clientService));
        binder.registerCustomEditor(ClientToDo.class, new ClientToDoEditor(clientToDoService));

    }

    @PreAuthorize("hasAnyRole('ROLE_OPERATOR', 'ROLE_ADMIN')")
    @RequestMapping(value = "new", method = RequestMethod.POST)
    @ResponseBody
    public WebResult event(NewToDoFORM form, @Validation Messages messages) {
        WebResult wr = new WebResult();

        // TODO validate
        if (messages.noErrors()) {
            wr.setData(clientToDoService.save(form));
            wr.success();
            messages.success("Действие успешно выполнено");
            // TODO event
        }

        wr.setMessages(messages);
        return wr;
    }

    @PreAuthorize("hasAnyRole('ROLE_OPERATOR', 'ROLE_ADMIN')")
    @RequestMapping(value = "update_dates", method = RequestMethod.POST)
    @ResponseBody
    public WebResult updateDates(UpdateDatesToDoFORM form, @Validation Messages messages) {
        WebResult wr = new WebResult();

        // TODO validate
        if (messages.noErrors()) {
            clientToDoService.update(form);
            wr.success();
            messages.success("Действие успешно выполнено");
            // TODO event
        }

        wr.setMessages(messages);
        return wr;
    }

    @PreAuthorize("hasAnyRole('ROLE_OPERATOR', 'ROLE_ADMIN')")
    @RequestMapping(value = "update_text", method = RequestMethod.POST)
    @ResponseBody
    public WebResult updateText(UpdateTextToDoFORM form, @Validation Messages messages) {
        WebResult wr = new WebResult();

        // TODO validate
        if (messages.noErrors()) {
            ClientToDo updated = clientToDoService.update(form);
            wr.setData(updated);
            wr.success();
            messages.success("Действие успешно выполнено");
            // TODO event
        }

        wr.setMessages(messages);
        return wr;
    }

    @PreAuthorize("hasAnyRole('ROLE_OPERATOR', 'ROLE_ADMIN')")
    @RequestMapping(value = "finish", method = RequestMethod.POST)
    @ResponseBody
    public WebResult finish(@RequestParam("clientToDo") ClientToDo form, @Validation Messages messages) {
        WebResult wr = new WebResult();

        // TODO validate
        if (messages.noErrors()) {
            ClientToDo finished = clientToDoService.finish(form);
            wr.setData(finished);
            wr.success();
            messages.success("Действие успешно выполнено");
            // TODO event
        }

        wr.setMessages(messages);
        return wr;
    }

    @PreAuthorize("hasAnyRole('ROLE_OPERATOR', 'ROLE_ADMIN')")
    @RequestMapping(value = "get", method = RequestMethod.GET)
    @ResponseBody
    public WebResult get(@RequestParam("client") Client client, @Validation Messages messages) {
        WebResult wr = new WebResult();

        List<ClientToDo> todos = clientToDoService.get(client);

        wr.setData(todos);
        wr.success();
        wr.setMessages(messages);
        return wr;
    }

    @PreAuthorize("hasAnyRole('ROLE_OPERATOR', 'ROLE_ADMIN')")
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public WebResult get(@RequestParam("clientToDo") ClientToDo clientToDo, @Validation Messages messages) {
        WebResult wr = new WebResult();

        // TODO validate
        if (messages.noErrors()) {
            clientToDoService.delete(clientToDo);
            wr.success();
            messages.success("Действие успешно выполнено");
            // TODO event
        }

        wr.setMessages(messages);
        return wr;
    }

}
