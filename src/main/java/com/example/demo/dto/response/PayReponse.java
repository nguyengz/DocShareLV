package com.example.demo.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PayReponse {
    private Long user_id;
    private Long package_id;


    public PayReponse() {
    }

    public PayReponse(Long user_id, Long package_id) {
        this.user_id = user_id;
        this.package_id = package_id;
    }

    
}