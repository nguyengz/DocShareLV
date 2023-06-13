
package com.example.demo.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "package_id", nullable = false)
  private Package packages;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private Users user;

  // các trường khác

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_detail_id")
  private OrderDetail orderDetail;

  private boolean orderStatus;

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