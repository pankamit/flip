package com.flip.config.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.flip.event.ForgetPasswordResponseEvent;

@Component
public class ForgetPasswordProducer {
	
	@Autowired
	private KafkaTemplate<String, ForgetPasswordResponseEvent> forgetPasswordResponseKafkaTemplate;

	@Value(value = "${forgetPassword.response.topic.name}")
	private String forgetPasswordResponseTopicName;

	public void sendMessage(ForgetPasswordResponseEvent forgetPasswordResponseEvent) {
		System.out.println("Sending forgetPassword Request");
		forgetPasswordResponseKafkaTemplate.send(forgetPasswordResponseTopicName, forgetPasswordResponseEvent);
	}
    
    @Bean
    public ForgetPasswordResponseEvent getForgetPasswordResponseEvent() {
    	return new ForgetPasswordResponseEvent();
    }

}
