package com.fccpractice.ecomfreecodecamp.service.cart;

import com.fccpractice.ecomfreecodecamp.model.Cart;
import com.fccpractice.ecomfreecodecamp.model.User;

import java.math.BigDecimal;

public interface ICartService {
    Cart getCart(Long id);
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);

    Cart initializeNewCart(User user);

    Cart getCartByUserId(Long userId);
}