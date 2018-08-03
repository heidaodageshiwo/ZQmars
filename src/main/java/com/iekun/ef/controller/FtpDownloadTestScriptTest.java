package com.iekun.ef.controller;

import com.iekun.ef.util.FtpUtil;

/**
 * Created by java1 on 2018/1/23 0023.
 */
public class FtpDownloadTestScriptTest {
    public static void main(String[] args) {
        String ftpHost = "10.1.1.202";
        String ftpUserName = "version";
        String ftpPassword = "123456";
        int ftpPort = 21;
        String ftpPath = "/";
        String localPath = "E:\\AllTxt";
        String fileName = "c4edba9f039a0000.zip";
        FtpUtil.downloadFtpFile(ftpHost, ftpUserName, ftpPassword, ftpPort, ftpPath, localPath, fileName);
    }
}
