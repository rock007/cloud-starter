package org.cloud.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.rest.RepositoryRestMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages ={ "org.cloud.backend"
        ,"org.cloud.db.sys.service","org.cloud.db.cms.service"})
@EnableJpaRepositories(basePackages ={ "org.cloud.db.sys.repository","org.cloud.db.cms.repository"})
@EntityScan(basePackages ={ "org.cloud.db.sys.entity","org.cloud.db.cms.entity"})
@EnableAutoConfiguration(exclude={RepositoryRestMvcAutoConfiguration.class})
public class BackendApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
    	
        SpringApplication.run(BackendApplication.class, args);
    }
    
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(BackendApplication.class);
    }

}
