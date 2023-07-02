
package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.example.demo.utils.Views;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "orders")
@Getter
@Setter
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @JsonView(Views.OrderInfoView.class)
  private Long id;

  @JsonView(Views.OrderInfoView.class)
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "package_id", nullable = false)
  private Package packages;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private Users user;

  // các trường khác
  @JsonView(Views.OrderInfoView.class)
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_detail_id")
  private OrderDetail orderDetail;

  @JsonView(Views.OrderInfoView.class)
  private boolean orderStatus;

  @JsonView(Views.OrderInfoView.class)
  private String orderCode;

  public Order() {
  }

  public Order(Package packages, Users user, OrderDetail orderDetail, boolean orderStatus, String orderCode) {
    this.packages = packages;
    this.user = user;
    this.orderDetail = orderDetail;
    this.orderStatus = orderStatus;
    this.orderCode = orderCode;
  }

  

}