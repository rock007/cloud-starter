package org.cloud.backend.system;

import org.cloud.backend.system.config.ApplicationRefreshEventListener;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages ={ "org.cloud.backend.system"})
@MapperScan("org.cloud.backend.system.dao.sys.mapper")
@EnableAutoConfiguration
public class SystemApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
    	
        SpringApplication app = new SpringApplication(SystemApplication.class);
        app.addListeners(new ApplicationRefreshEventListener());
        app.run(args);
    }
    
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SystemApplication.class);
    }

}