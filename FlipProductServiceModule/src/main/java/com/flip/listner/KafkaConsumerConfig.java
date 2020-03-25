package com.flip.listner;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.flip.event.ProductRatingEvent;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {
	
	@Value(value = "${kafka.bootstrapAddress}")
    private String bootstrapAddress;
    
    public ConsumerFactory<String, ProductRatingEvent> productRatingConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "forgetPasswordConfig");
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JsonDeserializer<>(ProductRatingEvent.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ProductRatingEvent> productRatingKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, ProductRatingEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(productRatingConsumerFactory());
        return factory;
    }

}
