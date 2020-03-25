package com.flip;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
//@EnableZipkinServer
public class FlipZipkinServerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlipZipkinServerServiceApplication.class, args);
		System.out.println("Zipkin Server Service");
	}

	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
		return builder.routes()
				.route(r -> r.path("/user-service-config/helloUser")
						.uri("http://localhost:8091/user-service-config/helloUser").id("user-service"))
				.route(r -> r.path("/product-service-config/**").uri("http://localhost:8090/product-service-config/**")
//				.route(r -> r.path("/product-service-config/helloProduct")
//						.uri("http://localhost:8090/product-service-config/helloProduct")
				.id("product-service"))
				.build();
	}

}
