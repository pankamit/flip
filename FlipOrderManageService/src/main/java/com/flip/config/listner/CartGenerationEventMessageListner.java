package com.flip.config.listner;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.flip.event.CartGenerationEvent;
import com.flip.service.CartService;

@Component
public class CartGenerationEventMessageListner {

	@Autowired
	private CartService cartService;
	
    @KafkaListener(topics = "${cart.generation.topic.name}", containerFactory = "cartGenerationEventResponseKafkaListenerContainerFactory")
    public void greetingListener(CartGenerationEvent cartGenerationEvent) {
    	cartService.addCart(cartGenerationEvent.getUserId());
    }

}
