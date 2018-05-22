package org.cloud.authserver.config;

import org.cloud.authserver.config.security.CustomAuthenticationEntryPoint;
import org.cloud.authserver.config.security.CustomLogoutSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.approval.UserApprovalHandler;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class OAuth2Configuration {

    @Configuration
    @EnableResourceServer
    protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

        @Autowired
        private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

        @Autowired
        private CustomLogoutSuccessHandler customLogoutSuccessHandler;
    	    	
        @Override
        public void configure(HttpSecurity http) throws Exception {

        	http.headers().frameOptions().disable();
        	
    		http.authorizeRequests()
                    .anyRequest().fullyAuthenticated()
                    .and()
                    	.formLogin()        
                    	.loginPage("/login.html").loginProcessingUrl("/login.action")
                    	.failureUrl("/login.html?error")
                    	.successHandler(new AuthSuccessHandler())
                    	.permitAll()
    		 .and()
             .sessionManagement()
             .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
             .and()
         			.exceptionHandling()
                     .authenticationEntryPoint(customAuthenticationEntryPoint)
                     .and()
                     	.logout()
                     	.logoutUrl("/oauth/logout")
                     	.logoutSuccessHandler(customLogoutSuccessHandler)
                 	.and()
                     	.csrf()
                     	.requireCsrfProtectionMatcher(new AntPathRequestMatcher("/oauth/authorize"))
                     	.disable()	;

        }

    }

    @Configuration
    @EnableAuthorizationServer
    protected static class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter implements EnvironmentAware {

        private static final String ENV_OAUTH = "authentication.oauth.";
        private static final String PROP_CLIENTID = "clientid";
        private static final String PROP_SECRET = "secret";
        private static final String PROP_TOKEN_VALIDITY_SECONDS = "tokenValidityInSeconds";

        private RelaxedPropertyResolver propertyResolver;

        @Autowired
        @Qualifier("authenticationManagerBean")
        private AuthenticationManager authenticationManager;

        @Autowired
    	private UserApprovalHandler userApprovalHandler;
        
    	@Autowired
    	private TokenStore tokenStore;
        
        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints)
                throws Exception {
            endpoints
                    .tokenStore(tokenStore)
                    .userApprovalHandler(userApprovalHandler)
                    .authenticationManager(authenticationManager);
        }

        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            
        	clients
                    .inMemory()
                    .withClient(propertyResolver.getProperty(PROP_CLIENTID))
                    .scopes("read", "write","trust")
                    .authorities("admin", "normal")
                    .authorizedGrantTypes("password", "refresh_token")
                    .secret(propertyResolver.getProperty(PROP_SECRET))
                    .accessTokenValiditySeconds(propertyResolver.getProperty(PROP_TOKEN_VALIDITY_SECONDS, Integer.class, 1800));
            
        }
     
        @Override
        public void setEnvironment(Environment environment) {
            this.propertyResolver = new RelaxedPropertyResolver(environment, ENV_OAUTH);
        }

    }

}
