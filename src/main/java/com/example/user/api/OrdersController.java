package com.example.user.api;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.user.service.UserOrderService;

@RestController
@RequestMapping("/api/orders")
public class OrdersController {

    private final UserOrderService userOrderService;

    public OrdersController(UserOrderService userOrderService) {
        this.userOrderService = userOrderService;
    }

    @GetMapping
    public ResponseEntity<Map<String, String>> create(
            @RequestParam int userId,
            @RequestParam int productId
    ) {
        int requestId = userOrderService.createOrder(userId, productId);
        return ResponseEntity.ok(Map.of("requestId", Integer.toString(requestId)));
    }
}
