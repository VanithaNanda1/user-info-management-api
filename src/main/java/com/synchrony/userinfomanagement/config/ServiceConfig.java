package com.synchrony.userinfomanagement.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ServiceConfig {
	
	@Value("${client-id}")
	private String clientId;
	
	@Bean
	public RestTemplate imgUrRestTemplate(RestTemplateBuilder restTemplateBuilder) {
		
		return restTemplateBuilder.defaultHeader("Authorization", "Client-ID "+clientId).build();
	};

}
