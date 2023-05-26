package com.example.demo.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FollowForm {
    private Long user_id;
    private Long friend_id;


    public FollowForm() {
    }

    public FollowForm(Long user_id, Long friend_id) {
        this.user_id = user_id;
        this.friend_id = friend_id;
    }

}
