/**
*@Author: sam
*@Date: 2018年1月8日
*@Copyright: 2018  All rights reserved.
*/
package org.cloud.core.utils;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpUtil {

    public  static String get(String url){
    	
    	String responseBody="";
    	
        CloseableHttpClient httpclient = HttpClients.createDefault();
        
        try {
        	RequestConfig requestConfig = RequestConfig.custom()
        			.setSocketTimeout(10000)
        			.setConnectTimeout(15000)
        			.build();//设置请求和传输超时时间
        	
            HttpGet httpget = new HttpGet(url);
            
            httpget.setConfig(requestConfig);
            
            System.out.println("Executing request " + httpget.getRequestLine());

            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

                @Override
                public String handleResponse(
                        final HttpResponse response) throws ClientProtocolException, IOException {
                    int status = response.getStatusLine().getStatusCode();
                    if (status >= 200 && status < 300) {
                        HttpEntity entity = response.getEntity();
                        return entity != null ? EntityUtils.toString(entity) : null;
                    } else {
                    	return "";
                    }
                }

            };
            responseBody = httpclient.execute(httpget, responseHandler);
            System.out.println("----------------------------------------");
            System.out.println(responseBody);
            
        }catch(Exception ex){
        	
        	System.out.println("HttpUtil.get "+ex.getMessage());
        }
        finally {
            try {
				httpclient.close();
				
			} catch (IOException e) {

				e.printStackTrace();
			}
        }
        return responseBody;
    }
}

