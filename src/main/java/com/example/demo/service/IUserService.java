package com.example.demo.service;

import com.example.demo.model.Users;

import java.io.UnsupportedEncodingException;
import java.util.Optional;

import javax.mail.MessagingException;

public interface IUserService {
    Optional<Users> findByUsername(String name); //Tim kiem User co ton tai trong DB khong?
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    Users save(Users users);
    Boolean verify(String verificationCode);
    void register(Users user, String siteURL) throws UnsupportedEncodingException, MessagingException;
}
