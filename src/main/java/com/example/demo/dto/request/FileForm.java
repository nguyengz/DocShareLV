package com.example.demo.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileForm {

    private Long file_id;
    private String drive_id;


    public FileForm() {
    }

    public FileForm(Long file_id, String drive_id) {
        this.file_id = file_id;
        this.drive_id = drive_id;
    }
    
}
