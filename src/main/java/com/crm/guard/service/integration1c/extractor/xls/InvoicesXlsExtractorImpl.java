package com.crm.guard.service.integration1c.extractor.xls;

import com.crm.guard.entity.Contract;
import com.crm.guard.entity.Invoice;
import com.crm.guard.service.api.ContractService;
import com.crm.guard.service.api.InvoiceService;
import com.crm.guard.service.integration1c.extractor.DataExtractor;
import com.crm.guard.service.integration1c.integrator.Input;
import com.crm.guard.service.integration1c.util.Result;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import static com.crm.guard.utils.XlsCellUtils.*;

@Component
public class InvoicesXlsExtractorImpl implements DataExtractor {

    @Autowired
    private ContractService contractService;

    @Autowired
    private InvoiceService invoiceService;

    @Override
    public Result extract(Collection<Input> files) throws IOException, InvalidFormatException {

        Result<Invoice> result = new Result<Invoice>();

        Map<String, Contract> stringContractMap = contractService.getDbContracts();

        result.log("Удаляем старые счета...");
        invoiceService.removeAll();
        result.log("Удалено...");

        for (Input input : files) {
            addToResult(input, result, stringContractMap);
        }
        return result;
    }

    private void addToResult(Input inputStream, Result<Invoice> result, Map<String, Contract> stringContractMap) throws IOException, InvalidFormatException {
        Workbook wb = WorkbookFactory.create(inputStream.getStream());
        Sheet sheet = wb.getSheetAt(0);
        Iterator<Row> iterator = sheet.iterator();

        skipFirstRow(iterator);

        int rowNumber = 0;
        int added = 0;
        while (iterator.hasNext()) {
            rowNumber++;
            Row row = iterator.next();

            // код договора
            String fullId = getStringValue(row.getCell(0));
            // Номер счета
            String number = getStringValue(row.getCell(1));
            // Дата счета
            Date billDate = getDateValue(row.getCell(2));
            // Сумма к оплате
            BigDecimal debt = getBigDecimalValue(row.getCell(3));
            // Период оказания услуг
            String period = getStringValue(row.getCell(4));
            // Описание
            String description = getStringValue(row.getCell(5));

            Contract contract = stringContractMap.get(fullId);
            if (contract == null) {
                result.log(rowNumber, "Не найден договор с ид " + fullId);
                continue;
            }

            Invoice invoice = new Invoice();
            invoice.setContract(contract);
            invoice.setNumber(number);
            invoice.setInvoiceDate(billDate);
            invoice.setDebt(debt);
            invoice.setPeriod(period);
            invoice.setDescription(description);

            result.add(invoice);
            added++;
        }
        result.log(MessageFormat.format("\r\n Обнаружено {0} записей в файле, из них корректных {1}",
                rowNumber, added));
    }

    @Override
    public String getName() {
        return "Счета";
    }

    private void skipFirstRow(Iterator<Row> iterator) {
        if (iterator.hasNext()) {
            iterator.next();
        }
    }
}
