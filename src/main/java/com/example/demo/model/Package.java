package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Package {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int duration;
    private float price;
    private int dowloads;


    public Package() {
    }

    public Package(Long id, String name, int duration, float price, int dowloads) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.price = price;
        this.dowloads = dowloads;
    }



}
