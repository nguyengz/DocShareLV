package com.example.demo.service;

import com.example.demo.model.Like;

public interface ILikeService {
   int sumLike(Long id);
   boolean save(Long userId,Long fileId);
   void  deleteLikeById(Long id);
}
