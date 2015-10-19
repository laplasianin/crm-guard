package com.crm.guard.controller;

import com.crm.guard.binder.ClientEditor;
import com.crm.guard.entity.Client;
import com.crm.guard.filter.Validation;
import com.crm.guard.form.UploadedFile;
import com.crm.guard.service.api.ClientService;
import com.crm.guard.service.api.ContractService;
import com.crm.guard.service.api.FileService;
import com.crm.guard.service.api.OperatorService;
import com.crm.guard.validator.base.Messages;
import com.crm.guard.validator.operator.KickFromWorkValidator;
import com.crm.guard.validator.operator.TakeToWorkValidator;
import com.crm.guard.webresult.WebResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.util.Iterator;

@Secured(value = {"ROLE_USER"})
@Controller
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private OperatorService operatorService;

    @Autowired
    private FileService fileService;

    @Autowired
    TakeToWorkValidator takeToWorkValidator;

    @Autowired
    private ContractService contractService;

    @Autowired
    KickFromWorkValidator kickFromWorkValidator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Client.class, new ClientEditor(clientService));
    }

    @PreAuthorize("hasAnyRole('ROLE_OPERATOR', 'ROLE_ADMIN')")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String client(@RequestParam(value = "id") String clientId, Model model) {
        Client client = clientService.findWithOperator(clientId);
        model.addAttribute("client", client);
        model.addAttribute("myClient", operatorService.isMyClient(client));
        model.addAttribute("disabledContractMessage", contractService.getDisabledString(client));
        return "client";
    }

    @PreAuthorize("hasAnyRole('ROLE_OPERATOR', 'ROLE_ADMIN')")
    @RequestMapping(value = "file/upload", method = RequestMethod.POST)
    @ResponseBody
    public WebResult upload(@RequestParam(value = "client") Client client, MultipartHttpServletRequest request,  @Validation Messages messages) {
        WebResult wr = new WebResult();
        UploadedFile uploadedFile = new UploadedFile();

        Iterator<String> itr =  request.getFileNames();
        MultipartFile multipartFile = request.getFile(itr.next());

        uploadedFile.setClient(client);
        uploadedFile.setName(multipartFile.getOriginalFilename());
        uploadedFile.setFile(multipartFile);

        try {
            fileService.saveFile(uploadedFile);
            messages.success("Файл " + uploadedFile.getName() +" успешно сохранен");
            wr.success();
        } catch (IOException e) {
            messages.error("Ошибка при сохранении файла: " + e.getMessage());
        }

        wr.setMessages(messages);
        return wr;

    }

    @PreAuthorize("hasAnyRole('ROLE_OPERATOR', 'ROLE_ADMIN')")
    @RequestMapping(value = "take_to_work", method = RequestMethod.POST)
    @ResponseBody
    public WebResult takeToWork(@RequestParam(value = "id") String clientId, @Validation Messages messages) {
        WebResult wr = new WebResult();

        Client client = clientService.findWithOperator(clientId);
        takeToWorkValidator.validate(client, messages);

        if (messages.noErrors()) {
            operatorService.takeToWork(client);
            wr.success();
        }

        wr.setMessages(messages);
        return wr;

    }

    @PreAuthorize("hasAnyRole('ROLE_OPERATOR', 'ROLE_ADMIN')")
    @RequestMapping(value = "kick_from_work", method = RequestMethod.POST)
    @ResponseBody
    public WebResult kickFromWork(@RequestParam(value = "id") String clientId, @Validation Messages messages) {
        WebResult wr = new WebResult();

        Client client = clientService.findWithOperator(clientId);
        kickFromWorkValidator.validate(client, messages);

        if (messages.noErrors()) {
            operatorService.kickFromWork(client);
            wr.success();
        }

        wr.setMessages(messages);
        return wr;
    }

    @PreAuthorize("hasAnyRole('ROLE_OPERATOR', 'ROLE_ADMIN')")
    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    public WebResult update(
            @RequestParam(value = "mobileNumber") String mobile,
            @RequestParam(value = "email") String email,
            @RequestParam(value = "disabled", required = false) Boolean disabled,
            @RequestParam(value = "id") Client client,
            @Validation Messages messages) {

        WebResult wr = new WebResult();

        client.setEmail(email);
        client.setMobileNumber(mobile);
        client.setDisabled(disabled);
        clientService.saveOrUpdate(client);
        wr.success();
        messages.success("Данные клиента обновлены");
        wr.setMessages(messages);
        return wr;
    }


}
