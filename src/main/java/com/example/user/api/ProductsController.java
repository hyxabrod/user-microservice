package com.example.user.api;

import java.util.ArrayList;
import java.util.List;

import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StoreQueryParameters;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.contracts.products.ProductEvent;

@RestController
public class ProductsController {

    private final StreamsBuilderFactoryBean streamsBuilderFactoryBean;

    public ProductsController(StreamsBuilderFactoryBean streamsBuilderFactoryBean) {
        this.streamsBuilderFactoryBean = streamsBuilderFactoryBean;
    }

    @GetMapping("/api/products")
    public ResponseEntity<List<ProductEvent>> getAllProducts() {
        KafkaStreams streams = streamsBuilderFactoryBean.getKafkaStreams();
        if (streams == null) {
            return ResponseEntity.internalServerError().build();
        }

        try {
            ReadOnlyKeyValueStore<Integer, ProductEvent> store = streams.store(
                    StoreQueryParameters.fromNameAndType(
                            "products-store",
                            QueryableStoreTypes.keyValueStore()));

            List<ProductEvent> result = new ArrayList<>();
            try (KeyValueIterator<Integer, ProductEvent> all = store.all()) {
                while (all.hasNext()) {
                    result.add(all.next().value);
                }
            }

            return ResponseEntity.ok(result);
        } catch (org.apache.kafka.streams.errors.InvalidStateStoreException | IllegalStateException e) {
            // Store not ready yet or streams in ERROR/NOT_RUNNING state
            return ResponseEntity.status(org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE).build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
