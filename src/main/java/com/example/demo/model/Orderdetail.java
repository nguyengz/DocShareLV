package com.example.demo.model;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime start_date;
    private LocalDateTime end_date;
    private float price;
    private int total_price;
    
    @OneToOne(mappedBy = "orderDetail", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Order order;

    public OrderDetail() {
    }

    public OrderDetail( LocalDateTime start_date, LocalDateTime end_date, float price, int total_price) {
       
        this.start_date = start_date;
        this.end_date = end_date;
        this.price = price;
        this.total_price = total_price;

    }

}