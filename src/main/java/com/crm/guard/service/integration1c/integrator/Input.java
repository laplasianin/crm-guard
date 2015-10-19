package com.crm.guard.service.integration1c.integrator;

import java.io.InputStream;

public class Input {

    private InputStream stream;

    private String entityId;

    public Input(InputStream stream, String entityId) {
        this.stream = stream;
        this.entityId = entityId;
    }

    public InputStream getStream() {
        return stream;
    }

    public String getEntityId() {
        return entityId;
    }
}
