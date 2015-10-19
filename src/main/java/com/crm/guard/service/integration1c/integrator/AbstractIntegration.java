package com.crm.guard.service.integration1c.integrator;

import com.crm.guard.entity.AbstractEntity;
import com.crm.guard.service.api.SettingsService;
import com.crm.guard.service.integration1c.extractor.DataExtractor;
import com.crm.guard.service.integration1c.util.Logger;
import com.crm.guard.service.integration1c.util.Result;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public abstract class AbstractIntegration {


    @Autowired
    private SettingsService settingsService;

    @Autowired
    private Logger logger;

    @Autowired
    ServletContext servletContext;

    public void extract(String fileName, DataExtractor extractor) throws IOException, InvalidFormatException {

        logger.logStart("\r\nЗапуск процесса интеграции "
                + extractor.getName() + ".\r\n");

        Result<AbstractEntity> extract = extractDataFromSource(fileName, extractor);
        logger.log("Считывание файла, " + extractor.getName(), extract);

        Result<AbstractEntity> result = toLocalDatabase(extract.getExtracted());
        logger.log("Интеграция, " + extractor.getName(), result);

        logger.logFinish("\r\nЗавершение процесса интеграции "
                + extractor.getName() + ".\r\n");
    }

    private Result<AbstractEntity> extractDataFromSource(String fileName, DataExtractor extractor) throws IOException, InvalidFormatException {
        List<Input> inputStream = getInputStream(fileName);
        Result<AbstractEntity> extracted = extractor.extract(inputStream);
        for (Input input : inputStream) {
            input.getStream().close();
        }
        return extracted;
    }

    protected List<Input> getInputStream(String fileName) throws FileNotFoundException {
        List<Input> streams = new ArrayList<Input>();

        final String realPath = settingsService.getIntegrationPath();

        File file = new File(realPath);

        String[] directories = file.list(new FilenameFilter() {
            @Override
            public boolean accept(File current, String name) {
                return new File(current, name).isDirectory();
            }
        });

        for (String directory : directories) {
            String name = realPath + "/" + directory + "/" + fileName;
            try {
                streams.add(new Input(new FileInputStream(name), directory));
            } catch (FileNotFoundException e) {
                System.out.println("Не найден файл " + name);
            }
        }

        return streams;
    }

    protected abstract Result<AbstractEntity> toLocalDatabase(Collection<AbstractEntity> extract);

}
