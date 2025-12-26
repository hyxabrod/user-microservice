# User Microservice

This service acts as the primary "Read Layer" and entry point for users.

## Key Responsibilities:
1.  **Kafka Streams Consumer**: Consumes `products.changelog` and maintains a local, low-latency materialization of products in **RocksDB**.
2.  **Product Query**: Serves fast `GET /api/products` requests by reading directly from the embedded RocksDB state store (Zero-Latency).
3.  **Order Initiation**: receives `POST /api/orders` requests and sends an `OrderRequest` to the `orders.request` Kafka topic (Async processing).
