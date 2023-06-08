package com.example.demo.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseMessage {
     private String message;
     private String status;
     private String data;

    public ResponseMessage() {
    }

    public ResponseMessage(String message) {
        this.message = message;
    }


    public ResponseMessage(String message, String status, String data) {
        this.message = message;
        this.status = status;
        this.data = data;
    }

}
