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

import com.example.demo.utils.Views;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Package {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   @JsonView(Views.OrderInfoView.class)
    private Long id;

    @JsonView(Views.OrderInfoView.class)
    private String name;

    @JsonView(Views.OrderInfoView.class)
    private int duration;

    @JsonView(Views.OrderInfoView.class)
    private Double price;

    @JsonView(Views.OrderInfoView.class)
    private int dowloads;

    @JsonView(Views.OrderInfoView.class)
    private Double storageSize;


    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "packages", cascade = CascadeType.ALL)
    Set<Order> orders;

    public Package() {
    }

    public Package(Long id, String name, int duration, Double price, int dowloads, Set<Order> orders) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.price = price;
        this.dowloads = dowloads;
        this.orders = orders;
    }

}
