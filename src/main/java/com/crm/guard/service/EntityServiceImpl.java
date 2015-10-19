package com.crm.guard.service;

import com.crm.guard.dao.EntityDAO;
import com.crm.guard.entity.Entity;
import com.crm.guard.service.api.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EntityServiceImpl implements EntityService {

    @Autowired
    private EntityDAO entityDAO;

    @Override
    @Transactional(readOnly = true)
    public List<Entity> findAll() {
        return entityDAO.getAll();  // TODO cached
    }

    @Transactional
    @Override
    public void createEntities() {
        Entity entity = new Entity();
        entity.setId(1L);
        entity.setName("Общество с ограниченной ответственностью Охранное предприятие «Гард-НН СБР»");
        entity.setShortName("ООО ОП «Гард-НН СБР»");
        entityDAO.saveOrUpdate(entity);

        entity = new Entity();
        entity.setId(2L);
        entity.setName("Общество с ограниченной ответственностью Охранное предприятие «АКС Гард-НН»");
        entity.setShortName("ООО ОП «АКС Гард-НН»");
        entityDAO.saveOrUpdate(entity);

        entity = new Entity();
        entity.setId(3L);
        entity.setName("Общество с ограниченной ответственностью Частная охранная организация «Бумеранг АКС-НН»");
        entity.setShortName("ООО ЧОО «Бумеранг АКС-НН»");
        entityDAO.saveOrUpdate(entity);

        entity = new Entity();
        entity.setId(4L);
        entity.setName("Общество с ограниченной ответственностью Охранное предприятие «Гард-НН»");
        entity.setShortName("ООО ОП «Гард-НН»");
        entityDAO.saveOrUpdate(entity);

        entity = new Entity();
        entity.setId(5L);
        entity.setName("Общество с ограниченной ответственностью «Гард-НН»");
        entity.setShortName("ООО «Гард-НН»");
        entityDAO.saveOrUpdate(entity);

        entity = new Entity();
        entity.setId(6L);
        entity.setName("Общество с ограниченной ответственностью Частная охранная организация «Гард-НН»");
        entity.setShortName("ООО ЧОО \"Гард-НН\"");
        entityDAO.saveOrUpdate(entity);
    }

}
