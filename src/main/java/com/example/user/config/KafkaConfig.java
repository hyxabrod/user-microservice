package com.example.user.config;

import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.example.contracts.orders.OrderReply;
import com.example.contracts.orders.OrderRequest;

@EnableKafka
@Configuration
public class KafkaConfig {

        @Bean
        ProducerFactory<String, OrderRequest> orderRequestProducerFactory() {
                return new DefaultKafkaProducerFactory<>(
                                Map.of(
                                                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class,
                                                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class,
                                                JsonSerializer.ADD_TYPE_INFO_HEADERS, false));
        }

        @Bean(name = "orderRequestKafkaTemplate")
        KafkaTemplate<String, OrderRequest> orderRequestKafkaTemplate() {
                return new KafkaTemplate<>(orderRequestProducerFactory());
        }

        @Bean
        ConsumerFactory<String, OrderReply> orderReplyConsumerFactory() {
                JsonDeserializer<OrderReply> valueDeserializer = new JsonDeserializer<>(OrderReply.class);
                valueDeserializer.addTrustedPackages("com.example.contracts");

                return new DefaultKafkaConsumerFactory<>(
                                Map.of(
                                                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
                                                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, valueDeserializer),
                                new StringDeserializer(),
                                valueDeserializer);
        }

        @Bean(name = "orderReplyListenerContainerFactory")
        ConcurrentKafkaListenerContainerFactory<String, OrderReply> orderReplyListenerContainerFactory() {
                var factory = new ConcurrentKafkaListenerContainerFactory<String, OrderReply>();
                factory.setConsumerFactory(orderReplyConsumerFactory());
                return factory;
        }
}
