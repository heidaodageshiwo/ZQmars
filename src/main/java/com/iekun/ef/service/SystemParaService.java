package com.iekun.ef.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iekun.ef.dao.SysParamMapper;
import com.iekun.ef.model.SysParam;


@Service
public class SystemParaService {
	
	
    @Autowired
    private SysParamMapper sysParamMapper;
    
	public void setSysPara(String key, String value)
	{
		SysParam sysPara = new SysParam();
		sysPara.setKey(key);
		sysPara.setValue(value);
		             
	    sysParamMapper.updateSysPara(sysPara);
		  		
		return;
	}
	
	
	public String getSysPara(String key)
	{
		
		try {
			SysParam sysPara = sysParamMapper.getSmsTemplate(key);
			if(sysPara != null)
			{
				return sysPara.getValue();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	} 
	
	public void insertSysPara(String key, String value)
	{
		SysParam record = new SysParam();
		record.setKey(key);
		record.setValue(value);
		
		sysParamMapper.insertSelective(record);
	}
}
