package com.crm.guard.binder;

import com.crm.guard.entity.EventType;
import com.crm.guard.service.api.EventTypeService;

import java.beans.PropertyEditorSupport;

public class EventTypeEditor extends PropertyEditorSupport {

    private EventTypeService eventTypeService;

    public EventTypeEditor(EventTypeService eventTypeService) {
        this.eventTypeService = eventTypeService;
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        setValue(eventTypeService.findById(text));
    }

    @Override
    public String getAsText() {
        EventType eventType = (EventType) getValue();
        if (eventType == null) {
            return null;
        } else {
            return eventType.getCode();
        }
    }
}
