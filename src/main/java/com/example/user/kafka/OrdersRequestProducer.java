package com.example.user.kafka;

import java.util.concurrent.ThreadLocalRandom;

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

    public int send(int userId, int productId) {
        int requestId = ThreadLocalRandom.current().nextInt(1, Integer.MAX_VALUE);

        OrderRequest request = new OrderRequest(
                requestId,
                userId,
                productId
        );

        kafkaTemplate.send(
                TOPIC,
                String.valueOf(requestId),
                request
        );

        return requestId;
    }
}
