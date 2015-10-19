package com.crm.guard.controller;

import com.crm.guard.binder.TemplateEditor;
import com.crm.guard.entity.Client;
import com.crm.guard.entity.Template;
import com.crm.guard.entity.TemplateType;
import com.crm.guard.exception.DeliverySendException;
import com.crm.guard.exception.OperationNotSupportedException;
import com.crm.guard.exception.WrongDataBaseValueException;
import com.crm.guard.filter.Filter;
import com.crm.guard.filter.FilterRequest;
import com.crm.guard.filter.Validation;
import com.crm.guard.form.template.TemplateSaveOrUpdateFORM;
import com.crm.guard.service.api.ClientService;
import com.crm.guard.service.api.DeliveryFacade;
import com.crm.guard.service.api.TemplateService;
import com.crm.guard.service.delivery.parse.DeliveryPatterns;
import com.crm.guard.validator.base.Messages;
import com.crm.guard.validator.clients.ClientsNotEmptyValidator;
import com.crm.guard.validator.delivery.CustomDeliveryValidator;
import com.crm.guard.validator.delivery.TemplateExistsValidator;
import com.crm.guard.webresult.WebResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Secured(value = {"ROLE_USER"})
@Controller
@RequestMapping("/delivery")
public class DeliveryController {

    @Autowired
    private TemplateService templateService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private DeliveryFacade deliveryFacade;

    @Autowired
    private TemplateExistsValidator templateExistsValidator;

    @Autowired
    private CustomDeliveryValidator customDeliveryValidator;

    @Autowired
    private ClientsNotEmptyValidator clientsNotEmptyValidator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Template.class, new TemplateEditor(templateService));
    }

    @PreAuthorize("hasAnyRole('ROLE_OPERATOR', 'ROLE_ADMIN')")
    @RequestMapping(value = "send_custom", method = RequestMethod.POST)
    @ResponseBody
    public WebResult send(@RequestParam("subject") String subject,
                          @RequestParam("text") String text,
                          @RequestParam("type") TemplateType type,
                          @FilterRequest Filter filter,
                          @RequestParam("clients") String clientsIds,
                          @Validation Messages messages) throws NoSuchFieldException, WrongDataBaseValueException, DeliverySendException, IllegalAccessException {
        WebResult wr = new WebResult();

        final Template template = new Template();
        template.setSubject(subject);
        template.setTemplate(text);
        template.setType(type);

        customDeliveryValidator.validate(template, messages);
        if (messages.noErrors()) {
            List<Client> clients = getClients(filter, clientsIds);

            clientsNotEmptyValidator.validate(clients, messages);
            if (messages.noErrors()) {
                    deliveryFacade.send(clients, template);
                    messages.success("Сообщения успешно разосланы " + clients.size() + " клиентам");
                    wr.success();
            }
        }
        wr.setMessages(messages);
        return wr;
    }

    @PreAuthorize("hasAnyRole('ROLE_OPERATOR', 'ROLE_ADMIN')")
    @RequestMapping(value = "send", method = RequestMethod.POST)
    @ResponseBody
    public WebResult send(@RequestParam("template") Template template,
                          @FilterRequest Filter filter,
                          @RequestParam("clients") String clientsIds,
                          @Validation Messages messages) throws NoSuchFieldException, WrongDataBaseValueException, DeliverySendException, IllegalAccessException {
        WebResult wr = new WebResult();
        templateExistsValidator.validate(template, messages);
        if (messages.noErrors()) {
            List<Client> clients = getClients(filter, clientsIds);

            clientsNotEmptyValidator.validate(clients, messages);
            if (messages.noErrors()) {
                deliveryFacade.send(clients, template);
                messages.success("Сообщения успешно разосланы " + clients.size() + " клиентам");
                wr.success();
            }
        }
        wr.setMessages(messages);
        return wr;
    }

    private List<Client> getClients(Filter filter, String clientsIds) {
        List<Client> clients =  clientService.findByIds(clientsIds);
        if (CollectionUtils.isEmpty(clients)) {
            clients.addAll(clientService.findAllWebResult(filter).getRows());
        }
        return clients;
    }

    @PreAuthorize("hasAnyRole('ROLE_OPERATOR', 'ROLE_ADMIN')")
    @RequestMapping(value = "all", method = RequestMethod.GET)
    @ResponseBody
    public List<Template> dataTable() {
        return templateService.finAll();
    }

    @PreAuthorize("hasAnyRole('ROLE_OPERATOR', 'ROLE_ADMIN')")
    @RequestMapping(value = "template", method = RequestMethod.GET)
    public void template(@RequestParam(value = "id", required = false) Long id, Model model) {
        model.addAttribute("template", templateService.findById(id));
        model.addAttribute("types", TemplateType.values());
        model.addAttribute("patterns", DeliveryPatterns.values());
    }

    @PreAuthorize("hasAnyRole('ROLE_OPERATOR', 'ROLE_ADMIN')")
    @RequestMapping(value = "save_or_update", method = RequestMethod.POST)
    @ResponseBody
    public WebResult saveOrUpdate(TemplateSaveOrUpdateFORM form, @Validation Messages messages) {
        WebResult wr = new WebResult();
        // TODO validate
        if (messages.noErrors()) {
            Template template = templateService.saveOrUpdate(form);
            wr.success();
            if (form.getEntity() == null) {
                wr.setData(template.getId());
            }
        }
        wr.setMessages(messages);

        return wr;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String handleDeliverySendException(DeliverySendException ex, HttpServletResponse response) throws IOException {
        return ex.getMessage();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String handleWrongDataBaseValueException(WrongDataBaseValueException ex, HttpServletResponse response) throws IOException {
        return ex.getMessage();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String handleOperationNotSupportedException(OperationNotSupportedException ex, HttpServletResponse response) throws IOException {
        return ex.getMessage();
    }

}
