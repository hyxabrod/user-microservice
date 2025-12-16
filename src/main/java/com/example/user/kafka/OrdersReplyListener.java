package com.example.user.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.example.contracts.orders.OrderReply;

@Component
public class OrdersReplyListener {

    @KafkaListener(
            topics = "orders.reply",
            containerFactory = "orderReplyListenerContainerFactory"
    )
    public void onReply(OrderReply reply) {
        System.out.println(
                "Received reply: requestId=" + reply.requestId()
                + ", status=" + reply.status()
        );
    }
}
