package org.cloud.unified.service.core.sys;


import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.cloud.core.model.JsonBody;
import org.cloud.core.utils.DateUtil;
import org.cloud.core.utils.StringUtil;
import org.cloud.db.sys.entity.SysUser;
import org.cloud.db.sys.entity.UploadFile;
import org.cloud.db.sys.service.UploadFileService;
import org.cloud.unified.service.api.sys.UploadFeignService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UploadFeignServiceImp implements UploadFeignService {


    private static final Logger logger = LoggerFactory.getLogger(UploadFeignServiceImp.class);

    @Value("${application.uploadDir}")
    private String uploadDir = "upload";

    @Value("${application.uploadRootDir}")
    private String uploadRootDir = "d:\files_dir";

    @Autowired
    UploadFileService uploadFileService;

    @Override
    public JsonBody<Map<String, Object>> upload_file(
            @RequestParam(value="upload_file",required=true) MultipartFile file,
            @RequestParam(value="user_id",required=false) String user_id,
            HttpServletRequest request){

        JsonBody<Map<String, Object>> JsonBody;

        Map<String, Object>  resultMap=new HashMap<String,Object>();

        UploadFile oneFile= saveUploadFile(file, request,user_id);

        if(oneFile!=null){
            resultMap.put("file_name", oneFile.getFile_name());
            resultMap.put("file_url", oneFile.getPath());
            resultMap.put("file_id", oneFile.getId());

            JsonBody=new JsonBody<>(1,"文件上传成功",resultMap);

        }else{

            JsonBody=new JsonBody<>(-1,"文件上传失败",resultMap);
        }

        return JsonBody;
    }

    private UploadFile saveUploadFile( MultipartFile file,
                                       HttpServletRequest request ,String userId) {

        UploadFile oneFile=new UploadFile();
        try {

            String subDir=DateUtil.getDate(new Date(),DateUtil.yyyymmdd_sd);

            if (file.isEmpty()) {

                return null;
            }

            String name=file.getOriginalFilename();
            String ext = FilenameUtils.getExtension(name);
            String secName=StringUtil.makeUid()+"."+ext;
            String path=uploadDir+File.separator+subDir+File.separator+secName;
            String path2=uploadDir+"/"+subDir+"/"+secName;

            String root =uploadRootDir;

            File parentDir=new File( root+File.separator+uploadDir+File.separator+subDir+File.separator);

            if(!parentDir.exists())parentDir.mkdirs();

            byte[] bytes = file.getBytes();
            BufferedOutputStream stream =
                    new BufferedOutputStream(new FileOutputStream(new File(root+File.separator+path)));
            stream.write(bytes);
            stream.close();

            oneFile.setStatus(0);
            oneFile.setCreate_date(new Date());
            oneFile.setFile_name(name);
            oneFile.setFile_size(bytes.length);
            oneFile.setSec_name(secName);

            oneFile.setFile_ext(ext);

            oneFile.setPath(path2);

            if(userId!=null&&!"".equals(userId))
                oneFile.setCreate_user(userId);

            oneFile=uploadFileService.save(oneFile);

        } catch (Exception e) {
            logger.error("保存上传文件失败", e);
        }

        return oneFile;
    }

    @Override
    public ResponseEntity<byte[]> getFile(@PathVariable String pid,
                                                  HttpServletResponse response,
                                                  HttpServletRequest request) throws Exception{

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);

        String root =uploadRootDir;
        String fullPath="",subPath="";

        UploadFile uploadFile=uploadFileService.findById(pid);

        if(uploadFile!=null){

            subPath=uploadFile.getPath();

            fullPath=root+File.separator+subPath;

            if(new File(fullPath).exists()==false){

                return new ResponseEntity<byte[]>(new byte[]{},
                        headers, HttpStatus.NOT_FOUND);
            }

            String ext = FilenameUtils.getExtension(fullPath).toLowerCase();

            if(ext.equals("gif")){

                headers.setContentType(MediaType.IMAGE_GIF);

            }else if(ext.equals("jpg")){

                headers.setContentType(MediaType.IMAGE_JPEG);

            }else if(ext.equals("png")){

                headers.setContentType(MediaType.IMAGE_PNG);

            }
        }

        if(fullPath.equals("")){

            return new ResponseEntity<byte[]>(new byte[]{},
                    headers, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(new File(fullPath)),
                headers, HttpStatus.OK);
    }
    
}
