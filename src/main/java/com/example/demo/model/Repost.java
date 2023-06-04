package com.example.demo.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Repost {

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
    
    private int repostCount;
    
    private String repostContent;

    public Repost() {
    }

    public Repost( File file, Users user, int repostCount, String repostContent) {
        this.file = file;
        this.user = user;
        this.repostCount = repostCount;
        this.repostContent = repostContent;
    }
    
    
  
}