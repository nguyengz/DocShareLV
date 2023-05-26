package com.example.demo.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentForm {
    private Long userid;
    private Long fileid;
    private String contten;
   

    public CommentForm() {
    }

    public CommentForm(Long userid, Long fileid, String contten) {
        this.userid = userid;
        this.fileid = fileid;
        this.contten = contten;
    }

}
