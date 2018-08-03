package com.iekun.ef.service;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExportTask {

	@Autowired
	ExportDataService exportDataService;

	public void exeUeInfoExport(Integer fileType, String exportId, String startDate, String endDate,	Integer operator, 
			String homeOwnership, String siteSN, String deviceSN, String imsi, SqlSessionFactory sqlSessionFactory)
	{
		new Thread() {
             public void run() 
             {
            	 //exportDataService.exportUeInfoData(fileType, exportId, startDate, endDate, operator, homeOwnership, siteSN, deviceSN, imsi, sqlSessionFactory);
            	// exportDataService.exportUeInfoDataFromSplitTable(fileType, exportId, startDate, endDate, operator, homeOwnership, siteSN, deviceSN, imsi, sqlSessionFactory);
            	 exportDataService.exportUeInfoDataFromSplitTable1(fileType, exportId, startDate, endDate, operator, homeOwnership, siteSN, deviceSN, imsi, sqlSessionFactory);
             }
         }.start();
	}
	
	
	public void exeTargetInfoExport(Integer fileType, String exportId, String startDate, String endDate,
			String ruleName, String homeOwnership, String siteSn,  String deviceSn, String imsi, SqlSessionFactory sqlSessionFactory)
	{
		 new Thread() {
             public void run() 
             {
             exportDataService.exportTargetData(fileType, exportId, startDate, endDate, ruleName, homeOwnership, siteSn, deviceSn, imsi, sqlSessionFactory);
             }
         }.start();
	}
	
	public void exeLogExport(Integer fileType, String exportId, String startDate, String endDate, String userName, String logType, String deviceSn, SqlSessionFactory sqlSessionFactory)
	{
		exportDataService.exportLogData(fileType, exportId, startDate, endDate, userName, logType, deviceSn, sqlSessionFactory);
	}
	
}
