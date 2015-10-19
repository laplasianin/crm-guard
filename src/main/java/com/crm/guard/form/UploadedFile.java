package com.crm.guard.form;

import com.crm.guard.entity.Client;
import org.springframework.web.multipart.MultipartFile;

public class UploadedFile {

    private String name;

    private Client client;

    private MultipartFile file;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
