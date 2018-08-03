package com.iekun.ef.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.iekun.ef.dao.DataTablePager;
import com.iekun.ef.model.SysParam;
import com.iekun.ef.model.TargetAlarm;
import com.iekun.ef.model.TargetAlarmNewEntity;
import com.iekun.ef.service.ImportTargetRuleService;
import com.iekun.ef.service.ResourceService;
import com.iekun.ef.service.TargetAlarmService;
import com.iekun.ef.service.UserService;
import com.iekun.ef.service.SystemParaService;

import com.iekun.ef.util.ComInfoUtil;
import com.iekun.ef.util.ConvertTools;

import com.iekun.ef.util.M16Tools;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by jiangqi.yang  on 2016/10/26.
 */

@Controller
@RequestMapping("/target")
public class TargetAlarmController {

    private static Logger logger = LoggerFactory.getLogger(TargetAlarmController.class);
    
    @Autowired
    private ResourceService resourceServ;
	
	@Autowired
    private UserService userService;
	
	@Autowired
	private ImportTargetRuleService importTargetRuleServ;
	
    @Autowired
    private TargetAlarmService  targetAlarmServ;

    @Autowired
    private SystemParaService systemParaService;

    @Autowired
    private ComInfoUtil comInfoUtil;

    @RequestMapping("/current")
    public String current(Map<String, Object> model ) {

        comInfoUtil.getTopHeaderInfo( model );
        comInfoUtil.getSystemInfo( model );

        //// TODO: 2016/11/7
    	long roleId = SigletonBean.getRoleId();
        resourceServ.getResourceInfoByRoleId(model, roleId);
        model.put("user", userService.getUserDetailInfo());

        return "business/currentAlarm";
    }

    @RequestMapping("/getCurrentTargets")
    @ResponseBody
    public JSONObject getCurrentTargets() {
        JSONObject jsonObject  = new JSONObject();

        //// TODO: 2016/11/8
        jsonObject = targetAlarmServ.getCurrentTargets();
        return jsonObject;
    }

    /**
     * 获取未确认的黑名单拦截信息
     * @return
     */
    @RequestMapping("/getCurrentTargetsNew")
    @ResponseBody
   public JSONObject getCurrentTargetsNew(HttpServletRequest request) {
        String formData = request.getParameter("formData");
        Map<String, String> params = (Map<String, String>)JSONObject.parse(formData);
        JSONObject jsonObject  = new JSONObject();

        //分页处理
        int iDisplayStart = M16Tools.stringToNumber(request.getParameter("iDisplayStart"));
        int iDisplayLength = M16Tools.stringToNumber(request.getParameter("iDisplayLength"));
        String sEcho = request.getParameter("sEcho");
        int startNum = iDisplayStart / iDisplayLength + 1;

        PageHelper.startPage(startNum, iDisplayLength);
        List<TargetAlarmNewEntity> records  = targetAlarmServ.getCurrentTargetsNew(params);
        PageInfo pg = new PageInfo(records);
        DataTablePager page = new DataTablePager();
        page.setDataResult(records);
        page.setiTotalRecords(pg.getTotal());
        page.setiTotalDisplayRecords(pg.getTotal());
        page.setiDisplayLength(iDisplayLength);
        page.setsEcho(sEcho);

        return (JSONObject)JSONObject.toJSON(page);
    }

    /**
     * 预警确认
     * @param request
     * @return
     */
    @RequestMapping("/confirmTargetAlarmNew")
    @ResponseBody
    public JSONObject confirmTargetAlarmNew(HttpServletRequest request) {
        String ids = request.getParameter("ids");
        List<Integer> idsList = (List<Integer>)JSONObject.parse(ids);

        JSONObject jsonObject  = new JSONObject();
        jsonObject.put("status", true);
        jsonObject.put("message", "成功");

        boolean isConfirm = false;
        try{
            targetAlarmServ.confirmTargetAlarmNew(idsList);
            isConfirm = true;
        } catch (Exception e) {
            e.printStackTrace();
            isConfirm = false;
        }
        if(isConfirm == true) {
            jsonObject.put("status", true);
            jsonObject.put("message", "成功");
        } else {
            jsonObject.put("status", false);
            jsonObject.put("message", "失败");
        }

        return  jsonObject;
    }

