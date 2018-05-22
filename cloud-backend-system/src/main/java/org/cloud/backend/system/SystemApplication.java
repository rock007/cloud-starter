package org.cloud.backend.system;

import org.cloud.backend.system.config.ApplicationRefreshEventListener;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackages ={ "org.cloud.backend.system"})
@MapperScan("org.cloud.backend.system.dao.sys.mapper")
@EnableAutoConfiguration
public class SystemApplication {

    public static void main(String[] args) {

        SpringApplication app = new SpringApplication(SystemApplication.class);
        app.addListeners(new ApplicationRefreshEventListener());
        app.run(args);
    }

}
