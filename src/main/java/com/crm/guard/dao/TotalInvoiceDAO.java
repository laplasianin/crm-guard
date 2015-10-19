package com.crm.guard.dao;

import com.crm.guard.entity.TotalInvoice;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository
public class TotalInvoiceDAO extends AbstractDao<TotalInvoice, Long> {

    public Map<String, BigDecimal> getSumOfGroupBillsByClient() {
        String hql = "SELECT c.id, sum(inv.DEBT) FROM INVOICE inv " +
                " join CONTRACT con on con.id = inv.CONTRACT_ID " +
                " join CLIENT c on c.id = con.client_id " +
                " group by c.id";
        List<Object[]> rows = createQuery(hql).list();
        Map<String, BigDecimal> result = new HashMap<String, BigDecimal>();
        for (Object[] row : rows) {
            result.put((String) row[0], (BigDecimal) row[1]);
        }
        return result;
    }
}
