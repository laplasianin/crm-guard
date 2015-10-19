package com.crm.guard.service.integration1c.integrator;

import com.crm.guard.entity.AbstractEntity;
import com.crm.guard.service.api.ContractService;
import com.crm.guard.service.integration1c.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;
import java.util.Collection;

@Service
public class ContractsIntegration extends AbstractIntegration {

    @Autowired
    private ContractService contractService;

    @Autowired
    ServletContext servletContext;

    @Override
    protected Result<AbstractEntity> toLocalDatabase(Collection<AbstractEntity> extract) {
        Result<AbstractEntity> result = new Result<AbstractEntity>();
        contractService.saveOrUpdate(extract);
        result.log("Сохранено/обновлено " + extract.size() + " записей");

        return result;
    }


}
