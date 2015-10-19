package com.crm.guard.service;

import com.crm.guard.dao.FileDAO;
import com.crm.guard.entity.File;
import com.crm.guard.filter.Filter;
import com.crm.guard.form.UploadedFile;
import com.crm.guard.service.api.FileService;
import com.crm.guard.service.api.SettingsService;
import com.crm.guard.webresult.WebResultTable;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

@Service
public class FileServiceImpl implements FileService {

    private final String FILES_PATH = "/WEB-INF/files";
    @Autowired
    private FileDAO fileDAO;

    @Autowired
    private SettingsService settingsService;

    @Autowired
    ServletContext servletContext;

    @Override
    @Transactional
    public boolean saveFile(UploadedFile uploadedFile) throws IOException {
        File file = new File();
        file.setClient(uploadedFile.getClient());
        file.setName(uploadedFile.getName());
        fileDAO.saveOrUpdate(file);
        saveToDisk(uploadedFile);
        return false;
    }

    @Override
    @Transactional(readOnly = true)
    public WebResultTable<File> findWebResultById(Filter filter, String id) {
        List<File> files = fileDAO.getFiles(filter, id);
        return new WebResultTable<File>(files, 0L);
    }

    @Override
    public void httpDoFile(HttpServletResponse response, String path) throws IOException {
        java.io.File file = new java.io.File(settingsService.getFilesPath() + "/" + path);
        byte[] bytes = FileUtils.readFileToByteArray(file);

        response.setContentType("application/binary");
        response.setContentLength(bytes.length);
        response.setHeader("Content-Disposition", "attachment; filename=\"" + URLEncoder.encode(file.getName(), "UTF-8") + "\"");
        FileCopyUtils.copy(bytes, response.getOutputStream());
    }

    @Override
    @Transactional
    public void delete(String path, String clientId, String fileName) throws IOException {
        java.io.File file = new java.io.File(settingsService.getFilesPath() + "/" + path);
        if (file.exists()) {
            file.delete();
        }
        if (!file.exists()) {
            File dbFile = fileDAO.getFile(clientId, fileName);
            if (dbFile != null) {
                fileDAO.delete(dbFile);
            }
        }
    }

    private void saveToDisk(UploadedFile uploadedFile) throws IOException {
        final String path = settingsService.getFilesPath();
        java.io.File file = new java.io.File(path);
        if (!file.exists()) {
            file.mkdir();
        }

        file = new java.io.File(path + "/" + uploadedFile.getClient().getId());
        if (!file.exists()) {
            file.mkdir();
        }
        java.io.File multipartFile = new java.io.File(file.getPath() + "/" + uploadedFile.getName());
        uploadedFile.getFile().transferTo(multipartFile);
    }
}
