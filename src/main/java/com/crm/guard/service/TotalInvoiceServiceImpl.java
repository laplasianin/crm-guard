package com.crm.guard.service;

import com.crm.guard.dao.TotalInvoiceDAO;
import com.crm.guard.service.api.TotalInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class TotalInvoiceServiceImpl implements TotalInvoiceService {

    @Autowired
    private TotalInvoiceDAO totalInvoiceDAO;

    @Override
    @Transactional
    public void removeAll() {
        totalInvoiceDAO.deleteAll();
    }
}
