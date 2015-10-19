package com.crm.guard.form.clienttodo;

import com.crm.guard.entity.ClientToDo;
import com.crm.guard.form.base.FORM;

public class UpdateTextToDoFORM implements FORM {

    private ClientToDo clientToDo;

    private String title;
    private String description;

    public ClientToDo getClientToDo() {
        return clientToDo;
    }

    public void setClientToDo(ClientToDo clientToDo) {
        this.clientToDo = clientToDo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
