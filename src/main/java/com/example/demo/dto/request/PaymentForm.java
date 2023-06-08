package com.example.demo.dto.request;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentForm implements Serializable{
    private String status;
    private String message;
    private String URL;
}
