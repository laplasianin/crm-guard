package com.crm.guard.service.api;

import com.crm.guard.entity.Client;
import org.springframework.transaction.annotation.Transactional;

public interface OperatorService {

    Boolean isMyClient(Client client);

    void takeToWork(Client client);

    @Transactional
    void kickFromWork(Client client);
}