    /**
     * 查询历史信息
     * @param request
     * @return
     */
   @RequestMapping("/queryHistoryDataNew")
    @ResponseBody
    public JSONObject queryHistoryDataNew(HttpServletRequest request) {
        String formData = request.getParameter("formData");
        Map<String, Object> params = (Map<String, Object>)JSONObject.parse(formData);

        //分页处理
        int iDisplayStart = M16Tools.stringToNumber(request.getParameter("iDisplayStart"));
        int iDisplayLength = M16Tools.stringToNumber(request.getParameter("iDisplayLength"));
        String sEcho = request.getParameter("sEcho");
        int startNum = iDisplayStart / iDisplayLength + 1;

        PageHelper.startPage(startNum, iDisplayLength);
        List<TargetAlarmNewEntity> records = targetAlarmServ.queryHistoryAlarmData(params);
        PageInfo pg = new PageInfo(records);
        DataTablePager page = new DataTablePager();
        page.setDataResult(records);
        page.setiTotalRecords(pg.getTotal());
        page.setiTotalDisplayRecords(pg.getTotal());
        page.setiDisplayLength(iDisplayLength);
        page.setsEcho(sEcho);

        return (JSONObject)JSONObject.toJSON(page);
    }

    /**
     * 检测是否能够下载文件
     * @param request
     * @return
     */
    @RequestMapping("/checkIsCanDownLoadExcelFile")
    @ResponseBody
    public JSONObject checkIsCanDownLoadExcelFile(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        String blacklistHistoryDownload = systemParaService.getSysPara("blacklistHistoryDownload");
        if(blacklistHistoryDownload == null || blacklistHistoryDownload.isEmpty() || !blacklistHistoryDownload.equals("1")) {
            jsonObject.put("status", false);
            jsonObject.put("message", "暂无下载权限");
        } else {
            jsonObject.put("status", true);
            jsonObject.put("message", "有权限下载");
        }
        return jsonObject;
    }

