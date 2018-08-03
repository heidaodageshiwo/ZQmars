package com.iekun.ef.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.iekun.ef.dao.DataTablePager;
import com.iekun.ef.model.*;
import com.iekun.ef.service.TargetGroupService;
import com.iekun.ef.service.UtilService;
import com.iekun.ef.util.ConvertTools;
import com.iekun.ef.util.M16Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/target")
public class TargetGroupController {

    @Autowired
    TargetGroupService targetGroupService;

    @Autowired
    private UtilService utilService;

    //获得当前时间
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
    private String date = sdf.format(new Date());

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
     * 获取分组列表
     */
    @RequestMapping("/queryAllGroup")
    @ResponseBody
    public JSONObject queryAllGroup( HttpServletRequest request){

        //获取当前用户id
        long creatorId = SigletonBean.getUserId();

        //分页处理
        int iDisplayStart = M16Tools.stringToNumber(request.getParameter("iDisplayStart"));
        int iDisplayLength = M16Tools.stringToNumber(request.getParameter("iDisplayLength"));
        String sEcho = request.getParameter("sEcho");
        int startNum = iDisplayStart / iDisplayLength + 1;

        PageHelper.startPage(startNum, iDisplayLength);
        List<TargetGroup> targetGroups =  targetGroupService.queryAllGroup(creatorId);
        PageInfo pg = new PageInfo(targetGroups);
        DataTablePager page = new DataTablePager();
        page.setDataResult(targetGroups);
        page.setiTotalRecords(pg.getTotal());
        page.setiTotalDisplayRecords(pg.getTotal());
        page.setiDisplayLength(iDisplayLength);
        page.setsEcho(sEcho);

        return (JSONObject)JSONObject.toJSON(page);

    }

    /**
     * 获取分组对应的黑名单人员信息
     * */
    @RequestMapping("/queryBlacklist")
    @ResponseBody
    public JSONObject queryBlacklist( HttpServletRequest request){

        String groupId = request.getParameter("formData");

            //分页处理
            int iDisplayStart = M16Tools.stringToNumber(request.getParameter("iDisplayStart"));
            int iDisplayLength = M16Tools.stringToNumber(request.getParameter("iDisplayLength"));
            String sEcho = request.getParameter("sEcho");
            int startNum = iDisplayStart / iDisplayLength + 1;

            PageHelper.startPage(startNum, iDisplayLength);
            List<TargetRule> targetGroup = targetGroupService.queryBlacklist(groupId);
            PageInfo pg = new PageInfo(targetGroup);
            DataTablePager page = new DataTablePager();
            page.setDataResult(targetGroup);
            page.setiTotalRecords(pg.getTotal());
            page.setiTotalDisplayRecords(pg.getTotal());
            page.setiDisplayLength(iDisplayLength);
            page.setsEcho(sEcho);

            return (JSONObject)JSONObject.toJSON(page);

    }

    /**
     * 删除分组中单个黑名单人员信息
     */
    @RequestMapping("/delGroupTargetPerson")
    @ResponseBody
    public JSONObject delGroupTargetPerson(long targetRuleId,String imsi){

        JSONObject jsonObject = new JSONObject();
        jsonObject = targetGroupService.delGroupTarget(targetRuleId,imsi);
        return jsonObject;

    }

    /**
     * 通过用户ID查询对应的站点信息
     * */
    @RequestMapping("/queryAllSiteByUserId")
    @ResponseBody
    public JSONObject queryAllSite(){

        JSONObject jsonObject = new JSONObject();
        jsonObject = targetGroupService.queryAllSiteByUserId();
        return jsonObject;

    }

