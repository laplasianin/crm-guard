package com.crm.guard.service;

import com.crm.guard.dao.TotalInvoiceSimpleDAO;
import com.crm.guard.entity.TotalInvoiceSimple;
import com.crm.guard.service.api.TotalInvoiceSimpleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class TotalInvoiceSimpleServiceImpl implements TotalInvoiceSimpleService {

    @Autowired
    private TotalInvoiceSimpleDAO totalInvoiceSimpleDAO;

    @Override
    @Transactional
    public void save(List<TotalInvoiceSimple> entities) {
        for (TotalInvoiceSimple entity : entities) {
            totalInvoiceSimpleDAO.saveOrUpdate(entity);
        }
    }
}
