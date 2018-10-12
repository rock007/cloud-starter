package org.cloud.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Api
 */
@SpringBootApplication
//@EnableDiscoveryClient
@ComponentScan(basePackages ={"org.cloud.api","org.cloud.db.sys.service"})
@EnableJpaRepositories(basePackages ={ "org.cloud.db.sys.repository"})
@EntityScan(basePackages ={ "org.cloud.db.sys.entity"})
public class ApiApplication extends SpringBootServletInitializer
{

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ApiApplication.class);
    }

}