    /**
     * 添加分组
     * */
    @RequestMapping("/addGroup")
    @ResponseBody
    public JSONObject addGroup(  String name,
                                 Long[] site,
                                 String warning,
                                 String startDate,
                                 String endDate,
                                 String remark){

        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for(Long siteId : site){
            jsonArray.add(siteId);
        }
        StringBuffer siteNames = new StringBuffer();
        for (int x = 0;x<site.length;x++){
            String siteName = targetGroupService.querySiteNameById(site[x]);
            if(siteName != null){
                siteNames.append(siteName+";");
            }
        }
        //获取当前用户id
        long creator_id = SigletonBean.getUserId();
       /* //获取创建人姓名然后再进行添加  改成存储创建时间 创建人姓名没用了
        String creator_name = targetGroupService.queryCreatorName(creator_id);*/
        TargetGroup targetGroup = new TargetGroup();
        targetGroup.setName(name);
        targetGroup.setCreator_id(creator_id);
        targetGroup.setWarning(warning);
        targetGroup.setCreate_time(startDate);
        targetGroup.setOverdue_time(endDate);
        targetGroup.setRemark(remark);
        //存入站点ID和名字
        targetGroup.setSite(String.valueOf(siteNames) + String.valueOf(jsonArray));
        //targetGroup.setSite(String.valueOf(jsonArray));
        //targetGroup.setSite(String.valueOf(siteNames));
        targetGroup.setCreator_name(sdf.format(new Date()));
        jsonObject = targetGroupService.addGroup(targetGroup,site);
        return jsonObject;

    }

        /**
         * 删除分组
         * */
        @RequestMapping("/delGrouplist")
        @ResponseBody
        public JSONObject delGrouplist(String ids) {
            JSONObject jsonObject = new JSONObject();

            List<String> list = (List<String>)JSONObject.parse(ids);
            Map map = new HashMap();
            map.put("groupIds", list);

            jsonObject = targetGroupService.delGrouplist(map);
            return jsonObject;

    }

    /**
     * 补录黑名单
     * */
    @RequestMapping("/addOneTarget")
    @ResponseBody
    public JSONObject addOneTarget(String receivers){
        JSONObject jsonObject = new JSONObject();
        List<Map<String, String>> receiverList = ( List<Map<String, String>>)JSONObject.parse(receivers);
        jsonObject = targetGroupService.addOneTarget(receiverList);

        return jsonObject;
    }

    /**
     * 导入黑名单
     * */
    @RequestMapping(value = "/addExcelTarget", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject addExcelTarget(@RequestParam("uploadFile") MultipartFile uploadFile,String groupId) {

        JSONObject jsonObject = new JSONObject();

        boolean isUploadSucc = false;
        try{
            jsonObject = targetGroupService.analyseExcel(uploadFile,groupId);
        }catch(Exception e){
            e.printStackTrace();
        }


        return jsonObject;
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
     * 更新分组信息
     * */
    @RequestMapping("/updateGroup")
    @ResponseBody
    public JSONObject updateGroup(  String groupIdUp,
                                 String name,
                                 long[] site,
                                 String warning,
                                 String startDate,
                                 String endDate,
                                 String remark){
        JSONObject jsonObject = new JSONObject();

        JSONArray jsonArray = new JSONArray();
        for(Long siteId : site){
            jsonArray.add(siteId);
        }
        StringBuffer siteNames = new StringBuffer();
        for (int x = 0;x<site.length;x++){
            String siteName = targetGroupService.querySiteNameById(site[x]);
            siteNames.append(siteName+";");
        }
        //获取当前用户id
        long creator_id = SigletonBean.getUserId();
       /* //获取创建人姓名然后再进行添加
        String creator_name = targetGroupService.queryCreatorName(creator_id);*/
        TargetGroup targetGroup = new TargetGroup();
        targetGroup.setId(groupIdUp);
        targetGroup.setName(name);
        targetGroup.setCreator_id(creator_id);
        targetGroup.setWarning(warning);
        targetGroup.setCreate_time(startDate);
        targetGroup.setOverdue_time(endDate);
        targetGroup.setRemark(remark);
        targetGroup.setSite(String.valueOf(siteNames) + String.valueOf(jsonArray));
        //targetGroup.setSite(String.valueOf(siteNames));
        targetGroup.setCreator_name(sdf.format(new Date()));

        jsonObject = targetGroupService.updateGroup(targetGroup,site,groupIdUp);
        return jsonObject;

    }

    @RequestMapping("/querySiteIdsByGroupId")
    @ResponseBody
    public JSONObject querySiteIdsByGroupId(String groupId){
        JSONObject jsonObject = new JSONObject();
        jsonObject = targetGroupService.querySiteIdsByGroupId(groupId);
        return jsonObject;
    }


}
