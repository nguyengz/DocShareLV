package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByOrderCode(String orderCode);
    List<Order> findByUserIdAndOrderStatusTrue(Long userId);
     List<Order> findByUserId(Long userId);
      @Query("SELECT COUNT(o) FROM Order o WHERE o.orderStatus = true")
    long countByOrderStatusTrue();
}