
package org.cloud.backend.connect;

import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class EchoWebSocketHandler extends TextWebSocketHandler {

	private static Logger logger = LoggerFactory.getLogger(EchoWebSocketHandler.class);

	private static final AtomicInteger clientIds = new AtomicInteger(0);
	
	private final int id;
	
	public EchoWebSocketHandler() {
		this.id = clientIds.getAndIncrement();
	}
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) {
		logger.debug("新连接 " + this);
		
		try{
			//ClientManager
			//			.broadcast(String.format("{'type': 'join','data':[%s]}", "欢迎新客户加入聊天"));
			
			//连接成功
			session.sendMessage(new TextMessage(String.format("{\"msg\": \"连接成功，返回\",\"sessionid\":\"%s\"}", session.getId())));
			
		}catch(Exception ex){
			logger.error("连接出现异常", ex);
		}
		
	}

	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message)
			throws Exception {
		
		String echoMessage = message.getPayload();
		logger.debug(echoMessage);
		
		
	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception)
			throws Exception {
		
		try{
			session.close(CloseStatus.SERVER_ERROR);
			
		}catch(Exception ex){
			logger.error("handleTransportError", ex);
		}
	}

}
