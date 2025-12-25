package com.example.user.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StoreQueryParameters;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.stereotype.Service;

import com.example.contracts.products.ProductEvent;

@Service
public class ProductQueryService {

    private final StreamsBuilderFactoryBean streamsBuilderFactoryBean;

    public ProductQueryService(StreamsBuilderFactoryBean streamsBuilderFactoryBean) {
        this.streamsBuilderFactoryBean = streamsBuilderFactoryBean;
    }

    public List<ProductEvent> getAllProducts() {
        KafkaStreams streams = streamsBuilderFactoryBean.getKafkaStreams();
        if (streams == null) {
            throw new IllegalStateException("Streams not started");
        }

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
        return result;
    }
}
