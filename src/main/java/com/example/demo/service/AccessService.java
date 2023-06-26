package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Access;
import com.example.demo.model.Users;

public interface AccessService {
    Access save(Access access);
    public List<Access> findAccessByUserAndValid(Long userId,Users user);
}
