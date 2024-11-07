package com.fccpractice.ecomfreecodecamp.service.order;

import com.fccpractice.ecomfreecodecamp.dto.OrderDto;
import com.fccpractice.ecomfreecodecamp.model.Order;

import java.util.List;

public interface IOrderService {
    Order placeOrder(Long userId);
    OrderDto getOrder(Long orderId);
    List<OrderDto> getUserOrders(Long userId);
}