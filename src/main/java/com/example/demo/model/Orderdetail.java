package com.example.demo.model;

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
    private Long id;
    private String start_date;
    private int end_date;
    private float price;
    private int total_price;

  @OneToOne(mappedBy = "orderDetail", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Order order;

    public OrderDetail() {
    }


    public OrderDetail( String start_date, int end_date, float price, int total_price) {
    
        this.start_date = start_date;
        this.end_date = end_date;
        this.price = price;
        this.total_price = total_price;
    }
   
}