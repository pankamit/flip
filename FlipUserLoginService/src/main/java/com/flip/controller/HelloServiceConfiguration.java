package com.flip.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AvailabilityFilteringRule;
import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.PingUrl;

@RequestMapping("/user-service")
public class HelloServiceConfiguration {
	
	@Autowired
    IClientConfig ribbonClientConfig;
    @Bean
    public IPing ribbonPing(IClientConfig config) {
        return new PingUrl();
    }
    @Bean
    public IRule ribbonRule(IClientConfig config) {
        return new AvailabilityFilteringRule();
    }

}
