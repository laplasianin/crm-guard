package com.crm.guard.service.api;

import com.crm.guard.entity.IntegrationAudit;
import com.crm.guard.entity.User;
import com.crm.guard.exception.IntegrationValidationException;

public interface IntegrationAuditService {

    void start(User user, IntegrationAudit.Type full) throws IntegrationValidationException;
    void finish() throws IntegrationValidationException;
    void terminate(Exception e) throws IntegrationValidationException;
    IntegrationAudit getUncompleted();

    void saveOrUpdate(IntegrationAudit uncompleted);

    boolean hasUncompleted();

    IntegrationAudit getLastCcompleted();

    IntegrationAudit getLastUncompleted();

    IntegrationAudit getLastCompletedOrTerminated();
}