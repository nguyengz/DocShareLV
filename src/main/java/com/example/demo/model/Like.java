package com.example.demo.model;

import java.time.LocalDateTime;

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


@Entity
@Table(name = "File_like")
public class Like {
    
    @EmbeddedId
    private UserFile id;
    
    @MapsId("fileId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id", nullable = false)
    private File file;
 
    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    

    public Like() {
    }


    public Like(File file, Users user) {
      
        this.file = file;
        this.user = user;
   
    }


    public UserFile getId() {
        return this.id;
    }

    public void setId(UserFile id) {
        this.id = id;
    }

    public File getFile() {
        return this.file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public Users getUser() {
        return this.user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
  
  
}