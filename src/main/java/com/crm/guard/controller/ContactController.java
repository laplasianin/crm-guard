package com.crm.guard.controller;

import com.crm.guard.binder.ClientEditor;
import com.crm.guard.binder.ContactEditor;
import com.crm.guard.binder.EventTypeEditor;
import com.crm.guard.entity.*;
import com.crm.guard.exception.DeliverySendException;
import com.crm.guard.exception.WrongDataBaseValueException;
import com.crm.guard.filter.Validation;
import com.crm.guard.form.contact.ContactMasterFORM;
import com.crm.guard.form.contact.ContactUpdateFORM;
import com.crm.guard.form.event.UserEventFORM;
import com.crm.guard.service.api.*;
import com.crm.guard.utils.PrincipalUtils;
import com.crm.guard.validator.base.Messages;
import com.crm.guard.validator.contact.ContactRemoveValidator;
import com.crm.guard.validator.contact.ContactUpdateValidator;
import com.crm.guard.validator.delivery.CustomDeliveryValidator;
import com.crm.guard.validator.event.UserEventCreateValidator;
import com.crm.guard.webresult.WebResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Secured(value = {"ROLE_USER"})
@Controller
@RequestMapping("/contact")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private EventTypeService eventTypeService;

    @Autowired
    private UserEventService userEventService;

    @Autowired
    private ContactUpdateValidator contactUpdateValidator;

    @Autowired
    private ContactRemoveValidator contactRemoveValidator;

    @Autowired
    private UserEventCreateValidator userEventCreateValidator;

    @Autowired
    private CustomDeliveryValidator customDeliveryValidator;

    @Autowired
    private DeliveryFacade deliveryFacade;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(EventType.class, new EventTypeEditor(eventTypeService));
        binder.registerCustomEditor(Client.class, new ClientEditor(clientService));
        binder.registerCustomEditor(Contact.class, new ContactEditor(contactService));
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @PreAuthorize("hasAnyRole('ROLE_OPERATOR', 'ROLE_ADMIN')")
    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public String editContact(@RequestParam(value = "client_id") String clientId, @RequestParam(value = "id", required = false) Long contactId, Model model) {
        Contact contact = null;
        if (contactId != null) {
            contact = contactService.getContactById(contactId);
        }
        model.addAttribute("client", clientService.findById(clientId));
        model.addAttribute("contact", contact);
        return "contact_edit";
    }

    @PreAuthorize("hasAnyRole('ROLE_OPERATOR', 'ROLE_ADMIN')")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String getContact(@RequestParam(value = "client_id") String clientId, @RequestParam(value = "id", required = false) Long contactId, Model model) {
        Contact contact = null;
        if (contactId != null) {
            contact = contactService.getContactById(contactId);
        }
        model.addAttribute("client", clientService.findById(clientId));
        model.addAttribute("contact", contact);
        return "contact";
    }


    @PreAuthorize("hasAnyRole('ROLE_OPERATOR', 'ROLE_ADMIN')")
    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    public WebResult saveOrUpdate(@ModelAttribute ContactUpdateFORM form, @Validation Messages messages) {
        WebResult wr = new WebResult();
        final User user = PrincipalUtils.principal();
        form.setChanger(user);
        contactUpdateValidator.validate(form, messages);

        if (messages.noErrors()) {
            try {
                contactService.saveOrUpdate(form);
                wr.success();
            } catch (Exception e) {
                messages.error(e.toString());
            }
        }

        // TODO event

        wr.setMessages(messages);
        return wr;
    }

    @PreAuthorize("hasAnyRole('ROLE_OPERATOR', 'ROLE_ADMIN')")
    @RequestMapping(value = "set_master", method = RequestMethod.POST)
    @ResponseBody
    public WebResult setMaster(@ModelAttribute ContactMasterFORM form, @Validation Messages messages) {
        WebResult wr = new WebResult();
        final User user = PrincipalUtils.principal();
        form.setChanger(user);
//        contactUpdateValidator.validate(form, messages); // TODO validate and event

        if (messages.noErrors()) {
            try {
                contactService.setMaster(form);
                wr.success();
                messages.success("Действие успешно совершено");
            } catch (Exception e) {
                messages.error(e.toString());
            }
        }

        wr.setMessages(messages);
        return wr;
    }

    @PreAuthorize("hasAnyRole('ROLE_OPERATOR', 'ROLE_ADMIN')")
    @RequestMapping(value = "action/save", method = RequestMethod.POST)
    @ResponseBody
    public WebResult saveAction(@ModelAttribute UserEventFORM form, @Validation Messages messages) {
        WebResult wr = new WebResult();
        final User user = PrincipalUtils.principal();
        form.setUser(user);
        form.setEventDate(new Date());
        userEventCreateValidator.validate(form, messages);
        if (messages.noErrors()) {
            try {
                if (eventTypeService.getEmailCode().equals(form.getEventType().getCode())) {
                    sendMessage(form, TemplateType.EMAIL, messages, wr);
                } else if (eventTypeService.getSmsCode().equals(form.getEventType().getCode())) {
                    sendMessage(form, TemplateType.SMS, messages, wr);
                }

                userEventService.newEvent(form);
                wr.success();
                messages.success("Действие успешно совершено");
            } catch (Exception e) {
                messages.error(e.toString());
            }
        }
        wr.setMessages(messages);
        return wr;
    }

    private void sendMessage(UserEventFORM form, TemplateType type, Messages messages, WebResult wr) throws NoSuchFieldException, WrongDataBaseValueException, DeliverySendException, IllegalAccessException {
        final Template template = new Template();
        template.setSubject("Оповещение системы ГАРД-НН");
        template.setTemplate(form.getDescription());
        template.setType(type);

        customDeliveryValidator.validate(template, messages);
        if (messages.noErrors()) {
            List<Client> clients = new ArrayList<Client>(1);
            clients.add(form.getClient());
            if (messages.noErrors()) {
                deliveryFacade.send(clients, template);
                messages.success("Сообщение успешно разослано");
                wr.success();
            }
        }
        wr.setMessages(messages);
    }

    /*
    @Secured(value = {"ROLE_OPERATOR, ROLE_MANAGER"})
    @RequestMapping(value = "remove", method = RequestMethod.GET)
    @ResponseBody
    public WebResult remove(ContactRemoveFORM form, @Validation Messages messages, Principal principal) {
        WebResult wr = new WebResult();
        final User user = userService.findById(principal.getName());

        contactRemoveValidator.validate(form, messages);

        if (messages.noErrors()) {
            // TODO user event
            contactService.remove(form);
            wr.success();
        }

        wr.setMessages(messages);
        return wr;
    }
    */

}
