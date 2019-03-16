/**
*@Project: cloud-web
*@Author: sam
*@Date: 2017年6月8日
*@Copyright: 2017  All rights reserved.
*/
package com.scarecrow.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author sam
 *
 */
@SpringBootApplication
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
