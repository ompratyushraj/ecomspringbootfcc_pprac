package com.fccpractice.ecomfreecodecamp.repository;

import com.fccpractice.ecomfreecodecamp.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    void deleteAllByCartId(Long id);
}
