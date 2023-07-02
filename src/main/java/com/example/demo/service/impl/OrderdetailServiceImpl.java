package com.example.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.OrderDetail;
import com.example.demo.repository.OrderDetailRepository;
import com.example.demo.service.OrderdetailService;

@Service
public class OrderdetailServiceImpl implements OrderdetailService {

    @Autowired
    OrderDetailRepository orderdetailRepository;


    @Override
    public OrderDetail save(OrderDetail orderDetail) {

        return orderdetailRepository.save(orderDetail);
    }


    @Override
    public float sumPrice() {
        return orderdetailRepository.sumPrice();
    }

      @Override
    public List<Object[]> findMonthlyTotalPrices() {
        return orderdetailRepository.findMonthlyTotalPrices();
    }
}