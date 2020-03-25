package com.flip.config.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.flip.event.ForgetPasswordEvent;

@Component
public class ForgetPasswordEventProducer {
	
	@Autowired
	private KafkaTemplate<String, ForgetPasswordEvent> forgetPasswordKafkaTemplate;

	@Value(value = "${forgetPassword.request.topic.name}")
	private String forgetPasswordTopicName;

	public void sendMessage(ForgetPasswordEvent forgetPasswordEvent) {
		System.out.println("Sending forgetPassword Request");
		forgetPasswordKafkaTemplate.send(forgetPasswordTopicName, forgetPasswordEvent);
	}
    
    @Bean
    public ForgetPasswordEvent getForgetPasswordEvent() {
    	return new ForgetPasswordEvent();
    }

}
