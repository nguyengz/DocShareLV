package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.File;
import com.example.demo.model.Tag;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {
    @Query(value = "SELECT * FROM `File` WHERE CONCAT(`File`.`category_id`) LIKE %?1% ", nativeQuery=true)
    List<File> search(String keyword);
    List<File> findByCategoryId(Long status);

    @Query(value = "SELECT * FROM file ORDER BY view DESC LIMIT 3", nativeQuery=true)
    List<File> listTopFile();
}   
