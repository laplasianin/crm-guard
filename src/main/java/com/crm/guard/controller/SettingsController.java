package com.crm.guard.controller;

import com.crm.guard.entity.User;
import com.crm.guard.exception.BadRequestException;
import com.crm.guard.filter.Validation;
import com.crm.guard.service.api.IntegrationAuditService;
import com.crm.guard.service.api.SettingsService;
import com.crm.guard.service.api.UserService;
import com.crm.guard.service.integration1c.IntegrationFacade;
import com.crm.guard.utils.PrincipalUtils;
import com.crm.guard.validator.base.Messages;
import com.crm.guard.webresult.WebResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("/settings")
public class SettingsController {

	@Autowired
	private UserService userService;

	@Autowired
	private IntegrationFacade integration;

	@Autowired
	private IntegrationAuditService integrationService;

	@Autowired
	private SettingsService settingsService;

	@RequestMapping(value = "")
	public void settings(ModelMap model) {
		model.addAttribute("clientsPath", integration.getClientsPath());
		model.addAttribute("contractsPath", integration.getContractsPath());
		model.addAttribute("totalInvoicesPath", integration.getTotalInvoicesPath());
		model.addAttribute("invoicesPath", integration.getInvoicesPath());
		model.addAttribute("paymentsPath", integration.getPaymentsPath());
		model.addAttribute("last", integrationService.getLastCompletedOrTerminated());
		model.addAttribute("lastUncompleted", integrationService.getLastUncompleted());

		model.addAttribute("smsLogin", settingsService.getSmsLogin());
		model.addAttribute("smsPassword", settingsService.getSmsPass());
		model.addAttribute("integration1cPath", settingsService.getIntegrationPath());
		model.addAttribute("filesPath", settingsService.getFilesPath());
	}


	@PreAuthorize("hasAnyRole('ROLE_OPERATOR', 'ROLE_ADMIN')")
	@RequestMapping(value = "change/password", method = RequestMethod.POST)
	@ResponseBody
	public WebResult changePass(@RequestParam("oldPass") String oldPass, @RequestParam("newPass") String newPass, @Validation Messages messages) {
		WebResult wr = new WebResult();
		final User user = PrincipalUtils.principal();

		ShaPasswordEncoder shaPasswordEncoder = new ShaPasswordEncoder();
		if (!shaPasswordEncoder.isPasswordValid(user.getPassword(), oldPass, user.getName())) {
			throw new BadRequestException("Указан неверный пароль");
		}

		user.setPassword(newPass);
		userService.saveUser(user);

		messages.success("Пароль успешно изменен");
		wr.setMessages(messages);
		return wr;
	}

	@PreAuthorize("hasAnyRole('ROLE_OPERATOR', 'ROLE_ADMIN')")
	@RequestMapping(value = "change/sms/login", method = RequestMethod.POST)
	@ResponseBody
	public WebResult saveSmsLogin(@RequestParam("login") String login, @Validation Messages messages) {
		WebResult wr = new WebResult();

		settingsService.saveSmsLogin(login);

		messages.success("Логин успешно изменен");
		wr.setMessages(messages);
		return wr;
	}

	@PreAuthorize("hasAnyRole('ROLE_OPERATOR', 'ROLE_ADMIN')")
	@RequestMapping(value = "change/sms/password", method = RequestMethod.POST)
	@ResponseBody
	public WebResult saveSmsPass(@RequestParam("password") String password, @Validation Messages messages) {
		WebResult wr = new WebResult();

		settingsService.saveSmsPassword(password);

		messages.success("Пароль успешно изменен");
		wr.setMessages(messages);
		return wr;
	}

	@PreAuthorize("hasAnyRole('ROLE_OPERATOR', 'ROLE_ADMIN')")
	@RequestMapping(value = "change/integration/path", method = RequestMethod.POST)
	@ResponseBody
	public WebResult saveIntegrationPath(@RequestParam("path") String path, @Validation Messages messages) {
		WebResult wr = new WebResult();

		File file = new File(path);
		if (!file.exists()) {
			throw new BadRequestException("Некорректный путь");
		}
		if (!file.isDirectory()) {
			throw new BadRequestException("Необходимо задать папку, а не файл");
		}

		settingsService.saveIntegrationPath(path);

		messages.success("Директория выгрузки данных из 1с изменена");
		wr.setMessages(messages);
		return wr;
	}

	@PreAuthorize("hasAnyRole('ROLE_OPERATOR', 'ROLE_ADMIN')")
	@RequestMapping(value = "change/files/path", method = RequestMethod.POST)
	@ResponseBody
	public WebResult saveFilesPath(@RequestParam("path") String path, @Validation Messages messages) {
		WebResult wr = new WebResult();

		File file = new File(path);
		if (!file.exists()) {
			throw new BadRequestException("Некорректный путь");
		}
		if (!file.isDirectory()) {
			throw new BadRequestException("Необходимо задать папку, а не файл");
		}

		settingsService.saveFilesPath(path);

		messages.success("Директория хранения данных успешно изменена");
		wr.setMessages(messages);
		return wr;
	}

	@ExceptionHandler
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public String handleException(Exception ex) throws IOException {
		return ex.getMessage();
	}
	
}
