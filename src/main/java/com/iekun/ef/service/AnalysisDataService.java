package com.iekun.ef.service;

import com.iekun.ef.controller.SigletonBean;
import com.iekun.ef.dao.AnalysisiDataMapper;
import com.iekun.ef.model.*;
import com.iekun.ef.util.PropertyUtil;
import com.iekun.ef.util.TimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AnalysisDataService {
	
	 public static Logger logger = LoggerFactory.getLogger( AnalysisDataService.class);

	@Autowired
	private AnalysisiDataMapper analysisiDataMapper;

	public  void exportData(String exportId,List<AnalysisData> DeviceDetailsModelList)
	{
					new Thread(){
						public void run(){
							String 	name=TimeUtils.timeFormatterStr.format( new Date())+".xls";
							PoiWriteExcel a =new PoiWriteExcel();


							int progessLength;
							progessLength = 0;
							ExportTaskInfo exportTaskInfo = (ExportTaskInfo)SigletonBean.exportInfoMaps.get(exportId);
							exportTaskInfo.setProgress(progessLength);

                            progessLength = progessLength + 20;
                            exportTaskInfo.setProgress(progessLength);
                            String returnUrl = null;
                            exportTaskInfo.setProgress(progessLength);
							a.PoiWriteExcel5("sheet", DeviceDetailsModelList, name);


							//this.needDelay(50*reportTimes);


							exportTaskInfo.setProgress(progessLength);
							String wjcflj= PropertyUtil.getProperty("filedown");
							returnUrl=wjcflj+"/"+name;
							exportTaskInfo.setUrl(returnUrl);
							exportTaskInfo.setProgress(100);
							return ;



						}

					}.start();




	}

	 
	//----------------------------------------------------------
	public List<AnalysisData>  getTueinfoData(String condition,String condition2){

		List<AnalysisData>  siteList =(List<AnalysisData>) analysisiDataMapper.getTueinfoData( condition,condition2);
		//User user=null;
		return siteList;
	}


	/*public List<AnalysisData> 	getTueinfoDataBydevicesn(String id){
		List<AnalysisData>  siteList =(List<AnalysisData>) analysisiDataMapper.getTueinfoDataBydevicesn( id);

		return siteList;
	}*/
	public List<AnalysisData> 	getTueinfoDataBydevicesn(String id,String condition3){
		List<AnalysisData>  siteList =(List<AnalysisData>) analysisiDataMapper.getTueinfoDataBydevicesn( id,condition3);

		return siteList;
	}

	public List<AnalysisData> 	queryAnalysisiDataAjaxByImsi(String id,String condition2){
		List<AnalysisData>  siteList =(List<AnalysisData>) analysisiDataMapper.queryAnalysisiDataAjaxByImsi( id,condition2);

		return siteList;
	}
	/*public List<AnalysisData> 	queryAnalysisiDataAjaxByAll(String condition){
		List<AnalysisData>  siteList =(List<AnalysisData>) analysisiDataMapper.queryAnalysisiDataAjaxByAll( condition);

		return siteList;
	}*/

	public List<AnalysisData> 	queryAnalysisiDataAjaxByAll(String condition,String condition2){
		List<AnalysisData>  siteList =(List<AnalysisData>) analysisiDataMapper.queryAnalysisiDataAjaxByAll( condition,condition2);

		return siteList;
	}

	public List<AnalysisData>  getBMD(){

		List<AnalysisData>  siteList =(List<AnalysisData>) analysisiDataMapper.getBMD();
		//User user=null;
		return siteList;
	}

}

