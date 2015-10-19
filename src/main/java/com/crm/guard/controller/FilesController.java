package com.crm.guard.controller;

import com.crm.guard.entity.File;
import com.crm.guard.filter.Filter;
import com.crm.guard.filter.FilterRequest;
import com.crm.guard.service.api.FileService;
import com.crm.guard.webresult.WebResultTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Secured(value = {"ROLE_USER"})
@Controller
@RequestMapping("/files")
public class FilesController {

    @Autowired
    private FileService fileService;

    @PreAuthorize("hasAnyRole('ROLE_OPERATOR', 'ROLE_ADMIN')")
    @RequestMapping(value = "table/data", method = RequestMethod.GET)
    @ResponseBody
    public WebResultTable<File> dataTable(@FilterRequest Filter filter, @RequestParam(value = "id") String id) {
        return fileService.findWebResultById(filter, id);
    }

    @PreAuthorize("hasAnyRole('ROLE_OPERATOR', 'ROLE_ADMIN')")
    @RequestMapping(value = "remove/{directory}/{file}/", method = RequestMethod.POST)
    @ResponseBody
    public void deleteFile(@PathVariable String directory, @PathVariable String file) throws IOException {
        fileService.delete(directory + "/" + file, directory, file);
    }

    @PreAuthorize("hasAnyRole('ROLE_OPERATOR', 'ROLE_ADMIN')")
    @RequestMapping(value = "get/{directory}/{file}/", method = RequestMethod.GET)
    @ResponseBody
    public void getFile(@PathVariable String directory, @PathVariable String file, HttpServletResponse response) throws IOException {
        fileService.httpDoFile(response, directory + "/" + file);
    }
}
