package com.iekun.ef.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.iekun.ef.dao.DataTablePager;
import com.iekun.ef.dao.NewHistoryMapper;
import com.iekun.ef.model.*;
import com.iekun.ef.service.NewHistoryService;
import com.iekun.ef.util.M16Tools;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 历史数据查询
 */
@RestController
@RequestMapping(value = "/newHistory")
public class NewHistoryController {
    @Autowired
    private NewHistoryService newHistoryService;

    @Autowired
    private NewHistoryMapper newHistoryMapper;

    /**
     * 查询时间插件开始时间
     * */
    @RequestMapping("/getUeinfoToDaterangepicker")
    @ResponseBody
    public JSONObject getUeinfoToDaterangepicker(){
        JSONObject jsonObject = new JSONObject();
        jsonObject = newHistoryService.getUeinfoToDaterangepicker();
        return jsonObject;
    }

    /**
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/getHistory")
    public JSONObject getHistory(HttpServletRequest request, HttpServletResponse response) {
         String formData = request.getParameter("formData");
        List<NewHistoryParamsEntity> selectParamsList = JSON.parseArray(formData, NewHistoryParamsEntity.class);
        //数据是否变化校验
        long wCount = 0;
        List<NewHistoryParamsEntity> buffer = new ArrayList<>();
        for(NewHistoryParamsEntity item : selectParamsList) {
            try{
                newHistoryService.checkCountIsNeedRefresh(item);
                wCount += item.getwCount();
                buffer.add(item);
            }catch (Exception e){
                e.printStackTrace();
                break;
            }
        }

        //分页处理
        int iDisplayStart = M16Tools.stringToNumber(request.getParameter("iDisplayStart"));
        int iDisplayLength = M16Tools.stringToNumber(request.getParameter("iDisplayLength"));
        String sEcho = request.getParameter("sEcho");
        List<UeInfo> records = newHistoryService.selectUeInfoByParams(buffer, iDisplayStart, iDisplayLength);
        DataTablePager page = new DataTablePager();
        page.setDataResult(records);
        page.setiTotalRecords(wCount);
        page.setiTotalDisplayRecords(wCount);
        page.setiDisplayLength(iDisplayLength);
        page.setsEcho(sEcho);

        Map<String, Object> result = new HashMap<>();
        result.put("records", page);
        result.put("params", buffer);
        return (JSONObject)JSONObject.toJSON(result);
    }

    /**
     * 创建要下载的文件
     * @param request
     */
    @RequestMapping("/createExcel")
    @ResponseBody
    public JSONObject createExel(HttpServletRequest request){
        String fileType = request.getParameter("fileType");
        String formData = request.getParameter("formData");
        List<NewHistoryParamsEntity> selectParamsList = JSON.parseArray(formData, NewHistoryParamsEntity.class);
        JSONObject jsonObject = new JSONObject();
        //数据是否变化校验
        long wCount = 0;
        List<NewHistoryParamsEntity> buffer = new ArrayList<>();
        if(selectParamsList != null){
            for(NewHistoryParamsEntity item : selectParamsList) {
                try{
                    newHistoryService.checkCountIsNeedRefresh(item);
                    wCount += item.getwCount();
                    buffer.add(item);
                }catch (Exception e){
                    e.printStackTrace();
                    break;
                }
            }
        }
        if("txt".equals(fileType)){
          jsonObject = newHistoryService.createTxt(buffer);
        }else if("excel".equals(fileType)){
          jsonObject =  newHistoryService.createExcel(buffer);
        }else if("csv".equals(fileType)){
           jsonObject = newHistoryService.createCsv(buffer);
        }
        return jsonObject;
    }

    /**
     * 导出至excel文件
     * @param request
     * @param response
     */
    @RequestMapping("/exportNewHistoryData")
    public void exportNewHistoryData(HttpServletRequest request, HttpServletResponse response) {
        String formData = request.getParameter("formData");
        newHistoryService.doExportHistoryInfos(response, formData);
    }

    @RequestMapping("/queryZtree")
    @ResponseBody
    public  List<TreeNode> queryZtree(){

        //要返回的zTree
        List<TreeNode> list = new ArrayList<TreeNode>();
        list.add(new TreeNode("不限","0","不限",true,false,false));
        //得到所有站点
        List<Site> siteList = newHistoryService.querySiteByUserId(SigletonBean.getUserId());
        List<Long>  province= new ArrayList<>();//省份
        List<Long> city = new ArrayList<>();//城市
        List<Long> towns = new ArrayList<>();//区域
        for(Site site: siteList){
            if(!province.contains(site.getProvince_id())){
                province.add(site.getProvince_id());
                list.add(new TreeNode(site.getProvince_id().toString(),"0",site.getProvince_name(),true,true,false));//省份
            }
            if(!city.contains(site.getCity_id())){
                city.add(site.getCity_id());
                list.add(new TreeNode(site.getCity_id().toString(),site.getProvince_id().toString(),site.getCity_name(),true,true,false));//城市
            }
            if(!towns.contains(site.getTown_id())){
                towns.add(site.getTown_id());
                list.add(new TreeNode(site.getTown_id().toString(),site.getCity_id().toString(),site.getTown_name(),true,true,false));//区域
            }
            list.add(new TreeNode(site.getSn().toString(),site.getTown_id().toString(),site.getName(),true,false,false));//站点
        }
        return list;
    }

}
