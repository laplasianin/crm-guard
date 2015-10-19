package com.crm.guard.service.api;

import com.crm.guard.entity.Entity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface EntityService {

    List<Entity> findAll();

    @Transactional
    void createEntities();
}