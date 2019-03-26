package org.cloud.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.rest.RepositoryRestMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages ={ "org.cloud.backend"
        ,"org.cloud.db.sys.service","org.cloud.db.cms.service","org.cloud.core"})
@EnableJpaRepositories(basePackages ={ "org.cloud.db.sys.repository","org.cloud.db.cms.repository"})
@EntityScan(basePackages ={ "org.cloud.db.sys.entity","org.cloud.db.cms.entity"})
@EnableAutoConfiguration(exclude={RepositoryRestMvcAutoConfiguration.class})
public class UnifiedBackendApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
    	
        SpringApplication.run(UnifiedBackendApplication.class, args);
    }
    
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(UnifiedBackendApplication.class);
    }
    
    /****
	@Bean
	public HttpMessageConverters fastJsonHttpMessageConverters() {
		
		FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
		FastJsonConfig fastJsonConfig = new FastJsonConfig();
		fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");  
		//fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
		//fastJsonConfig.setSerializerFeatures(SerializerFeature.WriteMapNullValue); // 正常转换 null 值
		
		fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat,SerializerFeature.WriteMapNullValue,SerializerFeature.DisableCircularReferenceDetect);
		fastConverter.setFastJsonConfig(fastJsonConfig);
		HttpMessageConverter<?> converter = fastConverter;
		
		return new HttpMessageConverters(converter);
	}
	
	
	 @Bean
	 public WebMvcConfigurerAdapter webMvcConfigurerAdapter() {
		 
	        return new WebMvcConfigurerAdapter() {
	        	
	            @Override
				public void addResourceHandlers(ResourceHandlerRegistry registry) {
					
	            	registry.addResourceHandler("/upload/**").addResourceLocations("file:"+uploadRootDir+File.separator+uploadDir+File.separator);  
	            	
					super.addResourceHandlers(registry);
				}

	        };
	  }
	 ****/

}
