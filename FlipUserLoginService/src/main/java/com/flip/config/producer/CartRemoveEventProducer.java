package com.flip.config.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.flip.event.CartGenerationEvent;

@Component
public class CartRemoveEventProducer {
	
	@Autowired
	private KafkaTemplate<String, CartGenerationEvent> cartRemoveKafkaTemplate;

	@Value(value = "${cart.removal.topic.name}")
	private String cartRemoveEventTopicName;

	public void sendMessage(CartGenerationEvent cartRemoveEvent) {
		System.out.println("Sending CartRemove Request");
		cartRemoveKafkaTemplate.send(cartRemoveEventTopicName, cartRemoveEvent);
	}
    
}
