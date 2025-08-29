package com.example.BookStore.repository;

import com.example.BookStore.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders,Integer> {
    List<Orders> findAllById(int userId);
}
