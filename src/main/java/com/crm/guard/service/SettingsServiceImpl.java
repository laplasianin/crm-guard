package com.crm.guard.service;

import com.crm.guard.dao.SettingsDAO;
import com.crm.guard.entity.Settings;
import com.crm.guard.service.api.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class SettingsServiceImpl implements SettingsService {

    @Autowired
    private SettingsDAO settingsDAO;

    private static final String smsLoginCode = "SMS_LOGIN";
    private static final String smsPassCode = "SMS_PASS";
    private static final String integration1cPathCode = "1C_PATH";
    private static final String filesPathCode = "FILES_PATH";

    @Override
    public String getSmsLogin() {
        Settings settings = getSmsLoginSetting();
        if (settings != null) {
            return settings.getValue();
        }
        return "";
    }

    @Override
    public String getSmsPass() {
        Settings settings = getSmsPassSetting();
        if (settings != null) {
            return settings.getValue();
        }
        return "";
    }

    @Override
    public String getIntegrationPath() {
        Settings settings = getIntegration1cPathSetting();
        if (settings != null) {
            return settings.getValue();
        }
        return "";
    }

    @Override
    public String getFilesPath() {
        Settings settings = getFilesPathSetting();
        if (settings != null) {
            return settings.getValue();
        }
        return "";
    }

    public Settings getSmsLoginSetting() {
        return settingsDAO.get(smsLoginCode);
    }

    public Settings getSmsPassSetting() {
        return settingsDAO.get(smsPassCode);
    }

    public Settings getIntegration1cPathSetting() {
        return settingsDAO.get(integration1cPathCode);
    }

    public Settings getFilesPathSetting() {
        return settingsDAO.get(filesPathCode);
    }

    @Override
    @Transactional
    public void saveSmsLogin(String login) {
        Settings smsLoginSetting = getSmsLoginSetting();
        if (smsLoginSetting == null) {
            smsLoginSetting = new Settings();
            smsLoginSetting.setId(smsLoginCode);
            smsLoginSetting.setName("Логин смс");
        }
        smsLoginSetting.setValue(login);
        settingsDAO.saveOrUpdate(smsLoginSetting);
    }

    @Override
    @Transactional
    public void saveSmsPassword(String pass) {
        Settings smsLoginSetting = getSmsPassSetting();
        if (smsLoginSetting == null) {
            smsLoginSetting = new Settings();
            smsLoginSetting.setId(smsPassCode);
            smsLoginSetting.setName("Пароль смс");
        }
        smsLoginSetting.setValue(pass);
        settingsDAO.saveOrUpdate(smsLoginSetting);
    }

    @Override
    @Transactional
    public void saveIntegrationPath(String path) {
        Settings integrationPathSetting = getIntegration1cPathSetting();
        if (integrationPathSetting == null) {
            integrationPathSetting = new Settings();
            integrationPathSetting.setId(integration1cPathCode);
            integrationPathSetting.setName("Путь выгрузки 1с");
        }
        integrationPathSetting.setValue(path);
        settingsDAO.saveOrUpdate(integrationPathSetting);
    }

    @Override
    @Transactional
    public void saveFilesPath(String path) {
        Settings filesPathSetting = getFilesPathSetting();
        if (filesPathSetting == null) {
            filesPathSetting = new Settings();
            filesPathSetting.setId(filesPathCode);
            filesPathSetting.setName("Путь хранения файлов контрагентов");
        }
        filesPathSetting.setValue(path);
        settingsDAO.saveOrUpdate(filesPathSetting);
    }
}
