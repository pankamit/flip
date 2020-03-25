package com.flip.config;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import com.flip.event.OrderReplyEvent;
import com.flip.event.OrderRequestEvent;

@Component
public class ConsumerRequestEvent {

	@KafkaListener(topics = "${order-request-topic}" ,containerFactory = "kafkaListenerContainerFactory")
	@SendTo
	public OrderReplyEvent listen(OrderRequestEvent request) throws InterruptedException {

		System.out.println("In Listner : "+request.getRequstData());
		OrderReplyEvent orderReplyEvent = new OrderReplyEvent();
		orderReplyEvent.setReply("Reply Done");
		orderReplyEvent.setReplyInfo("Yes");
		return orderReplyEvent;
	}

}
