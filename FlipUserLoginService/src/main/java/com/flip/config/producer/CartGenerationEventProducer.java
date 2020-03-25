package com.flip.config.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.flip.event.CartGenerationEvent;

@Component
public class CartGenerationEventProducer {
	
	@Autowired
	private KafkaTemplate<String, CartGenerationEvent> cartGenerationKafkaTemplate;

	@Value(value = "${cart.generation.topic.name}")
	private String cartGenerationEventTopicName;

	public void sendMessage(CartGenerationEvent cartGenerationEvent) {
		System.out.println("Sending CartGeneration Request");
		cartGenerationKafkaTemplate.send(cartGenerationEventTopicName, cartGenerationEvent);
	}
    
    @Bean
    public CartGenerationEvent getCartGenerationEvent() {
    	return new CartGenerationEvent();
    }

}
