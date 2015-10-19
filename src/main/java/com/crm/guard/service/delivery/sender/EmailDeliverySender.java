package com.crm.guard.service.delivery.sender;

import com.crm.guard.entity.Client;
import com.crm.guard.entity.User;
import com.crm.guard.exception.DeliverySendException;
import com.crm.guard.exception.WrongDataBaseValueException;
import com.crm.guard.form.base.FORM;
import com.crm.guard.form.delivery.DeliveryForm;
import com.crm.guard.form.delivery.message.EmailMessageForm;
import com.crm.guard.form.delivery.message.MessageForm;
import com.crm.guard.form.event.UserEventFORM;
import com.crm.guard.service.api.EventTypeService;
import com.crm.guard.service.api.UserEventService;
import com.crm.guard.service.api.UserService;
import com.crm.guard.utils.PrincipalUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;
import java.util.Date;

@Component("EMAIL")
public class EmailDeliverySender implements DeliverySender {

    @Autowired
    private JavaMailSender mailSender;
    
    @Autowired
    private UserService userService;

    @Value("${email.from}")
    private String emailFrom;

    @Autowired
    private EventTypeService eventTypeService;

    @Autowired
    private UserEventService userEventService;

    private static final Logger logger = LoggerFactory.getLogger(EmailDeliverySender.class);

    @Override
    public void populateForm(MessageForm form) throws WrongDataBaseValueException {
        final Client client = form.getClient();

        if (StringUtils.isEmpty(client.getEmail())) {
            throw new WrongDataBaseValueException("У клиента " + client.getFullName() + " не указан адрес электронной почты");
        }
        if (StringUtils.isEmpty(emailFrom)) {
            throw new WrongDataBaseValueException("Не указан имейл отправителя, обратитесь к администратору системы");
        }

        EmailMessageForm email = (EmailMessageForm) form;
        email.setFrom(emailFrom);
        email.setTo(client.getEmail());
    }

    @Override
    public void send(DeliveryForm form) throws DeliverySendException, WrongDataBaseValueException {
        for (MessageForm messageForm : form.getMessages()) {
            populateForm(messageForm);
            sendEmail(messageForm);
        }
    }

    @Override
    public void saveEvents(DeliveryForm form) {
        UserEventFORM userEventFORM = (UserEventFORM) getEventForm();
        for (MessageForm messageForm : form.getMessages()) {
            Client client = messageForm.getClient();

            userEventFORM.setClient(client);
            userEventService.newEvent(userEventFORM);
        }
    }

    private FORM getEventForm() {
        UserEventFORM userEventFORM = new UserEventFORM();
        userEventFORM.setEventType(eventTypeService.findById(eventTypeService.getEmailCode()));
        userEventFORM.setEventDate(new Date());
        User principal = PrincipalUtils.principal();
        userEventFORM.setUser(principal == null ? userService.system() : principal);
        userEventFORM.setDescription("Отправка Email");
        return userEventFORM;
    }

    @Override
    public MessageForm getBlankForm() {
        return EmailMessageForm.newBuilder().build();
    }

    private void sendEmail(MessageForm form) throws DeliverySendException {
        EmailMessageForm email = (EmailMessageForm) form;

        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
            helper.setFrom(email.getFrom());
            helper.setTo(email.getTo());
            helper.setSubject(email.getSubject());
            helper.setText(email.getBody(), true);
            mailSender.send(message);
        } catch (Exception e) {
            logger.error("Error during sending email");
            throw new DeliverySendException("Error during sending email");
        }
    }
}
