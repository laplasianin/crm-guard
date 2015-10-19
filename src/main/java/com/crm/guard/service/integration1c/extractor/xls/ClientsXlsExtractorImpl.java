package com.crm.guard.service.integration1c.extractor.xls;

import com.crm.guard.entity.Client;
import com.crm.guard.entity.ClientType;
import com.crm.guard.service.api.ClientService;
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
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static com.crm.guard.utils.XlsCellUtils.getStringValue;
import static org.apache.commons.lang3.StringUtils.equalsIgnoreCase;

@Component
public class ClientsXlsExtractorImpl implements DataExtractor {

    @Autowired
    private ClientService clientService;

    @Override
    public Result extract(Collection<Input> files) throws IOException, InvalidFormatException {

        Result<Client> result = new Result<Client>();

        Map<String, Client> dbClients = clientService.getDbClients();

        for (Input input : files) {
            addToResult(input, result, dbClients);
        }
        return result;
    }

    private void addToResult(Input input, Result<Client> result, Map<String, Client> dbClients) throws IOException, InvalidFormatException {
        String entityId = input.getEntityId();

        Workbook wb = WorkbookFactory.create(input.getStream());
        Sheet sheet = wb.getSheetAt(0);
        Iterator<Row> iterator = sheet.iterator();


        skipFirstRow(iterator);
        int rowNumber = 0;
        int added = 0;
        while (iterator.hasNext()) {
            rowNumber++;
            Row row = iterator.next();

            //код
            String code = getStringValue(row.getCell(0), 10);  // TODO 10????
            //краткое наименование
            String shortName = getStringValue(row.getCell(1));
            //поное наименование
            String fullName = getStringValue(row.getCell(2));
            // тип
            ClientType type = ClientType.valueOf(getStringValue(row.getCell(3)));
            // ИНН
            String inn;
            if (type == ClientType.P) {
                inn = getStringValue(row.getCell(4), 10);
            } else {
                inn = getStringValue(row.getCell(4), 12);
            }
            // КПП
            String kpp = getStringValue(row.getCell(5), 9);
            // номер лицевого счета
            String personalAccount = getStringValue(row.getCell(6), 9);  // TODO 9???
            // email
            String email = getStringValue(row.getCell(7));
            // Регистрационный адрес
            String registrationAddress = getStringValue(row.getCell(8));
            // TODO Почтовый адрес
            String postAddress = getStringValue(row.getCell(9));
            // TODO Адрес доставки
            String deliveryAddress = getStringValue(row.getCell(10));
            // TODO мобильный номер
//            String mobileNumber = row.getCell(11).getStringCellValue();

            if (StringUtils.isEmpty(inn)) {
                result.log(rowNumber, "Отсутствует ИНН");
                continue;
            }
            if (StringUtils.isEmpty(personalAccount)) {
                result.log(rowNumber, "Отсутствует лицевой счет");
                continue;
            }
            if (StringUtils.isEmpty(code)) {
                result.log(rowNumber, "Отсутствует КОД");
                continue;
            }

            code += "-" + entityId;  //код в пределах филиала

            Client client;
            client = findInList(result.getExtracted(), inn, kpp);
            if (client != null) {
                client.addCode(code);
                continue;
            }

            String id = Client.buildId(inn, kpp);
            client = dbClients.get(id);
            if (client == null) {
                client = new Client();
                client.setId(id);
            }

            client.addCode(code);
            client.setShortName(shortName);
            client.setFullName(fullName);
            client.setType(type);
            client.setInn(inn);
            client.setOkpo(kpp);
            if (StringUtils.isNotEmpty(email)) {
                client.setEmail(email);
            }
            client.setPersonalAccount(personalAccount);
            client.setRegistrationAddress(registrationAddress);
//            client.setMobileNumber(mobileNumber);

            result.add(client);
            added++;
        }
        result.log(MessageFormat.format("\r\n Обнаружено {0} записей в файле, из них корректных {1}",
                rowNumber, added));
    }

    @Override
    public String getName() {
        return "Контрагенты";
    }

    private Client findInList(List<Client> result, String inn, String kpp) {
        for (Client client : result) {
            if (equalsIgnoreCase(client.getInn(), inn)
                    && equalsIgnoreCase(client.getOkpo(), kpp)) {
                return client;
            }
        }
        return null;
    }

    private void skipFirstRow(Iterator<Row> iterator) {
        if (iterator.hasNext()) {
            iterator.next();
        }
    }
}
