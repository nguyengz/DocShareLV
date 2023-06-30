package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Package;

@Repository
public interface PackageRepository extends JpaRepository<Package, Long> {
     List<Package> findByActiveTrue();
    
//   @Query("SELECT package.id,package.name, COUNT(orders.id) FROM package JOIN orders ON package.id = orders.package_id GROUP BY package.id;")
//    List<Object[]> getOrderCountByPackage();
    }   
