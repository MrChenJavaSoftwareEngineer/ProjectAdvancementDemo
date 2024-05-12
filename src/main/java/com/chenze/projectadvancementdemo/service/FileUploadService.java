package com.chenze.projectadvancementdemo.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public interface FileUploadService {
    void createFile(File fileDir, File file);

    String getResult(MultipartFile file) throws IOException;

    String getString(MultipartFile imageFile) throws IOException;

    void fileUploadOfExcel(MultipartFile fileUploadOfExcel) throws IOException;
}
