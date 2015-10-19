package com.crm.guard.service.integration1c;

import com.crm.guard.entity.IntegrationAudit;
import com.crm.guard.entity.User;
import com.crm.guard.exception.IntegrationValidationException;
import com.crm.guard.service.api.IntegrationAuditService;
import com.crm.guard.service.api.UserService;
import com.crm.guard.service.integration1c.extractor.xls.*;
import com.crm.guard.service.integration1c.integrator.*;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class IntegrationFacade {

    @Autowired
    private UserService userService;

    @Autowired
    private ClientsIntegration clientsIntegration;

    @Autowired
    private ClientsXlsExtractorImpl clientsExtractor;

    @Autowired
    private ContractsIntegration contractsIntegration;

    @Autowired
    private ContractsXlsExtractorImpl contractsXlsExtractor;

    @Autowired
    private TotalInvoicesIntegration contractsTotalInvoicesIntegration;

    @Autowired
    private TotalInvoicesXlsExtractorImpl contractsTotalInvoicesXlsExtractor;

    @Autowired
    private InvoicesIntegration invoicesIntegration;

    @Autowired
    private InvoicesXlsExtractorImpl invoicesXlsExtractor;

    @Autowired
    private PaymentsIntegration paymentsIntegration;

    @Autowired
    private PaymentsXlsExtractorImpl paymentsXlsExtractor;

    @Autowired
    private IntegrationAuditService integrationAuditService;

    @Value("${integration.clients.name}")
    private String clientsName;

    @Value("${integration.contracts.name}")
    private String contractsName;

    @Value("${integration.contracts.total.invoices.name}")
    private String contractsTotalInvoicesName;

    @Value("${integration.invoices.name}")
    private String invoicesName;

    @Value("${integration.payments.name}")
    private String paymentsName;

    @Scheduled(cron = "0 0 9 ? * *")  // at 9AM every day
    public void scheduleRun() throws IOException, InvalidFormatException, IntegrationValidationException {
        User system = userService.findById("system");
        integrationProcess(system);
    }

    public void integrationProcess(User user) throws IOException, InvalidFormatException, IntegrationValidationException {
        integrationAuditService.start(user, IntegrationAudit.Type.full);

        try {
            clientsRun();
            contractsRun();
            totalInvoicesRun();
            invoicesRun();
            paymentsRun();
        } catch (Exception e) {
            integrationAuditService.terminate(e);
            throw new IntegrationValidationException(e.getMessage());
        }

        integrationAuditService.finish();
    }

    public void clients(User user) throws IOException, InvalidFormatException, IntegrationValidationException {
        integrationAuditService.start(user, IntegrationAudit.Type.clients);

        try {
            clientsRun();
        } catch (Exception e) {
            integrationAuditService.terminate(e);
            throw new IntegrationValidationException(e.getMessage());
        }

        integrationAuditService.finish();
    }

    public void contracts(User user) throws IOException, InvalidFormatException, IntegrationValidationException {
        integrationAuditService.start(user, IntegrationAudit.Type.contracts);

        try {
            contractsRun();
        } catch (Exception e) {
            integrationAuditService.terminate(e);
            throw new IntegrationValidationException(e.getMessage());
        }

        integrationAuditService.finish();
    }

    public void invoices(User user) throws IOException, InvalidFormatException, IntegrationValidationException {
        integrationAuditService.start(user, IntegrationAudit.Type.invoices);

        try {
            invoicesRun();
        } catch (Exception e) {
            integrationAuditService.terminate(e);
            throw new IntegrationValidationException(e.getMessage());
        }

        integrationAuditService.finish();
    }

    public void totalInvoices(User user) throws IOException, InvalidFormatException, IntegrationValidationException {
        integrationAuditService.start(user, IntegrationAudit.Type.total_invoice);

        try {
            totalInvoicesRun();
        } catch (Exception e) {
            integrationAuditService.terminate(e);
            throw new IntegrationValidationException(e.getMessage());
        }

        integrationAuditService.finish();
    }

    public void payments(User user) throws IOException, InvalidFormatException, IntegrationValidationException {
        integrationAuditService.start(user, IntegrationAudit.Type.invoices);

        try {
            paymentsRun();
        } catch (Exception e) {
            integrationAuditService.terminate(e);
            throw new IntegrationValidationException(e.getMessage());
        }

        integrationAuditService.finish();
    }

    private void clientsRun() throws IOException, InvalidFormatException {
        clientsIntegration.extract(clientsName, clientsExtractor);
    }

    private void contractsRun() throws IOException, InvalidFormatException {
        contractsIntegration.extract(contractsName, contractsXlsExtractor);
    }

    private void totalInvoicesRun() throws IOException, InvalidFormatException {
        contractsTotalInvoicesIntegration.extract(contractsTotalInvoicesName, contractsTotalInvoicesXlsExtractor);
    }

    private void invoicesRun() throws IOException, InvalidFormatException {
        invoicesIntegration.extract(invoicesName, invoicesXlsExtractor);
    }

    private void paymentsRun() throws IOException, InvalidFormatException {
        paymentsIntegration.extract(paymentsName, paymentsXlsExtractor);
    }

    public String getClientsPath() {
        return clientsName;
    }

    public String getContractsPath() {
        return contractsName;
    }

    public String getTotalInvoicesPath() {
        return contractsTotalInvoicesName;
    }

    public String getInvoicesPath() {
        return invoicesName;
    }

    public String getPaymentsPath() {
        return paymentsName;
    }
}
