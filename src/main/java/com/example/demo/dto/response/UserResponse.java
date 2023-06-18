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
    private String username;
    private String avatar;
    private List<File> Files;
    private List<FriendResponse> friends;


    public UserResponse() {
    }


    public UserResponse(Long id,String username, String avatar, List<File> Files, List<FriendResponse> friendResponses) {
        this.id = id;
        this.username = username;
        this.avatar = avatar;
        this.Files = Files;
        this.friends = friendResponses;
    }
 

}