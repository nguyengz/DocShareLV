package com.example.demo.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
    private Long price;
    private int dowloads;
    
    @JsonIgnore
    @OneToMany(mappedBy="packages")     
    Set<Order> orders;
    


    public Package() {
    }

    public Package(Long id, String name, int duration, Long price, int dowloads) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.price = price;
        this.dowloads = dowloads;
    }



}
