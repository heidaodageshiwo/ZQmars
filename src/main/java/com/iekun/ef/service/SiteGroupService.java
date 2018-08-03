package com.iekun.ef.service;

import com.iekun.ef.dao.SiteGroupMapper;
import com.iekun.ef.model.Site;
import com.iekun.ef.model.SiteUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by java1 on 2017/11/28 0028.
 */
@Service
public class SiteGroupService {

    @Autowired
    SiteGroupMapper siteGroupMapper;
    /**
     * 通过用户ID查询对应站点
     * */
    public List<Site> querySiteByUserId(Long userId){

        List<Site> siteList = siteGroupMapper.querySiteByUserId(userId);
        return siteList;
    }
    public List<SiteUser> querysiteUserList(Long userId){

        List<SiteUser> siteUserList = siteGroupMapper.querysiteUserList(userId);
        return siteUserList;
    }
  /*  public void insertData(String siteid,String userid){
        siteGroupMapper.insertData(siteid,userid);
    }*/
   /* public void deleteData(String siteid,String userid){
        siteGroupMapper.deleteData(siteid,userid);
    }*/

   /* public void deleteData(String siteid,String userid){
        siteGroupMapper.deleteData(siteid,userid);
    }*/
   public void insertData(List<SiteUser> sitelist){
       siteGroupMapper.insertData(sitelist);
   }

    public void deleteData(Map<String, Object> map) {
        siteGroupMapper.deleteData(map);
    }
}
