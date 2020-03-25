package com.flip.listner;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.flip.event.ProductRatingEvent;
import com.flip.service.ProductService;

@Component
public class ProductRatingEventMessageListner {

	@Autowired
	private ProductService productService;
	
    @KafkaListener(topics = "${product.rating.topic.name}", containerFactory = "productRatingKafkaListenerContainerFactory")
    public void greetingListener(ProductRatingEvent productRatingEvent) {
    	productService.updateRating(productRatingEvent);
    }

}
