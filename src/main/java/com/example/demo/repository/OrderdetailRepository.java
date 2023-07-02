
package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.demo.model.OrderDetail;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
     @Query("SELECT SUM(o.price) FROM OrderDetail o")
    Float sumPrice();
     @Query("SELECT MONTH(o.start_date), SUM(o.price) FROM OrderDetail o GROUP BY MONTH(o.start_date)")
     List<Object[]> findMonthlyTotalPrices();
}   
