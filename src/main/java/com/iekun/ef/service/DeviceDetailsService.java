package com.iekun.ef.service;

import com.iekun.ef.controller.SigletonBean;
import com.iekun.ef.dao.*;
import com.iekun.ef.model.*;
import com.iekun.ef.util.PropertyUtil;
import com.iekun.ef.util.TimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DeviceDetailsService {

    public static Logger logger = LoggerFactory.getLogger(DeviceDetailsService.class);

    @Autowired
    private DeviceDetailsMapper deviceDetailsMapper;

    @Autowired
    private SiteEntityMapper siteEntityMapper;

    public List<Device> getDeviceListInfoBySiteId(long siteId) {

        List<Device> deviceList = (List<Device>) deviceDetailsMapper.selectDeviceDetailsBySiteId(siteId);
        //User user=null;
        return deviceList;
    }

    public String getTableName(String date) {
        return deviceDetailsMapper.getTableName(date);
    }

    public List<DeviceDetailsCount> getDeviceDetailsCountlist(String devicesn, String table) {

        List<DeviceDetailsCount> DeviceDetailsCountList = (List<DeviceDetailsCount>) deviceDetailsMapper.getDeviceDetailsCountlist(devicesn, table);
        //User user=null;
        return DeviceDetailsCountList;
    }

    public List<Site> getSiteListInfoByUserId(Long userId) {

        List<Site> siteList = (List<Site>) deviceDetailsMapper.selectSiteListByUserId(userId);
        //User user=null;
        return siteList;
    }

    public List<SiteEntity> getSiteEntityListByUser(Long userId){
        return siteEntityMapper.getSiteEntityList(userId);
    }

    public void exportData(String exportId, List<DeviceDetailsModel> DeviceDetailsModelList) {
        new Thread() {
            public void run() {
                String name = TimeUtils.timeFormatterStr.format(new Date()) + ".xls";
                PoiWriteExcel a = new PoiWriteExcel();


                int progessLength;
                progessLength = 0;
                ExportTaskInfo exportTaskInfo = (ExportTaskInfo) SigletonBean.exportInfoMaps.get(exportId);
                exportTaskInfo.setProgress(progessLength);

                progessLength = progessLength + 20;
                exportTaskInfo.setProgress(progessLength);
                String returnUrl = null;
                exportTaskInfo.setProgress(progessLength);
                a.PoiWriteExcel2("sheet", DeviceDetailsModelList, name);


                //this.needDelay(50*reportTimes);


                exportTaskInfo.setProgress(progessLength);
                String wjcflj = PropertyUtil.getProperty("filedown");
                returnUrl = wjcflj + "/" + name;
                exportTaskInfo.setUrl(returnUrl);
                exportTaskInfo.setProgress(100);
                return;


            }

        }.start();


    }
		/*String Condition =null;int reportTimes = 0;List<String> tableNameList = null;
		tableNameList = this.getTableNameListAccordingCondition(startDate, endDate);
		Condition = this.getCondition(startDate, endDate, operator, homeOwnership, siteSN, deviceSN, imsi);
		if (Condition == null){Condition = "1=1";}
		reportTimes = tableNameList.size();
		Map<String,Object> map=new HashMap<>();
		int progessLength;
		progessLength = 0;
		String name="";
		if(fileType==0){
			name= TimeUtils.timeFormatterStr.format( new Date())+".txt";
		}else if(fileType==1){
			name=TimeUtils.timeFormatterStr.format( new Date())+".csv";
		}else{
			name=TimeUtils.timeFormatterStr.format( new Date())+".xlsx";
		}
		ExportTaskInfo exportTaskInfo = (ExportTaskInfo) SigletonBean.exportInfoMaps.get(exportId);
		exportTaskInfo.setProgress(progessLength);
		for(int i=0; i < reportTimes; i++) {
			String table = tableNameList.get(i);
			String condition = Condition;
			List<TargetAlarm> selfCityCodeList = targetAlarmServ.gettableshuju(table, condition);
			map.put("list"+i,selfCityCodeList);
			this.needDelay(3000);
			progessLength = progessLength + (int)(80/reportTimes);
			exportTaskInfo.setProgress(progessLength);
			logger.info(" progessLength = " + progessLength);
		}
		List<TargetAlarm> list = new ArrayList<>();
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			//System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
			list.addAll((List<TargetAlarm>)entry.getValue());
		}
		PoiWriteExcel a =new PoiWriteExcel();
		if(fileType==0) {
			a.PoiWriteTxt(list, name);
			String wjcflj= PropertyUtil.getProperty("file");
			this.execCmd("chmod 777 " + wjcflj);
			File file = new File(wjcflj+"/"+name);
			File zipFile = new File(wjcflj+"/"+name.substring(0,14)+".zip");
			InputStream input = null;
			try {
				input = new FileInputStream(file);
				ZipOutputStream zipOut = null;
				zipOut = new ZipOutputStream(new FileOutputStream(zipFile));
				zipOut.putNextEntry(new ZipEntry(file.getName()));
				int temp = 0;
				while((temp = input.read()) != -1) {
					zipOut.write(temp);
				}
				input.close();
				zipOut.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			a.PoiWriteExcel1("sheet", list, name);
		}
		this.needDelay(50*reportTimes);
		progessLength = progessLength + 20;
		exportTaskInfo.setProgress(progessLength);
		String returnUrl = null;
		exportTaskInfo.setProgress(progessLength);
		String wjcflj= PropertyUtil.getProperty("filedown");
		if(fileType==0) {
			returnUrl=wjcflj+"/"+name.substring(0,14)+".zip";
		}else{
			returnUrl=wjcflj+"/"+name;
		}
		exportTaskInfo.setUrl(returnUrl);
		exportTaskInfo.setProgress(100);
		return ;
	}*/


}

