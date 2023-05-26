package com.example.demo.dto.response;


import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.example.demo.model.File;
import com.example.demo.model.Users;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {
 
   
    private Long id;
    private String name;
    private String username;
    private String email;
    private String password;
    private String avatar;
    private List<File> Files;
    private List<FriendResponse> friends;


    public UserResponse() {
    }


    public UserResponse(Long id, String name, String username, String email, String password, String avatar, List<File> Files, List<FriendResponse> friendResponses) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.avatar = avatar;
        this.Files = Files;
        this.friends = friendResponses;
    }
 

}