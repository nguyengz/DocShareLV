package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.response.CommentResponse;

public interface CommentService {
     public List<CommentResponse> getCommentsByFileId(Long fileId);
 
    public Boolean saveComment(Long userId,Long fileId,String contten);
}
