package com.crm.guard.form.clienttodo;

import com.crm.guard.entity.ClientToDo;
import com.crm.guard.form.base.FORM;

import java.sql.Timestamp;

public class UpdateDatesToDoFORM implements FORM {

    private ClientToDo clientToDo;

    private Timestamp start;
    private Timestamp end;

    public ClientToDo getClientToDo() {
        return clientToDo;
    }

    public void setClientToDo(ClientToDo clientToDo) {
        this.clientToDo = clientToDo;
    }

    public Timestamp getStart() {
        return start;
    }

    public void setStart(Timestamp start) {
        this.start = start;
    }

    public Timestamp getEnd() {
        return end;
    }

    public void setEnd(Timestamp end) {
        this.end = end;
    }
}
