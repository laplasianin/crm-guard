package com.crm.guard.controller;

import com.crm.guard.binder.ClientEditor;
import com.crm.guard.binder.ClientToDoEditor;
import com.crm.guard.binder.TimestampEditor;
import com.crm.guard.entity.Client;
import com.crm.guard.entity.ClientToDo;
import com.crm.guard.service.api.ClientService;
import com.crm.guard.service.api.ClientToDoService;
import com.crm.guard.utils.PrincipalUtils;
import com.crm.guard.webresult.WebResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Timestamp;

@Controller
@RequestMapping("todo_by_operator")
public class ClientToDoByOperatorController {

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
    @RequestMapping(value = "count", method = RequestMethod.GET)
    @ResponseBody
    public WebResult byOperatorCount() {
        WebResult wr = new WebResult();

        long count = clientToDoService.byOperator();
        wr.setData(count);
        wr.success();

        return wr;
    }

    @PreAuthorize("hasAnyRole('ROLE_OPERATOR', 'ROLE_ADMIN')")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String byOperator(ModelMap model) {
        model.addAttribute("todos", clientToDoService.get(PrincipalUtils.principal()));
        return "client_todo/by_operator";  // TODO сделать таблицу покрасивее
    }

}
