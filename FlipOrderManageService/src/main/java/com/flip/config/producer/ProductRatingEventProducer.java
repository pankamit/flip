package com.flip.config.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.flip.event.ProductRatingEvent;

@Component
public class ProductRatingEventProducer {
	
	@Autowired
	private KafkaTemplate<String, ProductRatingEvent> productRatingKafkaTemplate;

	@Value(value = "${product.rating.topic.name}")
	private String productRatingEventTopicName;

	public void sendMessage(ProductRatingEvent productRatingEvent) {
		System.out.println("Sending Product Rating Request");
		productRatingKafkaTemplate.send(productRatingEventTopicName, productRatingEvent);
	}
    
}
