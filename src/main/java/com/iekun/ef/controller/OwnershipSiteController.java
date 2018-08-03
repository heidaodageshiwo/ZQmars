package com.iekun.ef.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.iekun.ef.dao.DataTablePager;
import com.iekun.ef.model.*;
import com.iekun.ef.service.OwnershipSiteService;
import com.iekun.ef.service.TargetGroupService;
import com.iekun.ef.service.UtilService;
import com.iekun.ef.util.M16Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;


@Controller
@RequestMapping("/soMapping")
public class OwnershipSiteController {

    @Autowired
    OwnershipSiteService ownershipSiteService;

    @Autowired
    UtilService utilService;

    @Autowired
    TargetGroupService targetGroupService;

    //获得当前时间
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 获取分组列表
     */
    @RequestMapping("/queryAllGroup")
    @ResponseBody
    public JSONObject queryAllGroup(HttpServletRequest request){

        //获取当前用户id
        long creatorId = SigletonBean.getUserId();
        JSONObject jsonObject = new JSONObject();

        //分页处理
        int iDisplayStart = M16Tools.stringToNumber(request.getParameter("iDisplayStart"));
        int iDisplayLength = M16Tools.stringToNumber(request.getParameter("iDisplayLength"));
        String sEcho = request.getParameter("sEcho");
        int startNum = iDisplayStart / iDisplayLength + 1;

        PageHelper.startPage(startNum, iDisplayLength);
        List<OwnershipSite> ownershipSites =  ownershipSiteService.queryAllGroup(creatorId);;
        PageInfo pg = new PageInfo(ownershipSites);
        DataTablePager page = new DataTablePager();
        page.setDataResult(ownershipSites);
        page.setiTotalRecords(pg.getTotal());
        page.setiTotalDisplayRecords(pg.getTotal());
        page.setiDisplayLength(iDisplayLength);
        page.setsEcho(sEcho);

        return (JSONObject)JSONObject.toJSON(page);
    }

  /*  *//**
     * 通过用户ID查询对应的站点信息
     * *//*
    @RequestMapping("/queryAllSiteByUserId")
    @ResponseBody
    public JSONObject queryAllSiteByUserId(){

        JSONObject jsonObject = new JSONObject();
        jsonObject = ownershipSiteService.queryAllSiteByUserId();
        return jsonObject;

    }*/

    /**
     * 获取zTree
     * */
    @RequestMapping("/queryZtree")
    @ResponseBody
    public  List<TreeNode> queryZtree(){

        //要返回的zTree
        List<TreeNode> list = new ArrayList<TreeNode>();
        //得到所有站点
        List<Site> siteList = targetGroupService.querySiteByUserId(SigletonBean.getUserId());
        List<Long>  province= new ArrayList<>();//省份
        List<Long> city = new ArrayList<>();//城市
        List<Long> towns = new ArrayList<>();//区域
        for(Site site: siteList){
            if(!province.contains(site.getProvince_id())){
                province.add(site.getProvince_id());
                list.add(new TreeNode(site.getProvince_id().toString(),"0",site.getProvince_name(),true,false,false));//省份
            }
            if(!city.contains(site.getCity_id())){
                city.add(site.getCity_id());
                list.add(new TreeNode(site.getCity_id().toString(),site.getProvince_id().toString(),site.getCity_name(),true,false,false));//城市
            }
            if(!towns.contains(site.getTown_id())){
                towns.add(site.getTown_id());
                list.add(new TreeNode(site.getTown_id().toString(),site.getCity_id().toString(),site.getTown_name(),true,false,false));//区域
            }
            list.add(new TreeNode(site.getId().toString(),site.getTown_id().toString(),site.getName(),true,false,false));//站点
        }
        return list;
    }

    /**
     * 通过站点名字查询站点ID
     * */
    @RequestMapping("/querySiteIdBySiteName")
    @ResponseBody
    public List<Site> querySiteIdBySiteName(String nameArray){
        List<Site> siteList = targetGroupService.querySiteIdBySiteName(nameArray);
        return siteList;
    }