    public List<TargetAlarmNewEntity>queryHistoryAlarmDatas(Map<String, Object> params){
        List<TargetAlarmNewEntity> list = new ArrayList<>();

        return list;

    }
    /**
     * 导出至excel文件
     * @param request
     * @param response
     */
    @RequestMapping("/exportTxtHistoryData")
    public void exportTxtHistoryData(HttpServletRequest request, HttpServletResponse response) {
        String formData = request.getParameter("formData");
        Map<String, Object> params = (Map<String, Object>)JSONObject.parse(formData);
        params.put("userId", SigletonBean.getUserId());

        List<TargetAlarmNewEntity> records = new ArrayList<>();
        //查询总数
        int count = targetAlarmServ.queryCountByParams(params);
        //分批次查询
        if(count < 10000){
             records = targetAlarmServ.queryHistoryAlarmData(params);
        }else{
            int start  = 0;
            int length = 10000;
            while(count > 10000){
                params.put("start",start);
                params.put("length",length);
                List<TargetAlarmNewEntity> record = targetAlarmServ.queryHistoryAlarmDataLimit(params);
                records.addAll(record);
                start += 10000;
                count = count - 10000;
            }
            params.put("start",start);
            params.put("length",count);
            List<TargetAlarmNewEntity> record = targetAlarmServ.queryHistoryAlarmDataLimit(params);
            records.addAll(record);
        }

       // List<TargetAlarmNewEntity> records = targetAlarmServ.queryHistoryAlarmData(params);

        //获取文件名称
        String hStartTime = (String)params.get("hStartTime");
        String hEndTime = (String)params.get("hEndTime");
        String fileName = "预警历史信息-" + new SimpleDateFormat("yyyy年MM月dd日HH时mm分").format(new Date()) + ".xls";
        //
        try {
            HSSFWorkbook wb = new HSSFWorkbook();
            HSSFFont font = wb.createFont();
            font.setFontHeightInPoints((short)24);
            font.setFontName("宋体");
            font.setColor(HSSFColor.BLACK.index);

            HSSFSheet sheet = wb.createSheet();

            int rowIdx = 0;
            HSSFRow row = sheet.createRow(rowIdx);
            //写入表头
            HSSFCell cell = row.createCell(0);
            cell.setCellValue(new HSSFRichTextString("预警名称"));
            cell = row.createCell(1);
            cell.setCellValue(new HSSFRichTextString("类型"));
            cell = row.createCell(2);
            cell.setCellValue(new HSSFRichTextString("IMSI"));
            cell = row.createCell(3);
            cell.setCellValue(new HSSFRichTextString("归属地"));
            cell = row.createCell(4);
            cell.setCellValue(new HSSFRichTextString("运营商"));
            cell = row.createCell(5);
            cell.setCellValue(new HSSFRichTextString("站点名称"));
            cell = row.createCell(6);
            cell.setCellValue(new HSSFRichTextString("设备名称"));
            cell = row.createCell(7);
            cell.setCellValue(new HSSFRichTextString("告警时间"));

            //循环写入数据
            for(int j = 0; j < 65535 && j < records.size(); ++j) {
                rowIdx++;
                row = sheet.createRow(rowIdx);

                TargetAlarmNewEntity entity = records.get(j);
                cell = row.createCell(0);
                cell.setCellValue(new HSSFRichTextString(entity.getTargetName()));
                cell = row.createCell(1);
                String type = "0".equals(String.valueOf(entity.getIndication())) ? "黑名单预警" : "归属地预警";
                cell.setCellValue(new HSSFRichTextString(type));
                cell = row.createCell(2);
                cell.setCellValue(new HSSFRichTextString(entity.getImsi()));
                cell = row.createCell(3);
                cell.setCellValue(new HSSFRichTextString(entity.getCityName()));
                cell = row.createCell(4);
                cell.setCellValue(new HSSFRichTextString(entity.getOperator()));
                cell = row.createCell(5);
                cell.setCellValue(new HSSFRichTextString(entity.getSiteName()));
                cell = row.createCell(6);
                cell.setCellValue(new HSSFRichTextString(entity.getDeviceName()));
                cell = row.createCell(7);
                cell.setCellValue(new HSSFRichTextString(entity.getCaptureTime()));
            }

            //写到前端
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            wb.write(outputStream);

            byte[] xls = outputStream.toByteArray();
            outputStream.close();

            response.reset();
            fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
            response.addHeader("Content-lenth", "" + xls.length);
            response.setContentType("application/octet-stream;charset=UTF-8");

            OutputStream os = new BufferedOutputStream(response.getOutputStream());
            os.write(xls);
            os.flush();
            os.close();
            response.flushBuffer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/history")
    public String history(Map<String, Object> model ) {

        comInfoUtil.getTopHeaderInfo( model );
        comInfoUtil.getSystemInfo( model );

    	long roleId = SigletonBean.getRoleId();
        resourceServ.getResourceInfoByRoleId(model, roleId);
        model.put("user", userService.getUserDetailInfo());

        return "business/historyAlarm";
    }

    @RequestMapping("/queryHistoryData")
    @ResponseBody
    public JSONObject queryHistoryData(
            @RequestParam(value = "draw", required = false ) Integer draw,
            @RequestParam(value = "length", required = false ) Integer length,
            @RequestParam(value = "start", required = false ) Integer start,
            @RequestParam(value = "startDate", required = false ) String startDate,
            @RequestParam(value = "endDate", required = false ) String endDate,
            @RequestParam(value = "ruleName", required = false ) String ruleName,
            @RequestParam(value = "imsi", required = false ) String imsi,
            @RequestParam(value = "homeOwnership", required = false ) String homeOwnership,
            @RequestParam(value = "siteSN", required = false ) String siteSN,
            @RequestParam(value = "deviceSN", required = false ) String deviceSN
    ) {
        JSONObject jsonObject  = new JSONObject();

        jsonObject = targetAlarmServ.queryHistoryAlarmData( draw, length, start, startDate, endDate ,
                ruleName, homeOwnership, siteSN, deviceSN, imsi );

        return jsonObject;
    }


    @RequestMapping("/blacklist")
    public String blacklist(Map<String, Object> model ) {

        comInfoUtil.getTopHeaderInfo( model );
        comInfoUtil.getSystemInfo( model );

    	long roleId = SigletonBean.getRoleId();
        resourceServ.getResourceInfoByRoleId(model, roleId);
        model.put("user", userService.getUserDetailInfo());

        return "business/blacklistManager";
    }

    @RequestMapping("/whitelist")
    public String whitelist(Map<String, Object> model ) {

        comInfoUtil.getTopHeaderInfo( model );
        comInfoUtil.getSystemInfo( model );

        long roleId = SigletonBean.getRoleId();
        resourceServ.getResourceInfoByRoleId(model, roleId);
        model.put("user", userService.getUserDetailInfo());

        return "business/whitelistManager";
    }

    @RequestMapping("/getBlacklist")
    @ResponseBody
    public JSONObject getBlacklist() {
        JSONObject jsonObject = new JSONObject();
      
        jsonObject = targetAlarmServ.getBlacklist();
        return  jsonObject;
    }

    @RequestMapping("/addBlacklist")
    @ResponseBody
    public JSONObject addBlacklist(
            @RequestParam(value = "name", required = true ) String name,
            @RequestParam(value = "imsi", required = true ) String imsi,
            @RequestParam(value = "remark", required = false ) String remark,
            @RequestParam(value = "receivers", required = false ) String receivers
    ) {
        JSONObject jsonObject = new JSONObject();
        //// TODO: 2016/11/8
       
        //if (imsi.)
        @SuppressWarnings("unused")
		boolean isAdd = false;
        isAdd = ConvertTools.isNumeric(imsi);
        if (isAdd = false)
        {
        	 jsonObject.put("status", false);
             jsonObject.put("message", "imsi含有非数字");
             return jsonObject;
        }
        
        if(imsi.trim().length() != 15)
        {
        	jsonObject.put("status", false);
            jsonObject.put("message", "imsi长度不合法");
            return jsonObject;
        }
        	
        isAdd = targetAlarmServ.addBlackToList(name, imsi, remark, receivers);
        
        if (isAdd = true)
        {
        	 jsonObject.put("status", true);
             jsonObject.put("message", "成功");
        }
        else
        {
        	 jsonObject.put("status", false);
             jsonObject.put("message", "失败");
        }
            
        return  jsonObject;
    }

    @RequestMapping("/delBlacklist")
    @ResponseBody
    public JSONObject delBlacklist( @RequestParam(value = "id[]", required = true ) String[] ids ) {
        JSONObject jsonObject = new JSONObject();
        //// TODO: 2016/11/8
        @SuppressWarnings("unused")
		boolean isDel = false;
        
        isDel = targetAlarmServ.delBlackList(ids);
        
        if (isDel = true)
        {
        	 jsonObject.put("status", true);
             jsonObject.put("message", "成功");
        }
        else
        {
        	 jsonObject.put("status", false);
             jsonObject.put("message", "失败");
        }
            
        return  jsonObject;
    }


    @RequestMapping("/delAllBlacklist")
    @ResponseBody
    public JSONObject delAllBlacklist( @RequestParam(value = "id", required = true ) String[] ids ) {
        JSONObject jsonObject = new JSONObject();

        @SuppressWarnings("unused")
        boolean isDel = false;

        isDel = targetAlarmServ.delAllBlackList();

        if (isDel = true)
        {
            jsonObject.put("status", true);
            jsonObject.put("message", "成功");
        }
        else
        {
            jsonObject.put("status", false);
            jsonObject.put("message", "失败");
        }

        return  jsonObject;
    }





    @RequestMapping(value = "/addTargetRuleFromFile", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject addTargetRuleFromFile( 
    		 /* @RequestParam(value = "filename", required = true ) String filename,
              @RequestParam(value = "uploadFilename", required = true ) String uploadFilename*/
    		 @RequestParam("uploadFile") MultipartFile uploadFile
        ) {
    	JSONObject jsonObject = new JSONObject();
    	JSONObject dataObject = new JSONObject();
    	
    	@SuppressWarnings("unused")
		boolean isUploadSucc = false;
    
    	//File file = new File(filename);
    	isUploadSucc = importTargetRuleServ.readExcelFile(uploadFile);
    	
    	 if (isUploadSucc == true)
         {
         	 jsonObject.put("status", true);
              jsonObject.put("message", "成功");
              jsonObject.put("data", dataObject );
         }
         else 
         {
         	 jsonObject.put("status", false);
              jsonObject.put("message", "失败");
         }
        return jsonObject;
    }
    
    /***
     *  更新消息接收人
     * @param type  Integer, 0-黑名单消息接收人，1-归属地消息接受人
     * @param id Integer, 用户ID
     * @param receivers String， 通知人信息，JSON格式数据字符串
     * @return
     */
    @RequestMapping("/updateReceivers")
    @ResponseBody
    public  JSONObject updateReceivers(
            @RequestParam(value = "type", required = true ) Integer type,
            @RequestParam(value = "id", required = true ) Integer id,
            @RequestParam(value = "receivers", required = false ) String receivers
    ){
        JSONObject jsonObject = new JSONObject();
        //// TODO: 2016/11/8
        
        @SuppressWarnings("unused")
		boolean isUpdate = false;
        
        isUpdate = targetAlarmServ.updateReceviers(type, id, receivers);
        
        if (isUpdate = true)
        {
        	 jsonObject.put("status", true);
             jsonObject.put("message", "成功");
        }
        else
        {
        	 jsonObject.put("status", false);
             jsonObject.put("message", "失败");
        }
            
        return  jsonObject;
    }


    @RequestMapping("/homeOwnership")
    public String homeOwnership(Map<String, Object> model ) {

        comInfoUtil.getTopHeaderInfo( model );
        comInfoUtil.getSystemInfo( model );

        //// TODO: 2016/11/7  
    	long roleId = SigletonBean.getRoleId();
        resourceServ.getResourceInfoByRoleId(model, roleId);
        model.put("user", userService.getUserDetailInfo());

        return "business/homeOwnershipManager";
    }


    @RequestMapping("/getHomeOwnership")
    @ResponseBody
    public JSONObject getHomeOwnership() {
        JSONObject jsonObject = new JSONObject();
        //// TODO: 2016/11/9
        //jsonObject = com.iekun.ef.test.ui.UITargetAlarmData.getBlacklist();
        jsonObject = targetAlarmServ.getHomeOwnershipList();
        return  jsonObject;
    }

    @RequestMapping("/addHomeOwnership")
    @ResponseBody
    public JSONObject addHomeOwnership(
            @RequestParam(value = "name", required = true ) String name,
            @RequestParam(value = "cityName", required = true ) String cityName,
            @RequestParam(value = "cityCode", required = true ) String cityCode,
            @RequestParam(value = "remark", required = false ) String remark,
            @RequestParam(value = "receivers", required = false ) String receivers
    ) {
        JSONObject jsonObject = new JSONObject();
        //// TODO: 2016/11/9
        @SuppressWarnings("unused")
		boolean isAdd = false;
        
        isAdd = targetAlarmServ.addHomeOwnership(name, cityName, cityCode, remark, receivers);
        
        if (isAdd = true)
        {
        	 jsonObject.put("status", true);
             jsonObject.put("message", "成功");
        }
        else
        {
        	 jsonObject.put("status", false);
             jsonObject.put("message", "失败");
        }
            
        return  jsonObject;
    }

    @RequestMapping("/delHomeOwnership")
    @ResponseBody
    public JSONObject delHomeOwnership( @RequestParam(value = "id[]", required = true ) String[] ids ) {
        JSONObject jsonObject = new JSONObject();
        //// TODO: 2016/11/9
        @SuppressWarnings("unused")
		boolean isDel = false;
        
        isDel = targetAlarmServ.delHomeOwnership(ids);
        
        if (isDel = true)
        {
        	 jsonObject.put("status", true);
             jsonObject.put("message", "成功");
        }
        else
        {
        	 jsonObject.put("status", false);
             jsonObject.put("message", "失败");
        }
            
        return  jsonObject;
    }

    @RequestMapping("/getAlarmRuleNames")
    @ResponseBody
    public JSONObject getAlarmRuleNames() {
        JSONObject jsonObject = new JSONObject();

        jsonObject = targetAlarmServ.getAllRules();
        return  jsonObject;
    }


}
