package org.cloud.cms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.rest.RepositoryRestMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages ={ "org.cloud.cms"
        ,"org.cloud.db.sys.service","org.cloud.db.cms.service","org.cloud.core"})
@EnableJpaRepositories(basePackages ={ "org.cloud.db.sys.repository","org.cloud.db.cms.repository"})
@EntityScan(basePackages ={ "org.cloud.db.sys.entity","org.cloud.db.cms.entity"})
@EnableAutoConfiguration(exclude={RepositoryRestMvcAutoConfiguration.class})
public class CmsApplication {

    public static void main(String[] args) {
    	
        SpringApplication.run(CmsApplication.class, args);
    }

}
