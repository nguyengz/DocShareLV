package com.example.demo.service;

import java.util.List;

import com.example.demo.model.OrderDetail;
import com.example.demo.model.Users;

public interface OrderdetailService {
    OrderDetail save(OrderDetail orderDetail);
    float sumPrice();
    List<Object[]> findMonthlyTotalPrices();
}