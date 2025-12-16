package com.example.user.kafka;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.example.contracts.orders.OrderRequest;

@Component
public class OrdersRequestProducer {

    private static final String TOPIC = "orders.request";

    private final KafkaTemplate<String, OrderRequest> kafkaTemplate;

    public OrdersRequestProducer(
            @Qualifier("orderRequestKafkaTemplate") KafkaTemplate<String, OrderRequest> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public UUID send(UUID userId, UUID productId) {
        UUID requestId = UUID.randomUUID();

        OrderRequest request = new OrderRequest(
                requestId,
                userId,
                productId
        );

        kafkaTemplate.send(
                TOPIC,
                userId.toString(),
                request
        );

        return requestId;
    }
}
