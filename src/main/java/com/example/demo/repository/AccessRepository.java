package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Access;
import com.example.demo.model.Order;
import com.example.demo.model.Users;

@Repository
public interface AccessRepository extends JpaRepository<Access, Long> {
  List<Access> findByUser(Users user);
  List<Access> findByUserId(Long userId);
}   
