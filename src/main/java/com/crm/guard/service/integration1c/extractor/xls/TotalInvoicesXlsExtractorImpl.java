package com.crm.guard.service.integration1c.extractor.xls;

import com.crm.guard.entity.Contract;
import com.crm.guard.service.api.ContractService;
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
import java.util.Iterator;
import java.util.Map;

import static com.crm.guard.utils.XlsCellUtils.getBigDecimalValue;
import static com.crm.guard.utils.XlsCellUtils.getStringValue;

@Component
public class TotalInvoicesXlsExtractorImpl implements DataExtractor {

    @Autowired
    private ContractService contractService;

    @Override
    public Result extract(Collection<Input> files) throws IOException, InvalidFormatException {

        Result<Contract> result = new Result<Contract>();

        result.log("Удаляем данные задолженностей по договорам...");
        contractService.clearTotalInvoices();
        result.log("Удалено");

        Map<String, Contract> stringContractMap = contractService.getDbContracts();

        for (Input input : files) {
            addToResult(input, result, stringContractMap);
        }
        return result;
    }

    private void addToResult(Input input, Result<Contract> result, Map<String, Contract> stringContractMap) throws IOException, InvalidFormatException {
        Workbook wb = WorkbookFactory.create(input.getStream());
        Sheet sheet = wb.getSheetAt(0);
        Iterator<Row> iterator = sheet.iterator();

        skipFirstRow(iterator);

        int added = 0;
        int rowNumber = 0;
        while (iterator.hasNext()) {
            rowNumber++;
            Row row = iterator.next();

            // код
            String fullId = getStringValue(row.getCell(0));
            // долг
            BigDecimal number = getBigDecimalValue(row.getCell(1));

            Contract contract = stringContractMap.get(fullId);
            if (contract == null) {
                result.log(rowNumber, "Не найден договор с ид " + fullId);
                continue;
            }

            contract.setTotalInvoice(number);
            result.add(contract);
            added++;
        }
        result.log(MessageFormat.format("\r\n Обнаружено {0} записей в файле, из них корректных {1}",
                rowNumber, added));
    }

    @Override
    public String getName() {
        return "Долги по договору";
    }

    private void skipFirstRow(Iterator<Row> iterator) {
        if (iterator.hasNext()) {
            iterator.next();
        }
    }
}
