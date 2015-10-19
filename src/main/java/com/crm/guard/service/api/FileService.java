package com.crm.guard.service.api;

import com.crm.guard.entity.File;
import com.crm.guard.filter.Filter;
import com.crm.guard.form.UploadedFile;
import com.crm.guard.webresult.WebResultTable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface FileService {

    boolean saveFile(UploadedFile uploadedFile) throws IOException;

    WebResultTable<File> findWebResultById(Filter filter, String id);

    void httpDoFile(HttpServletResponse response, String path) throws IOException;

    void delete(String path, String clientId, String fileName) throws IOException;
}
