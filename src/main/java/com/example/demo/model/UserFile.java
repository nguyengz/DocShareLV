package com.example.demo.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

@Embeddable
public class UserFile implements Serializable {

    private Long fileId;

    private Long userId;


    public UserFile() {
    }

    public UserFile( Long fileId,Long userId) {
        this.userId = userId;
        this.fileId = fileId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

}