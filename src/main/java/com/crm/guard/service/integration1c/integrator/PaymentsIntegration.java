package com.crm.guard.service.integration1c.integrator;

import com.crm.guard.entity.AbstractEntity;
import com.crm.guard.service.api.PaymentService;
import com.crm.guard.service.integration1c.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class PaymentsIntegration extends AbstractIntegration {

    @Autowired
    private PaymentService paymentService;

    @Override
    protected Result<AbstractEntity> toLocalDatabase(Collection<AbstractEntity> extract) {
        Result<AbstractEntity> result = new Result<AbstractEntity>();

        paymentService.removeAll();
        paymentService.saveOrUpdate(extract);
        result.log("Сохранено/обновлено " + extract.size() + " записей");

        return result;
    }


}
