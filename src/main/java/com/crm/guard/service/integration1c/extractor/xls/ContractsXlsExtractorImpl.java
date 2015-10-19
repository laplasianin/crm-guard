package com.crm.guard.service.integration1c.extractor.xls;

import com.crm.guard.entity.Client;
import com.crm.guard.entity.Contract;
import com.crm.guard.entity.Entity;
import com.crm.guard.exception.BadRequestException;
import com.crm.guard.service.api.ClientService;
import com.crm.guard.service.api.ContractService;
import com.crm.guard.service.api.EntityService;
import com.crm.guard.service.integration1c.extractor.DataExtractor;
import com.crm.guard.service.integration1c.integrator.Input;
import com.crm.guard.service.integration1c.util.Result;
import org.apache.commons.lang3.StringUtils;
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
import java.util.*;

import static com.crm.guard.utils.XlsCellUtils.getDateValue;
import static com.crm.guard.utils.XlsCellUtils.getStringValue;

@Component
public class ContractsXlsExtractorImpl implements DataExtractor {

    @Autowired
    private ClientService clientService;

    @Autowired
    private EntityService entityService;

    @Autowired
    private ContractService contractService;

    @Override
    public Result extract(Collection<Input> files) throws IOException, InvalidFormatException {
        Result<Contract> result = new Result<Contract>();

        Map<String, Contract> stringContractMap = contractService.getDbContracts();
        List<Entity> entities = entityService.findAll();
        Map<String, Client> dbClients = clientService.getDbClients();

        for (Input input : files) {
            addToResult(input, result, stringContractMap, entities, dbClients);
        }
        return result;
    }

    private void addToResult(Input input, Result<Contract> result, Map<String, Contract> dbContracts, List<Entity> entities, Map<String, Client> dbClients) throws IOException, InvalidFormatException {
        Workbook wb = WorkbookFactory.create(input.getStream());
        Sheet sheet = wb.getSheetAt(0);
        Iterator<Row> iterator = sheet.iterator();

        skipFirstRow(iterator);
        int rowNumber = 0;
        int added = 0;
        while (iterator.hasNext()) {
            rowNumber++;
            Row row = iterator.next();

            // код
            String fullId = getStringValue(row.getCell(0));
            // номер
            String number = getStringValue(row.getCell(1));
            // дата начала
            Date start = getDateValue(row.getCell(2));
            // дата завершения
            Date to = getDateValue(row.getCell(3));
            // филиал
            String entityId = getStringValue(row.getCell(4));

            String[] idsArr = StringUtils.split(fullId, "/");
            String clientId = idsArr[0];
            String contractId = idsArr[1];

            if (number == null) {
                number = contractId;
            }

            if (StringUtils.isEmpty(contractId)) {
                result.log(rowNumber, "Отсутствует код");
                continue;
            }
            String clientCodeByFilial = clientId + "-" + entityId;
            Client client = findFromAllIds(clientCodeByFilial, dbClients);
            if (client == null) {
                result.log(rowNumber, "Не найден клиент с кодом " + clientCodeByFilial);
                continue;
            }

            Contract contract = findInList(fullId, result.getExtracted());

            if (contract == null) {
                contract = findInDb(fullId, dbContracts);
            }

            if (contract == null) {
                contract = new Contract();
                contract.setId(fullId);
            }

            contract.setClient(client);
            contract.setContractNumber(number);
            contract.setContractStartDate(start);
            contract.setContractStartDate(to);
            contract.setEntity(findEntity(entities, entityId));
            contract.setTotalInvoice(BigDecimal.ZERO);

            result.add(contract);
            added++;
        }
        result.log(MessageFormat.format("\r\n Обнаружено {0} записей в файле, из них корректных {1}",
                rowNumber, added));
    }

    private Contract findInList(String fullId, List<Contract> contracts) {
        for (Contract contract : contracts) {
            if (contract.getId().equals(fullId)) {
                return contract;
            }
        }
        return null;
    }

    private Client findFromAllIds(String clientCodeByFilial, Map<String, Client> dbClients) {
        for (Client client : dbClients.values()) {
            if (StringUtils.contains(client.getAllIds(), clientCodeByFilial)) {
                return client;
            }
        }
        return null;
    }

    private Contract findInDb(String fullId, Map<String, Contract> dbContracts) {
        return dbContracts.get(fullId);
    }

    @Override
    public String getName() {
        return "Договоры";
    }

    private Entity findEntity(List<Entity> entities, String entityId) {
        for (Entity entity : entities) {
            if (entity.getId().equals(Long.valueOf(entityId))) {
                return entity;
            }
        }
        throw new BadRequestException("Не найден филиал с ИД " + entityId);
    }

    private void skipFirstRow(Iterator<Row> iterator) {
        if (iterator.hasNext()) {
            iterator.next();
        }
    }
}
