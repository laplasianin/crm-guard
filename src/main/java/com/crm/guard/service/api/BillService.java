package com.crm.guard.service.api;

import com.crm.guard.entity.Bill;

import java.util.List;

public interface BillService {

    void delete(Bill bill);

    void deleteAll();

    List<Bill> findByInvoiceId(Long invoiceId);
}