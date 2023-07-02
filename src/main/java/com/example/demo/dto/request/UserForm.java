package com.example.demo.dto.request;

import javax.persistence.Lob;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserForm {

    private Long id;

    private String name;
    private String username;
    private String email;
    private String password;
    private String avatar;
    private boolean enabled;

    public UserForm() {
    }

    public UserForm(Long id, String name, String username, String email, String password, String avatar) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.avatar = avatar;
    }

}
