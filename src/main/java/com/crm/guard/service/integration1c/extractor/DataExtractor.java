package com.crm.guard.service.integration1c.extractor;

import com.crm.guard.entity.AbstractEntity;
import com.crm.guard.service.integration1c.integrator.Input;
import com.crm.guard.service.integration1c.util.Result;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.IOException;
import java.util.Collection;

public interface DataExtractor {

    Result<AbstractEntity> extract(Collection<Input> files) throws IOException, InvalidFormatException;

    String getName();
}
