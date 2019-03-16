package org.cloud.shop;

import org.cloud.db.shop.enumType.RoleType;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.rest.RepositoryRestMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.text.SimpleDateFormat;

@SpringBootApplication
//@EnableDiscoveryClient
//@EnableFeignClients({"org.cloud.unified.service.api.shop","org.cloud.unified.service.api.sys"})
@ComponentScan(basePackages ={ "org.cloud.shop"})
//@EnableJpaRepositories(basePackages ={ "org.cloud.db.sys.repository","org.cloud.db.shop.repository"})
//@EntityScan(basePackages ={ "org.cloud.db.sys.entity","org.cloud.db.shop.entity"})
@EnableAutoConfiguration(exclude={RepositoryRestMvcAutoConfiguration.class,
        DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class})
public class ShopApplication extends SpringBootServletInitializer {

    public static String CON_SESSION_LOGIN="56WB_SHOP_SESSION";

    public static String CON_SESSION_PAY_CARTS="SHOP_FUCK_PAY_CARTS";

    public static String CON_SESSION_PAY_ADDRESS="SHOP_FUCK_PAY_ADDRESS";

    private static SimpleDateFormat sd = new SimpleDateFormat("yyyyMMddHHmmss");

    public static String basePath="/";


    public static void main(String[] args) {
    	
        SpringApplication.run(ShopApplication.class, args);
    }
    
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ShopApplication.class);
    }

    public static String GetCurDT(){
        return sd.format(new java.util.Date());
    }

    public static UserDetails getCurUser(){

        UserDetails curUser=null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            curUser = (UserDetails)principal;
        }
        return curUser;
    }

    public static RoleType getCurUserRole(){

        UserDetails curUser=getCurUser();

        String text= curUser.getAuthorities().iterator().next().getAuthority();

        return RoleType.valueOf(text);
    }

}
