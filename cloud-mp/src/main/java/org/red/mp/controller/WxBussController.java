/**
*@Author: sam
*@Date: 2018年3月15日
*@Copyright: 2018  All rights reserved.
*/
package org.red.mp.controller;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.fp.prj.core.utils.StringUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;

@Controller
@RequestMapping("/buss")
public class WxBussController {

	private static final Logger logger = LoggerFactory.getLogger(WxBussController.class);

	@Value("${setting.submit_case_url}")
	private String submit_case_url ="";
	
	@Autowired
	protected RestTemplate restTemplate;
	
	@Autowired
	private WxMpService wxService;
	  
	@GetMapping("/case-submit.html")
	public String get_case_submit(Map<String, Object> model) throws Exception{
		
		WxJsapiSignature sign= wxService.createJsapiSignature("http://3dc2f330.ngrok.io/wechat/buss/case-submit.html");

		//当前时间戳
		model.put("timestamp", sign.getTimestamp());
		//随机字符串
		model.put("nonceStr", sign.getNonceStr());
		
		model.put("sign", sign.getSignature());
		
		return "/pages/case-submit";
	}
	
	/***
	 * 提交报案
	 * @param model
	 * @param upload_files 上传文件（最多三张）
	 * @param req
	 * @return
	 */
	@PostMapping("/case-submit.html")
	public String  post_case_submit(Map<String, Object> model,
			@RequestParam(value = "upload_files", required = true) MultipartFile[] upload_files, HttpServletRequest req) {

		String recDesc = req.getParameter("desc");
		String address = req.getParameter("address");
		String lng = req.getParameter("lng");
		String lat = req.getParameter("lat");
		
		if(address==null||"".equals(address)){
			
			model.put("isSuccessful", false);
			model.put("message", "案件地址不能为空");
			
			return "/pages/case-submit";
		}
		
		if(recDesc==null||"".equals(recDesc)){
			
			model.put("isSuccessful", false);
			model.put("message", "案件描述不能为空");
			
			return "/pages/case-submit";
		}
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);

		LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
		map.add("recDesc", recDesc);
		map.add("address", address);
		map.add("lng", lng);
		map.add("lat", lat);

		for(int i=0;i<upload_files.length;i++){
			
			MultipartFile f1=upload_files[i];
			
			if(f1.isEmpty()) continue;
			
			try{
				
				map.add("file", new ByteArrayResource(f1.getBytes()){
		            @Override
		            public String getFilename(){
		                return f1.getName();
		            }
		        });
				
			}catch(Exception ex){
				
				logger.error("上传文件转换错误：", ex);
			}
		   
		}
		
		HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<>(map, headers);

		try {

			ResponseEntity<String> response = restTemplate.postForEntity(submit_case_url, requestEntity, String.class);

			String jsonStr = response.getBody();

			logger.debug("提交案件返回 :" + jsonStr);

			Gson gson = new Gson();
			JsonObject jsonObject = gson.fromJson(jsonStr, JsonObject.class);

			model.put("isSuccessful", jsonObject.get("isSuccessful").getAsBoolean());
			model.put("message", jsonObject.get("message").getAsString());
			model.put("code", jsonObject.get("code").getAsInt());
			// result.put("data",
			// jsonObject.get("result").isJsonObject()?jsonObject.get("result").getAsJsonObject():jsonObject.get("result").getAsString());

		} catch (Exception ex) {

			logger.error("getUserInfo:", ex);
			model.put("isSuccessful", false);
			model.put("message", ex.getMessage());
		}
		return "/pages/case-submit";

	}
	
	
}

