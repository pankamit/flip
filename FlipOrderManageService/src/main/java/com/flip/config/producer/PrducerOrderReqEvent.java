package com.flip.config.producer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flip.event.OrderReplyEvent;
import com.flip.event.OrderRequestEvent;

@Component
public class PrducerOrderReqEvent {

	@Autowired
	private ReplyingKafkaTemplate<String, OrderRequestEvent,OrderReplyEvent> kafkaTemplate;
	
	@Value("${order-request-topic}")
	String requestTopic;
	
	@Value("${order-request-reply-topic}")
	String requestReplyTopic;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	public OrderReplyEvent senndMessage(OrderRequestEvent orderRequestEvent) throws Exception {
				
		ProducerRecord<String, OrderRequestEvent> record = new ProducerRecord<String, OrderRequestEvent>(requestTopic, orderRequestEvent);
		// set reply topic in header
		record.headers().add(new RecordHeader(KafkaHeaders.REPLY_TOPIC, requestReplyTopic.getBytes()));
		// post in kafka topic
		RequestReplyFuture<String, OrderRequestEvent, OrderReplyEvent> sendAndReceive = kafkaTemplate.sendAndReceive(record);

		OrderReplyEvent reply = new OrderReplyEvent();
		
//		sendAndReceive.addCallback(new ListenableFutureCallback<ConsumerRecord<String, OrderReplyEvent>>() {
//		      @Override
//		      public void onSuccess(ConsumerRecord<String, OrderReplyEvent> result) {
//		        // get consumer record value
//		    	  OrderReplyEvent  reply1 = result.value();
//		        System.out.println("Reply: " + reply1.toString());
//		        return reply1;
//		      }
//
//			@Override
//			public void onFailure(Throwable ex) {
//				
//				
//			}
//		  });
		
		
		// confirm if producer produced successfully
			SendResult<String, OrderRequestEvent> sendResult = sendAndReceive.getSendFuture().get();
		
		//print all headers
		sendResult.getProducerRecord().headers().forEach(header -> System.out.println(header.key() + ":" + header.value().toString()));
		
		
		// get consumer record
		ConsumerRecord<String, OrderReplyEvent> consumerRecord = sendAndReceive.get();

		
		reply = consumerRecord.value();
		
		return reply;
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	//	OrderReplyEvent orderReplyEvent = new OrderReplyEvent();
		
		//String orderReplyParseString = objectMapper.writeValueAsString(consumerRecord.value());

		
//		JsonNode jsonNodeRoot = objectMapper.readTree(orderReplyParseString);
//		orderReplyEvent.setReply(jsonNodeRoot.get("reply").asText());
//		orderReplyEvent.setReplyInfo(jsonNodeRoot.get("replyInfo").asText());
		
		//return consumerRecord.value();
		
//		Object objVal = consumerRecord.value();
//		
//		OrderReplyEvent orderReplyEvent = (OrderReplyEvent)objVal;
		
//		OrderReplyEvent orderReplyEvent = objectMapper.readValue(orderReplyParseString,
//				OrderReplyEvent.class);
		
//		OrderReplyEvent orderReplyEvent = objectMapper.readValue(consumerRecord.value().toString(),
//				OrderReplyEvent.class);

		
//		orderReplyEvent.setReply(value.getReply().toString());
		//return orderReplyEvent;
				
		
		// return consumer value
		
	//	return orderReplyEvent;
				
	}
}
