package com.crm.guard.controller;

import com.crm.guard.binder.ClientEditor;
import com.crm.guard.entity.Client;
import com.crm.guard.service.api.ClientService;
import com.crm.guard.utils.FileUtils;
import com.crm.guard.utils.collections.transformers.ContractsTransformer;
import com.ibm.icu.text.RuleBasedNumberFormat;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import static com.crm.guard.utils.RussianCaseUtils.defineCase;
import static com.crm.guard.utils.RussianCaseUtils.rubleCases;
import static org.apache.commons.collections4.CollectionUtils.collect;
import static org.apache.commons.lang3.StringUtils.join;

@Secured(value = {"ROLE_USER"})
@Controller
@RequestMapping("/report")
public class ReportsController {

    @Autowired
    private ClientService clientService;

    @Autowired
    ServletContext servletContext;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Client.class, new ClientEditor(clientService));
    }

    @PreAuthorize("hasAnyRole('ROLE_OPERATOR', 'ROLE_ADMIN')")
    @RequestMapping(value = "stop_guarding_template", method = RequestMethod.GET)
    public void stopGuardingTemplate(@RequestParam(value = "id") Client client, HttpServletResponse response) throws IOException, JRException {
        final String path = servletContext.getRealPath("/assets/reports/");
        File inFile = new File(path + "/stopContractsAndStopGuarding.jrxml");

        client = clientService.findWithContracts(client.getId());
        HashMap<String, Object> parameters = getReportParams(client);

        JasperDesign jasperDesign = JRXmlLoader.load(inFile);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());

        JRPdfExporter exporter = new JRPdfExporter();
        exporter.setParameter(JRExporterParameter.CHARACTER_ENCODING, "UTF-8");

        addPdfFontsToStyles(jasperReport.getStyles());

        byte[] bytes = JasperExportManager.exportReportToPdf(jasperPrint);
        FileUtils.doFile(response, bytes, client.getShortName() + ".pdf");
    }

    @PreAuthorize("hasAnyRole('ROLE_OPERATOR', 'ROLE_ADMIN')")
    @RequestMapping(value = "stop_contracts_template", method = RequestMethod.GET)
    public void stopContractsTemplate(@RequestParam(value = "id") Client client, HttpServletResponse response) throws IOException, JRException {
        final String path = servletContext.getRealPath("/assets/reports/");
        File inFile = new File(path + "/stopContracts.jrxml");

        client = clientService.findWithContracts(client.getId());
        HashMap<String, Object> parameters = getReportParams(client);

        JasperDesign jasperDesign = JRXmlLoader.load(inFile);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());

        JRPdfExporter exporter = new JRPdfExporter();
        exporter.setParameter(JRExporterParameter.CHARACTER_ENCODING, "UTF-8");

        addPdfFontsToStyles(jasperReport.getStyles());

        byte[] bytes = JasperExportManager.exportReportToPdf(jasperPrint);
        FileUtils.doFile(response, bytes, client.getShortName() + ".pdf");
    }

    private HashMap<String, Object> getReportParams(@RequestParam(value = "id") Client client) {
        BigDecimal invoice = getTotalInvoice(client);

        HashMap<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("Client", client.getShortName());
        parameters.put("clientAddress", client.getRegistrationAddress());
        parameters.put("contactFullName", client.getFullName());
        parameters.put("contracts", join(collect(client.getContracts(), new ContractsTransformer()), ", "));
        parameters.put("objectName", ""); // TODO Что за объект
        parameters.put("objectAddress", client.getRegistrationAddress());
        parameters.put("stopContractDate", new SimpleDateFormat("dd.mm.yyyy").format(new Date()));
        parameters.put("totalInvoice", invoice.toPlainString());
        parameters.put("totalInvoiceInRus",new RuleBasedNumberFormat(Locale.getDefault(), RuleBasedNumberFormat.SPELLOUT).format(invoice));
        parameters.put("rubles", rubleCases.get(defineCase(invoice.intValue())));
        return parameters;
    }

    public void addPdfFontsToStyles(JRStyle[] styles) {
        final String font = servletContext.getRealPath("/assets/reports/") + "/times.ttf";
        if (styles != null) {
            for (JRStyle style : styles) {
                if (style.getName().equals("reportStyle")) {
                    style.setPdfFontName(font);
                    style.setBlankWhenNull(true);
                }

                if (style.getName().equals("reportBoldStyle")) {
                    style.setPdfFontName(font);
                    style.setBlankWhenNull(true);
                }

            }
        }
    }

    private BigDecimal getTotalInvoice(Client client) {
        BigDecimal invoice = BigDecimal.ZERO;
        if (client.getTotalInvoice() != null) {
            invoice = client.getTotalInvoice().getInvoice();
        }
        return invoice;
    }
}
