package com.crm.guard.service;

import com.crm.guard.dao.BillDAO;
import com.crm.guard.entity.Bill;
import com.crm.guard.service.api.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
public class BillServiceImpl implements BillService {

    @Autowired
    private BillDAO billDAO;

    @Override
    @Transactional
    public void delete(Bill bill) {
        billDAO.delete(bill);
    }

    @Override
    @Transactional
    public void deleteAll() {
        billDAO.deleteAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Bill> findByInvoiceId(Long invoiceId) {
        if (invoiceId == null) {
            return Collections.emptyList();
        }
        return billDAO.findByInvoiceId(invoiceId);
    }
}
