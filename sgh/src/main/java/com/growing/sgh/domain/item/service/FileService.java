package com.growing.sgh.domain.item.service;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@Service
@Slf4j
public class FileService {
    public String uploadFile(String uploadPath, String originalFileName, byte[] fileDate) throws IOException {
        UUID uuid = UUID.randomUUID();
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String saveFileName = uuid.toString() + extension;
        String fileUploadFullUrl = uploadPath + "/" + saveFileName;
        FileOutputStream fos = new FileOutputStream(fileUploadFullUrl);

        fos.write(fileDate);
        log.info(fileUploadFullUrl + "경로에 파일을 등록했습니다. ");
        fos.close();
        return saveFileName;
    }
    public void deleteFile(String filePath){
        File deleteFile = new File(filePath);
        if(deleteFile.exists()){
            deleteFile.delete();
            log.info(filePath + "파일을 삭제하였습니다.");
        }else {
            log.info("파일이 존재하지 않습니다.");
        }

    }
}
