package org.cloud.authserver.config;

import org.cloud.authserver.config.security.AuthSuccessHandler;
import org.cloud.db.service.imp.AccountAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;


//@EnableWebSecurity
//@Configuration
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AccountAuthService userDetailsService;
   
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new StandardPasswordEncoder();
    }
   
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        
    	http.headers().frameOptions().disable();
    	
		http.authorizeRequests()
	    		.antMatchers("/demo").permitAll()
	    		.antMatchers("/plugins/**").permitAll()
                .antMatchers("/vendors/**").permitAll()
	    		.antMatchers("/js/**").permitAll()
	    		.antMatchers("/css/**").permitAll()
	    		.antMatchers("/images/**").permitAll()
	    		.antMatchers("/fonts/**").permitAll()
	    		.antMatchers("/home/**").permitAll()
                .antMatchers("/**.html").permitAll()
                .antMatchers("/","**.json").permitAll() 
                .antMatchers("/cms/**.html").permitAll()
                .antMatchers("/api/**").permitAll()
                .antMatchers("/app/**").permitAll()
                .antMatchers("/echo/**").permitAll()
                .anyRequest().fullyAuthenticated()
                .and()
                	.formLogin()         
                	.loginPage("/login.html")
                	.failureUrl("/login.html?error")
                	.successHandler(new AuthSuccessHandler())
                	.permitAll()
                .and()
                	.logout()
                	.logoutUrl("/logout.html")             
                	.logoutSuccessUrl("/login.html")
                	.permitAll()
                .and()
                	.exceptionHandling().accessDeniedPage("/login.html?error")
				.and()
					.csrf().ignoringAntMatchers("/api/**","/api/**/**");
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

}