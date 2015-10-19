package com.crm.guard.binder;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.beans.PropertyEditorSupport;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TimestampEditor extends PropertyEditorSupport {
    private final Logger logger = Logger.getLogger(TimestampEditor.class);
    @Override
    public void setAsText(String text) {
        if (StringUtils.isNotEmpty(text)) {
            try {
                if (text.length() == 10) {
                    text = text + " 00:00";
                }
                setValue(new Timestamp(new SimpleDateFormat("dd.MM.yyyy HH:mm").parse(text).getTime()));
            } catch (ParseException e) {
                logger.error(e);
            }
        }
    }

}
