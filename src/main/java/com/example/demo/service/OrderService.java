package com.example.demo.service;

import java.util.Optional;

import com.example.demo.model.Order;
import com.example.demo.model.Users;

public interface OrderService {
    Order save(Order order);
    Optional<Order> findByOrderCode(String orderCode);
}
