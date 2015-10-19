package com.crm.guard.service.integration1c.util;

import java.util.ArrayList;
import java.util.List;

public class Result<C> {

    StringBuilder log = new StringBuilder();
    List<C> extracted = new ArrayList<C>();

    public void add(C entity) {
        extracted.add(entity);
    }

    public void log(int rowNumber, String message) {
        log.append("\r\n Строка ").append(rowNumber).append(" ").append(message);
    }

    public void log(String message) {
        log.append("\r\n " + message);
    }

    public StringBuilder getLog() {
        return log;
    }

    public List<C> getExtracted() {
        return extracted;
    }
}
