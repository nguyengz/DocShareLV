package com.example.demo.service;

import java.io.IOException;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.Category;
import com.example.demo.model.File;
import com.example.demo.model.Tag;

public interface IFileService {
    String uploadFile(MultipartFile file, String filePath, boolean isPublic);
    List<Category> getAllFileCategories();
    List<File> getAllFiles();
    List<File> search(Long keyword);
    void  deleteFileById(Long id);
    void deleteFile(String id) throws Exception;
    void downloadFile(String id, OutputStream outputStream) throws IOException, GeneralSecurityException;
    Optional<File> findById(Long id); 
    List<File> getTopFile();
}
