package org.cloud.gateway;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.protocol.RequestContent;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

public class AccessSignFilter extends ZuulFilter{

	@Override
	public Object run() {

		RequestContext ctx=RequestContext.getCurrentContext();
		HttpServletRequest request= ctx.getRequest();
		
		String token= request.getHeader("accessToken");
		return null;
	}

	@Override
	public boolean shouldFilter() {

		return true;
	}

	@Override
	public int filterOrder() {

		return 0;
	}

	@Override
	public String filterType() {

		return "pre";
	}

	
}