    /**
     * 添加分组
     * */
    @RequestMapping("/addASGroup")
    @ResponseBody
    public JSONObject addASGroup(String name,long[] siteData,String[] ownershipData,
                                 String startDate,String endDate,String remark,String receivers){

        JSONObject jsonObject = new JSONObject();

        OwnershipSite os = new OwnershipSite();
        //创建主键ID
        String uuid = UUID.randomUUID().toString().trim().replace("-", "");
        JSONArray jsonArray = new JSONArray();
        for(Long siteId : siteData){
            jsonArray.add(siteId);
        }
        StringBuffer sb = new StringBuffer();
        for(long siteId : siteData){
            //获得站点的名字
            String siteName = ownershipSiteService.querySiteName(siteId);
            sb.append(siteName+";");
            //站点ID和分组ID加入t_warning_site映射
            insertWarningSite(uuid,siteId);
        }
        StringBuffer stringBuffer = new StringBuffer();
        for(String id : ownershipData){
            String str = String.valueOf(id);
            String cityId = str.substring(str.length()-2);
            String provinceId = str.substring(0,str.length()-2);
            List<SelfAreaCode> list = ownershipSiteService.queryOwnershipNameById(Long.parseLong(provinceId),Long.parseLong(cityId));
            for(SelfAreaCode selfAreaCode : list){
                stringBuffer.append(selfAreaCode.getProvinceName());
                stringBuffer.append(selfAreaCode.getCityName()+";");
            }
            //添加t_warning_area映射关系
            insertWarningArea(uuid,id);
        }
        os.setOwnership(String.valueOf(ownershipData));
        os.setId(uuid);
        os.setName(name);
        os.setOwnership(String.valueOf(stringBuffer));
        os.setSite(String.valueOf(sb) + String.valueOf(jsonArray));
        //os.setSite(String.valueOf(sb));
        os.setWarning(receivers);
        os.setCreateTime(sdf.format(new Date()));
        os.setStartTime(startDate);
        os.setOverdueTime(endDate);
        os.setRemark(remark);
        os.setCreatorId(SigletonBean.getUserId());
        jsonObject = ownershipSiteService.insertGroup(os);
        return jsonObject;

    }

    /**
     * 站点ID和分组ID加入t_warning_site映射
     * */
    public void insertWarningSite(String uuid,long siteId){

        SiteWarningMapping sm = new SiteWarningMapping();
        sm.setSiteId(siteId);
        sm.setWarningId(uuid);
        ownershipSiteService.insertWarningSite(sm);

    }

    /**
     *添加t_warning_area映射关系
     * */
    public void insertWarningArea(String uuid,String id){

        SiteAreaMapping siteAreaMapping = new SiteAreaMapping();
        siteAreaMapping.setAreaId(id);
        siteAreaMapping.setWarningId(uuid);
        ownershipSiteService.insertWarningArea(siteAreaMapping);

    }

    /**
     * 删除分组
     * */
    @RequestMapping("/delGroupByIds")
    @ResponseBody
    public JSONObject delGrouplist(String ids) {

        JSONObject jsonObject = new JSONObject();
        List<String> list = (List<String>)JSONObject.parse(ids);
        Map map = new HashMap();
        map.put("groupIds", list);
        jsonObject = ownershipSiteService.delGrouplist(map);
        return jsonObject;

    }



    /**
     * 通过分组ID查询站点ID
     * */
    @RequestMapping("/querySiteIdByGroupId")
    @ResponseBody
    public JSONObject querySiteIdByGroupId(String groupId){

        JSONObject jsonObject = new JSONObject();
        jsonObject = ownershipSiteService.querySiteIdByGroupId(groupId);
        return jsonObject;
    }

    /**
     * 通过分组ID查询预警归属地ID
     * */
    @RequestMapping("/queryOwnershipIdByGroupId")
    @ResponseBody
    public JSONObject queryOwnershipIdByGroupId(String groupId){

        JSONObject jsonObject = new JSONObject();
        jsonObject = ownershipSiteService.queryOwnershipIdByGroupId(groupId);
        return jsonObject;
    }


    /**
     * 更新分组信息
     * */
    @RequestMapping("/updateGroup")
    @ResponseBody
    public JSONObject updateGroup(  String groupIdUp,
                                    String name,
                                    long[] newSite,
                                    String[] newownership,
                                    String warning,
                                    String startDate,
                                    String endDate,
                                    String remark){
        JSONObject jsonObject = new JSONObject();

        JSONArray jsonArray = new JSONArray();
        for(Long siteId : newSite){
            jsonArray.add(siteId);
        }
        StringBuffer siteNames = new StringBuffer();
        for (int x = 0;x<newSite.length;x++){
            String siteName = targetGroupService.querySiteNameById(newSite[x]);
            siteNames.append(siteName+";");
        }
        StringBuffer ownershipNames = new StringBuffer();
        for(String id : newownership){
            String str = String.valueOf(id);
            String cityId = str.substring(str.length()-2);
            String provinceId = str.substring(0,str.length()-2);
            List<SelfAreaCode> list = ownershipSiteService.queryOwnershipNameById(Long.parseLong(provinceId),Long.parseLong(cityId));
            for(SelfAreaCode selfAreaCode : list){
                ownershipNames.append(selfAreaCode.getProvinceName());
                ownershipNames.append(selfAreaCode.getCityName()+";");
            }
        }
        //获取当前用户id
        long creator_id = SigletonBean.getUserId();
        OwnershipSite os = new OwnershipSite();
        os.setId(groupIdUp);
        os.setName(name);
        os.setOwnership(ownershipNames.toString());
        os.setSite(String.valueOf(siteNames) + String.valueOf(jsonArray));
        os.setWarning(warning);
        os.setCreateTime(sdf.format(new Date()));
        os.setStartTime(startDate);
        os.setOverdueTime(endDate);
        os.setRemark(remark);
        os.setCreatorId(creator_id);

       jsonObject = ownershipSiteService.updateGroup(os, newSite,newownership);
        return jsonObject;

    }


}
