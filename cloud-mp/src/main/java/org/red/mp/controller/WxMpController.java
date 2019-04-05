/**
*@Author: sam
*@Date: 2018年3月15日
*@Copyright: 2018  All rights reserved.
*/
package org.red.mp.controller;

import java.io.File;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class WxMpController {

	private static final Logger logger = LoggerFactory.getLogger(WxMpController.class);

	
	@GetMapping("/MP_verify_guWH5HS6VvuBWfMc.txt")
	public ResponseEntity<byte[]> validTextPage() throws Exception{
		
		HttpHeaders headers = new HttpHeaders();  
		headers.setContentType(MediaType.TEXT_PLAIN);
		
	    return new ResponseEntity<byte[]>("guWH5HS6VvuBWfMc".getBytes(),  
	                                      headers, HttpStatus.OK);  
	}
	
	@GetMapping("/{fileName}")
	public ResponseEntity<byte[]> go2file(@PathVariable String fileName) throws Exception{
		
		String fullPath="";
		HttpHeaders headers = new HttpHeaders();  
		headers.setContentType(MediaType.ALL);
		
		String ext = FilenameUtils.getExtension(fullPath).toLowerCase();
    	
    	if(ext.equals("gif")){
    		 
    		headers.setContentType(MediaType.IMAGE_GIF);
    		 
    	}else if(ext.equals("jpg")){
    		
    		headers.setContentType(MediaType.IMAGE_JPEG);
    		
    	}else if(ext.equals("png")){
    		
    		headers.setContentType(MediaType.IMAGE_PNG);
    		
    	}else if(ext.equals("txt")){
    		
    		headers.setContentType(MediaType.TEXT_PLAIN);
    		
    	}
    	
	    if(fullPath.equals("")){
	    	
	    	return new ResponseEntity<byte[]>(new byte[]{},  
                    headers, HttpStatus.NOT_FOUND);
	    }
	    
	    return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(new File(fullPath)),  
	                                      headers, HttpStatus.OK);  
	}
		
}

