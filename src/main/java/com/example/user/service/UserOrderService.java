package com.example.user.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.user.kafka.OrdersRequestProducer;

@Service
public class UserOrderService {

    private final OrdersRequestProducer ordersRequestProducer;

    public UserOrderService(OrdersRequestProducer ordersRequestProducer) {
        this.ordersRequestProducer = ordersRequestProducer;
    }

    public UUID createOrder(UUID userId, UUID productId) {
        return ordersRequestProducer.send(userId, productId);
    }
}
