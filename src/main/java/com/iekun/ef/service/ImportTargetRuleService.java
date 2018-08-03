package com.iekun.ef.service;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.iekun.ef.jms.vo.send.SendImsi;
import com.iekun.ef.model.TargetRule;

@Service
public class ImportTargetRuleService {
	
	@Autowired
	TargetAlarmService targetAlarmService;
	
	private static Logger logger = LoggerFactory.getLogger(ImportTargetRuleService.class);

	 public boolean readExcelFile(MultipartFile file) {  
	        boolean result = false;  
	        //创建处理EXCEL的类  
	        ReadExcel readExcel=new ReadExcel();  
	        //解析excel，获取上传的事件单  
	        List<TargetRule> targetRuleList = readExcel.getExcelInfo(file);  
	        //至此已经将excel中的数据转换到list里面了,接下来就可以操作list,可以进行保存到数据库,或者其他操作,  
	        if(targetRuleList != null && !targetRuleList.isEmpty()){  
	        	//不为空，则写入数据库
	        	int listSize = targetRuleList.size();
	        	for(int i = 0; i < listSize; i ++)
	        	{
	        		TargetRule targetRule = targetRuleList.get(i);
	        		targetAlarmService.addBlackToList(targetRule.getName(), targetRule.getImsi(), targetRule.getRemark(), targetRule.getReceiver());
	        	}
	        	
	        	targetRuleList.clear();
	        	logger.info("黑名单上传成功，上传成功条数为：" + listSize);
	        	result = true;
	
	        }else{  
	            logger.info("上传失败" );
	        }
	        
	        return result;  
	    }
	 
	/* private boolean isExistInLib(String imsi)
	 {
		 boolean returnVal = false;
		 List<SendImsi> list = targetAlarmService.getAllTargetRules();
		 
		 
		 
		 return returnVal;
				 
	 }*/
	 
	 
}



