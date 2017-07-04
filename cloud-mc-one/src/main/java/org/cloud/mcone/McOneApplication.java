package org.cloud.mcone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan("org.cloud.db")
@EnableDiscoveryClient
@EnableZuulProxy
@EnableHystrix
//@EnableAutoConfiguration
//@EnableJpaRepositories(basePackages = "org.cloud.db")
//@EntityScan("org.cloud.db")
public class McOneApplication {

	public static void main(String[] args) {
		SpringApplication.run(McOneApplication.class, args);
	}
	
}
