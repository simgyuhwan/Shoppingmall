package com.growing.sgh.domain.item.service;

import com.growing.sgh.common.prop.FileProperties;
import com.growing.sgh.factory.dto.FileFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class FileServiceTest {

    private String path = "C:/shop/item";
    @InjectMocks FileService fileService;

    @Test
    @DisplayName("파일 등록 테스트")
    void uploadFileTest() throws IOException {
        String saveFileName = uploadFile();
        File file = new File(path + "/" + saveFileName);
        assertThat(file.exists()).isTrue();
    }

    @Test
    @DisplayName("파일 삭제 테스트")
    void deleteFileTest() throws IOException{
        String saveFileName = uploadFile();
        fileService.deleteFile(path +"/"+saveFileName);
        File file = new File(path + "/" + saveFileName);
        assertThat(file.exists()).isFalse();
    }

    private String uploadFile() throws IOException {
        MultipartFile multipartFile = createMultipartFile();
        String saveFileName = fileService.uploadFile(path,
                multipartFile.getOriginalFilename(), multipartFile.getBytes());
        return saveFileName;
    }

    private MultipartFile createMultipartFile(){
        return FileFactory.createMultipartFile();
    }
}