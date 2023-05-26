package com.example.demo.model;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;
import java.util.HashSet;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String fileName;
    private String fileType;
    private Long fileSize;
    private Date uploadDate;
    private Date modifyDate;
    private String description;
    private String link;
    private int view;
    private int likeFile;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Users user;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "File_tag", joinColumns = @JoinColumn(name = "File_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
    Set<Tag> tags = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy="file")     
    Set<Comment> comments;

    // @ManyToMany(mappedBy = "likeFiles")
    // private Set<Users> likes = new HashSet<>();
    @JsonIgnore
    @OneToMany(mappedBy="file")     
    Set<Like> likes;

    public File() {
    }

    public File(String fileName, String fileType, Long fileSize, String description, Users user, Category category, Set<Tag> tags) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.fileSize = fileSize;
        this.uploadDate = Calendar.getInstance().getTime();
        this.description = description;
        this.user = user;
        this.category = category;
        this.tags = tags;
    }

    public File(String fileName, String fileType, Long fileSize, String description, Users user) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.fileSize = fileSize;
        this.uploadDate = Calendar.getInstance().getTime();
        this.description = description;
        this.user = user;
    }

    public void setView() {
        this.view = this.view+1;
    }

    public void setLikeFile() {
        this.likeFile = this.likeFile+1;
    }

}
