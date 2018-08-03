package com.iekun.ef.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iekun.ef.dao.UserMapper;
import com.iekun.ef.dao.AreaCodeMapper;
import com.iekun.ef.dao.RoleMapper;
import com.iekun.ef.model.User;
import com.iekun.ef.model.AreaCode;
import com.iekun.ef.model.Role;
import com.iekun.ef.model.SelfAreaCode;
import com.iekun.ef.service.PasswordHelper;
import com.iekun.ef.controller.SigletonBean;

@Service
public class UtilService {
	
	  @Autowired
	  private AreaCodeMapper areaCodeMapper;
	  
	  public List<AreaCode>  getProvinceListInfo(){
		  
		    List<AreaCode>  provinceList =(List<AreaCode>) areaCodeMapper.selectProvinceList();
	
	        return provinceList;
	    }
	  
	  
	  public List<AreaCode>  getCityListInfo(long provinceId){
		  
		    List<AreaCode>  cityList =(List<AreaCode>) areaCodeMapper.selectCityList( provinceId);
	     
	        return cityList;
	    }
	  
	  
	  
	  public List<AreaCode>  getTownListInfo(long cityId){
		  
		    List<AreaCode>  townList =(List<AreaCode>) areaCodeMapper.selectTownList(cityId);
	       
	        return townList;
	    }
	 

	  public List<SelfAreaCode>  getCityCodeList(){
		  
		    Integer zoneLevel = 2; //取第一级、第二级城市 
		    List<SelfAreaCode>  provinceCityList =(List<SelfAreaCode>) areaCodeMapper.selectCityCodeList(zoneLevel);
	       
	        return provinceCityList;
	    }


	  public List<AreaCode> getAreaCodeList(){
		  List<AreaCode> areaList = areaCodeMapper.selectAreaCodeList();
		  return areaList;
	  }
}
