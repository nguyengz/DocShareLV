package com.example.demo.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Repost;
import com.example.demo.model.UserFile;

@Repository
public interface RepostRepository extends JpaRepository<Repost,UserFile> {
    @Transactional
    void deleteByUserIdAndFileId(Long userId, Long fileId);
    Repost findByUserIdAndFileId(Long userId, Long fileId);
}   
