package com.crm.guard.service.integration1c.util;

import com.crm.guard.entity.AbstractEntity;
import com.crm.guard.entity.IntegrationAudit;
import com.crm.guard.service.api.IntegrationAuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class Logger {

    @Autowired
    private IntegrationAuditService integrationAuditService;

    @Transactional
    public void logFinish(String message) {
        IntegrationAudit uncompleted = integrationAuditService.getUncompleted();
        uncompleted.addToLog(message);
        integrationAuditService.saveOrUpdate(uncompleted);
    }

    @Transactional
    public void logStart(String message) {
        IntegrationAudit uncompleted = integrationAuditService.getUncompleted();
        uncompleted.addToLog(message);
        integrationAuditService.saveOrUpdate(uncompleted);
    }

    @Transactional
    public void log(String message, Result<AbstractEntity> extract) {
        IntegrationAudit uncompleted = integrationAuditService.getUncompleted();
        uncompleted.addToLog(message);
        uncompleted.addToLog(extract.getLog().toString());
        integrationAuditService.saveOrUpdate(uncompleted);

    }
}
