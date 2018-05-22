package org.cloud.backend.config;

import org.cloud.backend.connect.EchoWebSocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.PerConnectionWebSocketHandler;


//@Configuration
//@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
	
		registry.addHandler(echoWebSocketHandler(), "/echo").setAllowedOrigins("*").withSockJS();
		
	}

	@Bean
	public WebSocketHandler echoWebSocketHandler() {
		
		return new PerConnectionWebSocketHandler(EchoWebSocketHandler.class);
		
	}
	
}