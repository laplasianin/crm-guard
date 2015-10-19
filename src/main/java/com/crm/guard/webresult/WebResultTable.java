package com.crm.guard.webresult;

import java.util.Collection;
import java.util.List;

public class WebResultTable<T> {

    private final Collection<T> rows;
    private final Long total;

    public WebResultTable(List<T> rows, Long total) {
        this.rows = rows;
        this.total = total;
    }

    public Collection<T> getRows() {
        return rows;
    }

    public Long getTotal() {
        return total;
    }

}
