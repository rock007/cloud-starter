package org.cloud.authserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * authserver
 *
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages ={"org.cloud.authserver","org.cloud.db.sys.service"})
@EnableJpaRepositories(basePackages ={ "org.cloud.db.sys.repository"})
@EntityScan(basePackages ={ "org.cloud.db.sys.entity"})
public class AuthserverApplication  
{

	public static void main(String[] args) {
		SpringApplication.run(AuthserverApplication.class, args);
	}

}
