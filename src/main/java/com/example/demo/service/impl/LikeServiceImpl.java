package com.example.demo.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.File;
import com.example.demo.model.Like;
import com.example.demo.model.Users;
import com.example.demo.repository.FileRepository;
import com.example.demo.repository.IUserRepository;
import com.example.demo.repository.LikeRepository;
import com.example.demo.service.ILikeService;

@Service
public class LikeServiceImpl implements ILikeService {

    @Autowired
    LikeRepository likeRepository;

    @Autowired
    FileRepository fileRepository;

    @Autowired
    IUserRepository userRepository;

    @Override
    public int sumLike(Long id) {
        File file = fileRepository.findById(id).orElse(null);
       // List<Like> likes = file.getUsers();
        int so=1;
        return so;
    }

    @Override
    public boolean save(Long userId,Long fileId) {
        Users user = userRepository.findById(userId).orElse(null);
        File file = fileRepository.findById(fileId).orElse(null);
        if (user != null && file != null) {
            Like like = new Like();
                like.setUser(user);
                like.setFile(file);
                like.setCreatedAt(LocalDateTime.now());
                likeRepository.save(like);
                file.setLikeFile();
                fileRepository.save(file);
                return true;
        }else{
            return false;
        }
    }

    @Override
    public void deleteLikeById(Long id) {
        likeRepository.deleteById(id);
       return;
    }

  

    
}