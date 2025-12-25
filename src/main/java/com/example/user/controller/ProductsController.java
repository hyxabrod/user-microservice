package com.example.user.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.contracts.products.ProductEvent;
import com.example.user.service.ProductQueryService;

@RestController
public class ProductsController {

    private final ProductQueryService service;

    public ProductsController(ProductQueryService service) {
        this.service = service;
    }

    @GetMapping("/api/products")
    public ResponseEntity<List<ProductEvent>> getAllProducts() {
        try {
            return ResponseEntity.ok(service.getAllProducts());
        } catch (IllegalStateException | org.apache.kafka.streams.errors.InvalidStateStoreException e) {
            return ResponseEntity.status(org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE).build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
