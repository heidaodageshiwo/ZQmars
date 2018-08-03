package com.iekun.ef.service;

import com.alibaba.fastjson.JSONObject;
import com.iekun.ef.exception.StorageException;
import com.iekun.ef.util.Md5;
import com.iekun.ef.util.TimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

/**
 * Created by  feilong.cai on 2016/12/3.
 */

@Service
public class StorageService {

    private static Logger logger = LoggerFactory.getLogger(StorageService.class);

    @Value(" ${storage.basePath}")
    private String basePathStr;
    @Value(" ${storage.tempPath}")
    private String tempPathStr;
    @Value(" ${storage.licensePath}")
    private String licensePathStr;
    @Value(" ${storage.upgradePath}")
    private String upgradePathStr;

    private Path  tempPath;
    private Path  licensePath;
    private Path  upgradePacketPath;

    @PostConstruct
    public void init() {
        this.tempPath = Paths.get( basePathStr.trim() + tempPathStr.trim() );
        this.licensePath = Paths.get( basePathStr.trim() + licensePathStr.trim() );
        this.upgradePacketPath = Paths.get( basePathStr.trim() + upgradePathStr.trim() );

        try {
            Files.createDirectories(tempPath);
            Files.createDirectories(licensePath);
            Files.createDirectories(upgradePacketPath);
        } catch (IOException e) {
            e.printStackTrace();
            throw new StorageException("Could not initialize storage", e);
        }
    }

    public void deleteAllTempFile() {
        FileSystemUtils.deleteRecursively(tempPath.toFile());
    }

    public JSONObject storeTempFile( MultipartFile file ) {

        JSONObject jsonObject = new JSONObject();
        JSONObject dataObject = new JSONObject();


        try {
            if (file.isEmpty()) {
                jsonObject.put("status", false);
                jsonObject.put("message", "上传失败");
                return  jsonObject;
//                throw new StorageException("Failed to store empty file " + file.getOriginalFilename());
            }

            String nowTime = TimeUtils.timeFormatterStr.format( new Date());
            String originalFilename = file.getOriginalFilename();
            Md5 md5 = new Md5();
            String filename = md5.getMD5ofStr(nowTime + originalFilename );

            Files.copy(file.getInputStream(), this.tempPath.resolve( filename ));

            dataObject.put("originalFilename", originalFilename);
            dataObject.put("filename",filename );

            jsonObject.put("status", true);
            jsonObject.put("message", "上传成功");
            jsonObject.put("data", dataObject );

        } catch (IOException e) {
            jsonObject.put("status", false);
            jsonObject.put("message", "上传失败");
//            throw new StorageException("Failed to store file " + file.getOriginalFilename(), e);
        }

        return jsonObject;
    }
 
    
    public String moveTempFile2LicensePath( String filename, String originalFilename, String deviceSN ){

        String curLicensePathStr = basePathStr.trim() + licensePathStr.trim();
        String saveFileName = originalFilename;
        if( deviceSN.isEmpty() ) {
            //系统许可文件
            curLicensePathStr += "/system";
            saveFileName = "license.lic";
        } else {
            //设备许可文件
            curLicensePathStr += "//" + deviceSN;
        }

        String tempFilePath = basePathStr.trim() + tempPathStr.trim() + "/" + filename;

        Path currLicensePath = Paths.get( curLicensePathStr );

        try {

            File tempFile = new File(tempFilePath);
            if(!tempFile.exists()){
                throw new StorageException("Failed to temp file not exist：" + filename );
            }

            Files.createDirectories(currLicensePath);

            Files.copy( tempFile.toPath() , currLicensePath.resolve( saveFileName ), REPLACE_EXISTING);


        } catch ( IOException e ) {
            throw new StorageException("Failed to store file " + saveFileName, e);
        }
        
        return curLicensePathStr;

    }
    
    public String moveTempFile2UpgradePath( String filename, String originalFilename, boolean isDeviceVer ){

        String curUpgradePathStr = basePathStr.trim() + upgradePathStr.trim();
        if( isDeviceVer ) {
        	//设备升级文件
        	curUpgradePathStr += "/deviceSN";
        	
        } else {
           	//系统升级文件
        	curUpgradePathStr += "/system";          
        }

        String tempFilePath = basePathStr.trim() + tempPathStr.trim() + "/" + filename;


        Path currUpgradePath = Paths.get( curUpgradePathStr );

        try {

            File tempFile = new File(tempFilePath);
            if(!tempFile.exists()){
                throw new StorageException("Failed to temp file not exist：" + filename );
            }

            Files.createDirectories(currUpgradePath);

            Files.copy( tempFile.toPath() , currUpgradePath.resolve( originalFilename ) , REPLACE_EXISTING );

        } catch ( IOException e ) {
            throw new StorageException("Failed to store file " + originalFilename, e);
        }   
        
        return curUpgradePathStr;

    }

}
