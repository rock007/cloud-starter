package org.cloud.unified.service.api.sys;


import org.cloud.core.model.JsonBody;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@FeignClient(value = "uploadFeignService")
public interface UploadFeignService {

    @RequestMapping(value="/upload-file", method=RequestMethod.POST)
    public JsonBody<Map<String, Object>> upload_file(
            @RequestParam(value="upload_file",required=true) MultipartFile file,
            @RequestParam(value="user_id",required=false) String user_id,
            HttpServletRequest request);

    @RequestMapping("/get-file-{pid}.action")
    public ResponseEntity<byte[]> getFile(@PathVariable String pid,
                                          HttpServletResponse response,
                                          HttpServletRequest request) throws Exception;

}
