package org.cloud.unified;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringCloudApplication
@ComponentScan(basePackages ={ "org.cloud.unified"
        ,"org.cloud.db.sys.service","org.cloud.db.cms.service","org.cloud.db.shop.service"})
@EnableJpaRepositories(basePackages ={ "org.cloud.db.sys.repository","org.cloud.db.cms.repository","org.cloud.db.shop.repository"})
@EntityScan(basePackages ={ "org.cloud.db.sys.entity","org.cloud.db.cms.entity","org.cloud.db.shop.entity"})
public class UnifiedServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UnifiedServiceApplication.class, args);
	}
	
}
