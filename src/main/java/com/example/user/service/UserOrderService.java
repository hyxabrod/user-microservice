package com.example.user.service;

import org.springframework.stereotype.Service;

import com.example.user.kafka.OrdersRequestProducer;

@Service
public class UserOrderService {

    private final OrdersRequestProducer ordersRequestProducer;

    public UserOrderService(OrdersRequestProducer ordersRequestProducer) {
        this.ordersRequestProducer = ordersRequestProducer;
    }

    public int createOrder(int userId, int productId) {
        return ordersRequestProducer.send(userId, productId);
    }
}
