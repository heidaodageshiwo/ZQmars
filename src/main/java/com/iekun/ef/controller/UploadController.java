package com.iekun.ef.controller;

import com.alibaba.fastjson.JSONObject;
import com.iekun.ef.service.StorageService;
import com.iekun.ef.util.Md5;
import com.iekun.ef.util.TimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

/**
 * Created by feilong.cai on 2016/11/21.
 */

@Controller
public class UploadController {

    private static Logger logger = LoggerFactory.getLogger(UploadController.class);

    @Autowired
    private StorageService storageService;

    @RequestMapping(value = "/uploadDvVerFile", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject uploadDvVerFile( @RequestParam("uploadFile") MultipartFile uploadFile ) {

    	JSONObject jsonObject;
    	logger.info("getOriginFilename: " + uploadFile.getOriginalFilename());
    	String originFileName = uploadFile.getOriginalFilename();
    	int  index = originFileName.lastIndexOf(".");
    	String ext = originFileName.substring(index+1);
    	if (ext.equals("zip"))
    	{
    		jsonObject = storageService.storeTempFile(uploadFile);
    	}
    	else
    	{
    		 jsonObject = new JSONObject();
    		 jsonObject.put("status", false);
             jsonObject.put("message", "文件格式错误,请上传zip后缀格式文件");
    	}

    	return jsonObject;
    }
    
    @RequestMapping(value = "/uploadSqlFile", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject uploadSqlFile( @RequestParam("uploadFile") MultipartFile uploadFile ) {

    	JSONObject jsonObject;
    	logger.info("getOriginFilename: " + uploadFile.getOriginalFilename());
    	String originFileName = uploadFile.getOriginalFilename();
    	int  index = originFileName.lastIndexOf(".");
    	String ext = originFileName.substring(index+1);
    	if (ext.equals("sql"))
    	{
    		jsonObject = storageService.storeTempFile(uploadFile);
    	}
    	else
    	{
    		 jsonObject = new JSONObject();
    		 jsonObject.put("status", false);
             jsonObject.put("message", "文件格式错误,请上传sql后缀格式文件");
    	}

    	return jsonObject;
    }
    
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject uploadFile( @RequestParam("uploadFile") MultipartFile uploadFile ) {

    	JSONObject jsonObject;
    	logger.info("getOriginFilename: " + uploadFile.getOriginalFilename());
     	String originFileName = uploadFile.getOriginalFilename();
    	int  index = originFileName.lastIndexOf(".");
    	String ext = originFileName.substring(index+1);
    	//if (ext.equals("lic"))
		if (ext.equals("zip"))
    	{
    		jsonObject = storageService.storeTempFile(uploadFile);
    	}
    	else
    	{
    		 jsonObject = new JSONObject();
    		 jsonObject.put("status", false);
             //jsonObject.put("message", "文件格式错误,请上传lic后缀格式文件");
			jsonObject.put("message", "文件格式错误,请上传zip后缀格式文件");
    	}

    	return jsonObject;
    }

	@RequestMapping(value = "/uploadLicFile", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject uploadLicFile( @RequestParam("uploadFile") MultipartFile uploadFile ) {

		JSONObject jsonObject;
		logger.info("getOriginFilename: " + uploadFile.getOriginalFilename());
		String originFileName = uploadFile.getOriginalFilename();
		int  index = originFileName.lastIndexOf(".");
		String ext = originFileName.substring(index+1);
		if (ext.equals("lic"))
		{
			jsonObject = storageService.storeTempFile(uploadFile);
		}
		else
		{
			jsonObject = new JSONObject();
			jsonObject.put("status", false);
			jsonObject.put("message", "文件格式错误,请上传lic后缀格式文件");
		}

		return jsonObject;
	}

    @RequestMapping(value = "/uploadImage", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject upload( @RequestParam("file") MultipartFile file, HttpServletRequest request ) {

        JSONObject jsonObject = new JSONObject();
        JSONObject dataObject = new JSONObject();

        if (file.isEmpty()) {
            jsonObject.put("status", false);
            jsonObject.put("message", "上传失败");
            return  jsonObject;
        }
        
        //JSONObject jsonObject;
    	logger.info("getOriginFilename: " + file.getOriginalFilename());
       	String originFileName = file.getOriginalFilename();
    	int  index = originFileName.lastIndexOf(".");
    	String ext = originFileName.substring(index+1);
    	if (ext.equals("jpg")||ext.equals("png"))
    	{
		  try {

	            String path= request.getSession().getServletContext().getRealPath("/");
	            Path uploadImgPath =  Paths.get( path +  "WEB-INF/classes/static/upload/img");
	            Files.createDirectories(uploadImgPath);

	            String originalFilename = file.getOriginalFilename();
	            String prefix = originalFilename.substring(originalFilename.lastIndexOf(".")+1);

	            String nowTime = TimeUtils.timeFormatterStr.format( new Date());
	            Md5 md5 = new Md5();
	            String filename = md5.getMD5ofStr(nowTime + originalFilename ) + "." + prefix;

	            Files.copy(file.getInputStream(), uploadImgPath.resolve( filename ) );

	            dataObject.put("orgFilename",originalFilename );
	            dataObject.put("filename",filename );
	            dataObject.put("url", "/upload/img/" + filename );

	            jsonObject.put("status", true);
	            jsonObject.put("message", "上传成功");
	            jsonObject.put("data", dataObject );

	        } catch ( IOException e) {
	            e.printStackTrace();
	            jsonObject.put("status", false);
	            jsonObject.put("message", e.getMessage());
	        }
    	}
    	else
    	{
    		 jsonObject = new JSONObject();
    		 jsonObject.put("status", false);
             jsonObject.put("message", "文件格式错误,请上传png或jpg后缀格式文件");
    	}

    	return jsonObject;
    }

}
