/**
*@Project: cloud-web
*@Author: sam
*@Date: 2017年6月8日
*@Copyright: 2017  All rights reserved.
*/
package com.scarecrow.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author sam
 *
 */
@SpringBootApplication
//@EnableFeignClients
@EnableDiscoveryClient
public class CloudFrontApplication  /***extends SpringBootServletInitializer**/{
	
	   public static void main(String[] args) {
	        SpringApplication.run(CloudFrontApplication.class, args);
	    }
	/**
	    @Bean
	    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
	        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
	        jsonConverter.setSupportedMediaTypes(MediaType.parseMediaTypes("application/hal+json"));
	        ObjectMapper objectMapper = new ObjectMapper();
	        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
	        objectMapper.registerModule(new Jackson2HalModule());
	        jsonConverter.setObjectMapper(objectMapper);
	        return jsonConverter;
	    }
	    
	    @Bean
	    @LoadBalanced
	    public RestTemplate restTemplate() {
	        return new RestTemplate();
	    }
	    ***/
}
