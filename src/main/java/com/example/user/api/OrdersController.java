package com.example.user.api;

import java.util.Map;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.user.service.UserOrderService;

@RestController
@RequestMapping("/orders")
public class OrdersController {

    private final UserOrderService userOrderService;

    public OrdersController(UserOrderService userOrderService) {
        this.userOrderService = userOrderService;
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> create(@RequestParam UUID userId,
            @RequestParam UUID productId) {
        UUID requestId = userOrderService.createOrder(userId, productId);
        return ResponseEntity.ok(Map.of("requestId", requestId.toString()));
    }
}
