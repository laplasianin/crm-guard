package com.crm.guard.utils;

import org.springframework.util.FileCopyUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileUtils {

    private FileUtils() {
    }

    public static byte[] readFile(File file) throws IOException {
        FileInputStream in = new FileInputStream(file);
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            try {
                byte[] buffer = new byte[1024];
                int len;
                while ((len = in.read(buffer)) > 0)
                    out.write(buffer, 0, len);
                return out.toByteArray();
            } finally {
                out.close();
            }
        } finally {
            in.close();
        }
    }

    public static File tmpFile(String ext) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-SSS");
        return new File(PrincipalUtils.principal().getName() + dateFormat.format(new Date()) + "." + ext);
    }

    public static void doFile(HttpServletResponse response, byte[] bytes, String fileName) throws IOException {
        response.setContentType("application/binary");
        response.setContentLength(bytes.length);
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        FileCopyUtils.copy(bytes, response.getOutputStream());
    }
}
