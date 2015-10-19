package com.crm.guard.service;

import com.crm.guard.dao.IntegrationAuditDAO;
import com.crm.guard.entity.IntegrationAudit;
import com.crm.guard.entity.User;
import com.crm.guard.exception.IntegrationValidationException;
import com.crm.guard.service.api.IntegrationAuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class IntegrationAuditServiceImpl implements IntegrationAuditService {

    @Autowired
    private IntegrationAuditDAO auditDAO;

    @Override
    @Transactional
    public synchronized void start(User user, IntegrationAudit.Type type) throws IntegrationValidationException {
        if (auditDAO.hasUncompleted()) {
            throw new IntegrationValidationException("Уже запущен процесс интеграции");
        }

        IntegrationAudit integrationAudit = new IntegrationAudit();
        integrationAudit.setUser(user);
        integrationAudit.setType(type);

        auditDAO.saveOrUpdate(integrationAudit);
    }

    @Override
    @Transactional
    public void finish() throws IntegrationValidationException {
        List<IntegrationAudit> audit = auditDAO.getUncompleted();

        if (audit.isEmpty()) {
            notFoundIntegration();
        } else if (audit.size() > 1) {
            nonUniqueIntegration(audit);
        } else {
            IntegrationAudit integrationAudit = audit.get(0);
            integrationAudit.setCompleted(new Date());
            auditDAO.saveOrUpdate(integrationAudit);
        }
    }

    private void notFoundIntegration() throws IntegrationValidationException {
        throw new IntegrationValidationException("Нет ни одного запущенного процесса интеграции");
    }

    @Override
    @Transactional
    public void terminate(Exception e) throws IntegrationValidationException {
        List<IntegrationAudit> audit = auditDAO.getUncompleted();

        if (audit.isEmpty()) {
            notFoundIntegration();
        } else if (audit.size() > 1) {
            nonUniqueIntegration(audit);
        } else {
            IntegrationAudit integrationAudit = audit.get(0);
            integrationAudit.setLog(
                    integrationAudit.getLog() + "\r\nАварийиное завершение работы интеграции. Причина: "
                            + e.getMessage() + "\r\n Подробное описание ошибки: "
                            + "\r\n" + Arrays.toString(e.getStackTrace())
            );
            integrationAudit.setTerminated(new Date());
            auditDAO.saveOrUpdate(integrationAudit);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public IntegrationAudit getUncompleted() {
        return auditDAO.getUncompleted().get(0);
    }

    @Override
    @Transactional
    public void saveOrUpdate(IntegrationAudit audit) {
        auditDAO.saveOrUpdate(audit);
    }

    @Override
    public boolean hasUncompleted() {
        return auditDAO.hasUncompleted();
    }

    @Override
    public IntegrationAudit getLastCcompleted() {
        return auditDAO.getLastCompleted();
    }

    @Override
    public IntegrationAudit getLastUncompleted() {
        return auditDAO.getLastUnCompleted();
    }

    @Override
    public IntegrationAudit getLastCompletedOrTerminated() {
        return auditDAO.getLastCompletedOrTerminated();
    }

    private void nonUniqueIntegration(List<IntegrationAudit> audit) {
        String error = "\r\n Интеграция была аварийно завершена, так как было запущено несколько одновременных процессов интеграции";
        for (IntegrationAudit integrationAudit : audit) {
            integrationAudit.setLog(integrationAudit.getLog() + error);
            integrationAudit.setTerminated(new Date());
            auditDAO.saveOrUpdate(integrationAudit);
        }
    }
}
