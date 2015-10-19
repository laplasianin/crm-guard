package com.crm.guard.service.delivery.sender.lib;

import com.crm.guard.service.api.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SmscFacade {

    @Autowired
    private SettingsService settingsService;

    public Smscenter getSmsc() {

        String smsLogin = settingsService.getSmsLogin();
        String smsPass = settingsService.getSmsPass();
        return new Smscenter(smsLogin, smsPass);
    }

    public String getError(String raw) {
        int code;
        try {
            code = Math.abs(Integer.parseInt(raw));
        } catch (NumberFormatException e) {
            return raw;
        }

        switch (code) {
            case 1: {
                return "Ошибка в параметрах";
            }
            case 2: {
                return "Неверный логин или пароль";
            }
            case 3: {
                return "Недостаточно средств на счете Клиента";
            }
            case 4: {
                return "IP-адрес временно заблокирован из-за частых ошибок в запросах";
            }
            case 5: {
                return "Неверный формат даты";
            }
            case 6: {
                return "Сообщение запрещено (по тексту или по имени отправителя)";
            }
            case 7: {
                return "Неверный формат номера телефона";
            }
            case 8: {
                return "Сообщение на указанный номер не может быть доставлено";
            }
            case 9: {
                return "Слишком частая отправка одинаковых смс в течение минуты";
            }
        }
        return "?";
    }
}
