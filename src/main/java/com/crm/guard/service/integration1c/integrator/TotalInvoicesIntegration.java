package com.crm.guard.service.integration1c.integrator;

import com.crm.guard.entity.AbstractEntity;
import com.crm.guard.entity.Contract;
import com.crm.guard.entity.TotalInvoiceSimple;
import com.crm.guard.service.api.ContractService;
import com.crm.guard.service.api.TotalInvoiceService;
import com.crm.guard.service.api.TotalInvoiceSimpleService;
import com.crm.guard.service.integration1c.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class TotalInvoicesIntegration extends AbstractIntegration {

    @Autowired
    private ContractService contractService;

    @Autowired
    private TotalInvoiceService totalInvoiceService;

    @Autowired
    private TotalInvoiceSimpleService totalInvoiceSimpleService;


    @Override
    protected Result<AbstractEntity> toLocalDatabase(Collection<AbstractEntity> extract) {
        Result<AbstractEntity> result = new Result<AbstractEntity>();

        saveContracts(extract);

        removeOldClientsTotalInvoices();
        saveNewClientTotalInvoices();

        result.log("Сохранили данные о долгах по договору и клиенту");
        return result;
    }

    private void saveNewClientTotalInvoices() {
        List<TotalInvoiceSimple> totalInvoices = contractService.findTotalInvoices();
        totalInvoiceSimpleService.save(totalInvoices);
    }

    private void removeOldClientsTotalInvoices() {
        totalInvoiceService.removeAll();
    }

    private void saveContracts(Collection<AbstractEntity> extract) {
        for (AbstractEntity contractObj : extract) {
            Contract contract = (Contract) contractObj;
            contractService.saveOrUpdate(contract);  // TODO batch update
        }
    }


}
