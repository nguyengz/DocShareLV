package com.example.demo.dto.response;


import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentResponse {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
 
    @Column(name="content")
    private String content;
 
    @Column(name="created_at")
    private LocalDateTime createdAt;

    private String username;

    public CommentResponse() {
    }
 

    public CommentResponse( String content, LocalDateTime createdAt) {
      
        this.content = content;
        this.createdAt = createdAt;
    }


}