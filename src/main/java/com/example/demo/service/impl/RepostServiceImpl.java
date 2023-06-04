package com.example.demo.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.File;
import com.example.demo.model.Repost;

import com.example.demo.model.UserFile;
import com.example.demo.model.Users;
import com.example.demo.repository.FileRepository;
import com.example.demo.repository.IUserRepository;
import com.example.demo.repository.RepostRepository;
import com.example.demo.service.IRepostService;

@Service
public class RepostServiceImpl implements IRepostService {

    @Autowired
    RepostRepository repostRepository;

    @Autowired
    FileRepository fileRepository;

    @Autowired
    IUserRepository userRepository;

    public boolean save(Long userId,Long fileId,String Content) {
        Users user = userRepository.findById(userId).orElse(null);
        File file = fileRepository.findById(fileId).orElse(null);
        if (user != null && file != null) {
            Repost repost = repostRepository.findByUserIdAndFileId(userId, fileId);
            if (repost == null) {
                repost = new Repost();
                UserFile id = new UserFile(userId, fileId);
                repost.setUser(user);
                repost.setFile(file);
                repost.setRepostContent(Content);
                repost.setRepostCount(repost.getRepostCount() + 1);
                repost.setId(id);
                repostRepository.save(repost);
                file.setRepostCount(file.getRepostCount() + 1);
                fileRepository.save(file);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

   
  
 }