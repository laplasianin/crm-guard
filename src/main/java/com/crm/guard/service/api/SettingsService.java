package com.crm.guard.service.api;

public interface SettingsService {

    String getSmsLogin();

    String getSmsPass();

    String getIntegrationPath();

    String getFilesPath();

    void saveSmsLogin(String login);

    void saveSmsPassword(String pass);

    void saveIntegrationPath(String path);

    void saveFilesPath(String path);
}