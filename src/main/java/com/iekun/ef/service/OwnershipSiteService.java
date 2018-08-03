package com.iekun.ef.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.iekun.ef.controller.SigletonBean;
import com.iekun.ef.dao.OwnershipSiteMapping;
import com.iekun.ef.dao.TargetGroupMapper;
import com.iekun.ef.model.*;
import com.iekun.ef.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OwnershipSiteService {

    @Autowired
    OwnershipSiteMapping ownershipSiteMapping;

    @Autowired
    TargetGroupMapper targetGroupMapper;

    private SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 查询所有分组
     * */
    public  List<OwnershipSite> queryAllGroup(long creatorId){
        try{
            List<OwnershipSite> targetGroup = ownershipSiteMapping.queryAllGroup(creatorId);
            for (OwnershipSite group : targetGroup) {
                group.setCreatorName(SigletonBean.getUserName());
                group.setStartTime(sdf1.format(DateUtils.parseDate(group.getStartTime())));
                group.setOverdueTime(sdf1.format(DateUtils.parseDate(group.getOverdueTime())));
            }
            return targetGroup;

        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过用户ID查询对应的站点信息
     */
    public JSONObject queryAllSiteByUserId() {

        boolean status = false;
        JSONObject jo = new JSONObject();
        JSONArray ja = new JSONArray();

        try{
            //获取用户ID
            long creator_id = SigletonBean.getUserId();
            List<Site> allSite = ownershipSiteMapping.queryAllSiteByUserId(creator_id);
            for (Site site : allSite) {
                JSONObject siteMessage = new JSONObject();
                siteMessage.put("id", site.getId());
                siteMessage.put("name", site.getName());
                ja.add(siteMessage);
            }
            status = true;
        }catch(Exception e){
            e.printStackTrace();
        }

        if (status == true) {
            jo.put("status", true);
            jo.put("message", "成功");
            jo.put("data", ja);
        } else {
            jo.put("status", false);
            jo.put("message", "失败");
            jo.put("data", ja);
        }
        return jo;

    }

    /**
     * 通过id查询对应的站点信息
     * */
    public String querySiteName(long siteId){

       String siteName =  ownershipSiteMapping.querySiteName(siteId);

       return siteName;
    }

    /**
     *添加t_warning_site映射关系
     * */
    public void insertWarningSite(SiteWarningMapping sm){

        try{
            ownershipSiteMapping.insertWarningSite(sm);
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    /**
     *添加t_warning_area映射关系
     * */
    public void insertWarningArea(SiteAreaMapping siteAreaMapping){

        try{
            ownershipSiteMapping.insertWarningArea(siteAreaMapping);
        }catch(Exception e){
            e.printStackTrace();
        }

    };

    /**
     * 通过ID查询区域名字
     * */
    public List<SelfAreaCode> queryOwnershipNameById(long provinceId,long cityId){

        List<SelfAreaCode> list = ownershipSiteMapping.queryOwnershipNameById(provinceId,cityId);
        return list;

    }

    /**
     * 添加分组
     * */
    public JSONObject insertGroup(OwnershipSite os){
        boolean status = false;
        JSONObject jo = new JSONObject();
        JSONArray ja = new JSONArray();

        //
        List<OwnershipSite> list = ownershipSiteMapping.queryAllGroup(SigletonBean.getUserId());
        List<String> groupNames = new ArrayList<>();
        for(OwnershipSite ownershipSite : list){
            groupNames.add(ownershipSite.getName());
        }

        if(groupNames.size() > 0 && groupNames.contains(os.getName())){
            jo.put("status", false);
            jo.put("message", "此新建分组名称已存在，请更换名称");
            jo.put("data", ja);
        }else{
            try{
                ownershipSiteMapping.insertGroup(os);
                status = true;
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        if (status == true) {
            jo.put("status", true);
            jo.put("message", "成功");
            jo.put("data", ja);
        }


        return jo;

    }

    /**
     * 删除分组
     */
    public JSONObject delGrouplist(Map map) {

        boolean status = false;
        JSONObject jo = new JSONObject();

        try {
            //删除分组
            ownershipSiteMapping.delGrouplist(map);
            //删除t_warning_site映射关系
            ownershipSiteMapping.delSiteMapping(map);
            //删除t_warning_area映射关系
            ownershipSiteMapping.delAreaMapping(map);
            status = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (status == true) {
            jo.put("status", true);
            jo.put("message", "成功");
        } else {
            jo.put("status", false);
            jo.put("message", "失败");
        }
        return jo;

    }

    /**
     * 通过站点名字查询站点ID
     * */
    public JSONObject querySiteIdByGroupId(String groupId){

        boolean status = false;
        JSONObject jo = new JSONObject();
        List<Long> siteIds = new ArrayList<>();

        try {
            List<Map<String,Long>> list = new ArrayList<Map<String,Long>>();
            list = ownershipSiteMapping.querySiteIdByGroupId(groupId);
            for(Map<String,Long> map : list){
                siteIds.add(map.get("SITE_ID"));
            }
            status = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (status == true) {
            jo.put("status", true);
            jo.put("siteNamess",siteIds);
            jo.put("message", "数据加载成功");
        } else {
            jo.put("status", false);
            jo.put("message", "加载分组名称失败");
        }
        return jo;

    }

    /**
     * 通过站点名字查询站点ID
     * */
    public JSONObject queryOwnershipIdByGroupId(String groupId){

        boolean status = false;
        JSONObject jo = new JSONObject();
        List<String> ownershipNames = new ArrayList<>();

        try {
            List<Map<String,String>> list = new ArrayList<Map<String,String>>();
            list = ownershipSiteMapping.queryOwnershipIdByGroupId(groupId);
            for(Map<String,String> map : list){
                ownershipNames.add(map.get("AREA_ID"));
            }
            status = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (status == true) {
            jo.put("status", true);
            jo.put("ownershipNames",ownershipNames);
            jo.put("message", "数据加载成功");
        } else {
            jo.put("status", false);
            jo.put("message", "加载分组名称失败");
        }
        return jo;

    }

    /**
     * 更新分组信息
     */
    public JSONObject updateGroup(OwnershipSite os,long[] newSite,String[] newownership) {
        boolean status = false;
        JSONObject jo = new JSONObject();
        //判断要添加的分组名字是否已存在
        List<OwnershipSite> list = ownershipSiteMapping.queryAllGroup(SigletonBean.getUserId());
        List<String> groupNames = new ArrayList<>();
        for(OwnershipSite x : list){
            if(x.getId().equals(os.getId())){
                continue;
            }else{
                groupNames.add(x.getName());
            }
        }
        //不存在则将新建分组加入数据库
        if(groupNames.size() > 0 && groupNames.contains(os.getName())){
            jo.put("status", false);
            jo.put("message", "要更新的分组名称已存在，请更换名称");
        }else{
            try {
                //更新分组信息
                ownershipSiteMapping.updateGroup(os);
                //删除分组站点映射
                ownershipSiteMapping.delupSiteMapping(os.getId());
                //删除分组归属地映射
                ownershipSiteMapping.delupAreaMapping(os.getId());
                //添加分组站点映射
                for(long siteId : newSite){
                    SiteWarningMapping sm = new SiteWarningMapping();
                    sm.setSiteId(siteId);
                    sm.setWarningId(os.getId());
                    insertWarningSite(sm);
                }
                //添加分组归属地映射
                for(String areaId : newownership){
                    SiteAreaMapping siteAreaMapping = new SiteAreaMapping();
                    siteAreaMapping.setAreaId(areaId);
                    siteAreaMapping.setWarningId(os.getId());
                    insertWarningArea(siteAreaMapping);
                }
                status = true;
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        if (status == true) {
            jo.put("status", true);
            jo.put("message", "成功");
        }
        return jo;

    }

}
