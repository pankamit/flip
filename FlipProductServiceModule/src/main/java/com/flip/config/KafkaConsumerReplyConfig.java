package com.flip.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.flip.event.OrderReplyEvent;
import com.flip.event.OrderRequestEvent;

@EnableKafka
@Configuration
public class KafkaConsumerReplyConfig {

	@Value(value = "${kafka.bootstrapAddress}")
	private String bootstrapAddress;

	@Value("${order-request-reply-topic}")
	private String replyTopic;
	
	@Value("${kafka.consumergroup}")
	private String requstReplygroupId;

	@Bean
	public Map<String, Object> producerConfigs() {
		Map<String, Object> props = new HashMap<>();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		return props;
	}

	@Bean
	public Map<String, Object> consumerConfigs() {
		Map<String, Object> props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
//		 props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
//		 StringDeserializer.class);
//		 props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
//		 JsonDeserializer.class);
		props.put(ConsumerConfig.GROUP_ID_CONFIG, requstReplygroupId);

		return props;
	}
	
	@Bean
	  public ConsumerFactory<String, OrderRequestEvent> consumerFactory() {
	    return new DefaultKafkaConsumerFactory<>(consumerConfigs(),new StringDeserializer(),new JsonDeserializer<>(OrderRequestEvent.class));
	  }
	  
	  @Bean
	  public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, OrderRequestEvent>> kafkaListenerContainerFactory() {
	    ConcurrentKafkaListenerContainerFactory<String, OrderRequestEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
	    factory.setConsumerFactory(consumerFactory());
	    factory.setReplyTemplate(kafkaTemplate());
	    return factory;
	  }
	
	  @Bean
		public ProducerFactory<String, OrderReplyEvent> requestProducerFactory() {
			return new DefaultKafkaProducerFactory<>(producerConfigs());
		}

		@Bean
		public KafkaTemplate<String, OrderReplyEvent> kafkaTemplate() {
			return new KafkaTemplate<>(requestProducerFactory());
		}
	

//	  @Bean
//	  public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, Request>> requestListenerContainerFactory() {
//	    ConcurrentKafkaListenerContainerFactory<String, Request> factory =
//	        new ConcurrentKafkaListenerContainerFactory<>();
//	    factory.setConsumerFactory(requestConsumerFactory());
//	    factory.setReplyTemplate(replyTemplate());
//	    return factory;
//	  }
	  

	

//	@Bean
//	public ReplyingKafkaTemplate<String, OrderRequestEvent, OrderReplyEvent> replyKafkaTemplate(ProducerFactory<String, OrderRequestEvent> pf,
//			KafkaMessageListenerContainer<String, OrderReplyEvent> container) {
//		return new ReplyingKafkaTemplate<>(pf, container);
//
//	}

//	@Bean
//	public KafkaMessageListenerContainer<String, OrderReplyEvent> replyContainer(ConsumerFactory<String, OrderReplyEvent> cf) {
//		ContainerProperties containerProperties = new ContainerProperties(replyTopic);
//		return new KafkaMessageListenerContainer<>(cf, containerProperties);
//	}

//	@Bean
//	public ConsumerFactory<String, OrderReplyEvent> replyConsumerFactory() {
//		return new DefaultKafkaConsumerFactory<>(consumerConfigs(), new StringDeserializer(),
//				new JsonSerializer<OrderReplyEvent>());
//	}
//
//	@Bean
//	public KafkaMessageListenerContainer<String, OrderReplyEvent> replyListenerContainer() {
//		ContainerProperties containerProperties = new ContainerProperties(replyTopic);
//		return new KafkaMessageListenerContainer<>(replyConsumerFactory(), containerProperties);
//	}
}
