package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.File;
import com.example.demo.model.Tag;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {
    @Query(value = "SELECT DISTINCT File.* FROM File JOIN file_tag ON File.id = file_tag.file_id JOIN tag ON file_tag.tag_id = tag.tag_id WHERE tag.tag_name LIKE %?1% ", nativeQuery=true)
    List<File> search(String keyword);
    List<File> findByCategoryId(Long status);

    @Query(value = "SELECT * FROM file ORDER BY like_file DESC LIMIT 3", nativeQuery=true)
    List<File> listTopFile();

    @Query(value = "SELECT * FROM file ORDER BY view DESC LIMIT 7", nativeQuery=true)
    List<File> listViewsFile();

    @Query("SELECT SUM(f.view) FROM File f")
    Double sumView();	
}   
