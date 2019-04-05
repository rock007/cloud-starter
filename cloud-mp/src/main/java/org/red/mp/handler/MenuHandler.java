package org.red.mp.handler;

import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

import static me.chanjar.weixin.common.api.WxConsts.MenuButtonType;


@Component
public class MenuHandler extends AbstractHandler {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
  @Override
  public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                  Map<String, Object> context, WxMpService weixinService,
                                  WxSessionManager sessionManager) {

    String msg = String.format("type:%s, event:%s, key:%s",
        wxMessage.getMsgType(), wxMessage.getEvent(),
        wxMessage.getEventKey());
    
    logger.debug("menu event:"+msg);
    if (MenuButtonType.VIEW.equals(wxMessage.getEvent())) {
     	
    	return null;
    }

    if(wxMessage.getEventKey().equals("V1001_ABOUTUS")){
		
		return WxMpXmlOutMessage.TEXT().content("新干县城市管理局运营")
		        .fromUser(wxMessage.getToUser()).toUser(wxMessage.getFromUser())
		        .build(); 
	}
    
    return WxMpXmlOutMessage.TEXT().content(msg)
        .fromUser(wxMessage.getToUser()).toUser(wxMessage.getFromUser())
        .build();
  }

}
