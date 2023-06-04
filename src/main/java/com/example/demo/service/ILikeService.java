package com.example.demo.service;

import com.example.demo.model.Like;
import com.example.demo.model.UserFile;

public interface ILikeService {
   
   boolean save(Long userId,Long fileId);
   boolean  deleteLikeById(UserFile id);
}
