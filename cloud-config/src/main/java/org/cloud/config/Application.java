package org.cloud.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.cloud.config.server.EnableConfigServer;
/**
 * cloud config
 *
 */
@Configuration
@EnableAutoConfiguration
@EnableDiscoveryClient
@EnableConfigServer
@RestController
public class Application 
{   
    //@Value("${config.name}")
    String name = "World";

    public static void main(String[] args) {
      SpringApplication.run(Application.class, args);
    }
}
