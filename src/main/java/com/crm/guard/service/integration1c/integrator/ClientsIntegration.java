package com.crm.guard.service.integration1c.integrator;

import com.crm.guard.entity.AbstractEntity;
import com.crm.guard.service.api.ClientService;
import com.crm.guard.service.integration1c.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ClientsIntegration extends AbstractIntegration {

    @Autowired
    private ClientService clientService;

    @Override
    protected Result<AbstractEntity> toLocalDatabase(Collection<AbstractEntity> extract) {
        Result<AbstractEntity> result = new Result<AbstractEntity>();
        clientService.saveOrUpdate(extract);
        result.log("Сохранено/обновлено " + extract.size() + " записей");
        return result;
    }


}
