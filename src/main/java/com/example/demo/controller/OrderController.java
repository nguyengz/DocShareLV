package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Access;
import com.example.demo.model.File;
import com.example.demo.model.Order;
import com.example.demo.service.AccessService;
import com.example.demo.service.OrderService;
import com.example.demo.utils.Views;
import com.fasterxml.jackson.annotation.JsonView;

@CrossOrigin(origins = "*")
@RequestMapping("/order")
@RestController
public class OrderController {
     @Autowired
  private OrderService orderService;

  @Autowired
  private AccessService accessService;

  @GetMapping("/list")
    @JsonView(Views.OrderInfoView.class)
    public ResponseEntity<List<Order>> getOrdersByUserIdAndStatusTrue(@RequestParam("user_id") Long userId) {
    List<Order> listOrders = orderService.getOrdersByUserIdAndStatusTrue(userId);
    return ResponseEntity.ok(listOrders);
  }

   @GetMapping("/access/list")
   
    public ResponseEntity<List<Access>> getAccessByUserId(@RequestParam("user_id") Long userId) {
    List<Access> accesses = accessService.getAccessByFileId(userId);
    return ResponseEntity.ok(accesses);
  }
}