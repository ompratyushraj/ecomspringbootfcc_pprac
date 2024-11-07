package com.fccpractice.ecomfreecodecamp.repository;

import com.fccpractice.ecomfreecodecamp.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
}
