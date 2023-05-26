package com.example.demo.dto.response;

import com.example.demo.model.Users;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FriendResponse {

    private Long id;
    private String name;
    private String username;
    private String avatar;

    public FriendResponse() {
    }



    public FriendResponse(Users user) {
        this.id = user.getId();
        this.name = user.getName();
        this.username = user.getUsername();
        this.avatar = user.getAvatar();
    }

}
