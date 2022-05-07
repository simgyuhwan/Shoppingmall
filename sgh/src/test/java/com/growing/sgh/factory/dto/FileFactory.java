package com.growing.sgh.factory.dto;

import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class FileFactory {
    public static MultipartFile createMultipartFile(){
        return new MockMultipartFile("test1", "test1.PNG", MediaType.IMAGE_PNG_VALUE,
                "test1".getBytes(StandardCharsets.UTF_8));
    }

    public static List<MultipartFile> createMultipartFileList(){
        return List.of(
                new MockMultipartFile("test1", "test1.PNG", MediaType.IMAGE_PNG_VALUE,
                        "test1".getBytes(StandardCharsets.UTF_8)),
                        new MockMultipartFile("test2", "test2.PNG", MediaType.IMAGE_PNG_VALUE,
                                "test2".getBytes(StandardCharsets.UTF_8))
        );
    }
}
