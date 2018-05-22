package org.cloud.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.rest.RepositoryRestMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableDiscoveryClient
@EnableEurekaClient
@ComponentScan(basePackages ={ "org.cloud.shop"
        ,"org.cloud.db.sys.service","org.cloud.db.shop.service"})
@EnableJpaRepositories(basePackages ={ "org.cloud.db.sys.repository","org.cloud.db.shop.repository"})
@EntityScan(basePackages ={ "org.cloud.db.sys.entity","org.cloud.db.shop.entity"})
@EnableAutoConfiguration(exclude={RepositoryRestMvcAutoConfiguration.class})
public class ShopApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
    	
        SpringApplication.run(ShopApplication.class, args);
    }
    
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ShopApplication.class);
    }

}
