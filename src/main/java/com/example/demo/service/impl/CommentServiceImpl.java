package com.example.demo.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.response.CommentResponse;
import com.example.demo.model.Comment;
import com.example.demo.model.File;
import com.example.demo.model.Users;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.FileRepository;
import com.example.demo.repository.IUserRepository;
import com.example.demo.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService{
 
    @Autowired
    private CommentRepository commentRepository;
    
    @Autowired
    FileRepository fileRepository;

    @Autowired
    IUserRepository userRepository;
 
    @Override
    public List<CommentResponse> getCommentsByFileId(Long fileId) {
        List<Comment> comments = commentRepository.findByFileId(fileId);
        return comments.stream().map(comment -> {
           
            CommentResponse commentResponse = new CommentResponse();
            commentResponse.setId(comment.getId());
            commentResponse.setContent(comment.getContent());
            commentResponse.setCreatedAt(comment.getCreatedAt());
            commentResponse.setUsername(comment.getUser().getUsername());
            return commentResponse;
        }).collect(Collectors.toList());
    }

    
 
    @Override
    public Boolean saveComment(Long userId,Long fileId,String contten) {
        Users user = userRepository.findById(userId).orElse(null);
        File file = fileRepository.findById(fileId).orElse(null);
        if (user != null && file != null) {
            Comment comment = new Comment(file,user,contten,LocalDateTime.now());
            comment.setStatus(false);
            commentRepository.save(comment);
            return true;
        }else{
            return false;
        }
    }






   
}