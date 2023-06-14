package com.netf.netflix.Service;

import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

@Service
@Log
public class FileService {
    public String uploadFile(String uploadPath, String originalFileName,
                             byte[] fileData) throws Exception{
        UUID uuid = UUID.randomUUID();
        String extension = originalFileName.substring(originalFileName
                .lastIndexOf("."));
        String savedFileName = uuid.toString() + extension;
        String fileUpLoadFullUrl = uploadPath + "/" + savedFileName;
        FileOutputStream fos = new FileOutputStream(fileUpLoadFullUrl);
        fos.write(fileData);
        fos.close();
        return savedFileName;
    }

    public void deleteFile(String filePath) throws Exception {
        File file = new File(filePath);
        if (file.exists()) {
            if (file.delete()) {
                log.info("File deleted successfully: " + filePath);
            } else {
                log.warning("Failed to delete file: " + filePath);
            }
        } else {
            log.warning("File does not exist: " + filePath);
        }
    }

}