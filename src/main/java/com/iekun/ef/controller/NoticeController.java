package com.iekun.ef.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.iekun.ef.dao.DataTablePager;
import com.iekun.ef.model.EmailTemplate;
import com.iekun.ef.model.Outbox;
import com.iekun.ef.model.TargetAlarmNewEntity;
import com.iekun.ef.service.EmailService;
import com.iekun.ef.service.ResourceService;
import com.iekun.ef.service.SmsService;
import com.iekun.ef.service.UserService;

import com.iekun.ef.util.ComInfoUtil;
import com.iekun.ef.util.M16Tools;
import com.iekun.ef.util.TimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jiangqi.yang  on 2016/10/26.
 */

@Controller
public class NoticeController {

    public static Logger logger = LoggerFactory.getLogger( NoticeController.class );

    @Autowired
    private ResourceService resourceServ;
	
	@Autowired
    private UserService userService;
	
	@Autowired
	private SmsService smsServ;

    @Autowired
    private ComInfoUtil comInfoUtil;

    @Autowired
    private EmailService emailService;
	
    @RequestMapping("/system/sms/template")
    public  String smsTemplate( Map<String, Object> model ) {

        comInfoUtil.getTopHeaderInfo( model );
        comInfoUtil.getSystemInfo( model );

        //// TODO: 2016/11/21
    	long roleId = SigletonBean.getRoleId();
        resourceServ.getResourceInfoByRoleId(model, roleId);
        model.put("user", userService.getUserDetailInfo());

        return "system/smsTemplate";
    }

    @RequestMapping("/system/sms/getSmsTemplate")
    @ResponseBody
    public JSONObject getSmsTemplate() {
        JSONObject jsonObject = new JSONObject();

        //// TODO: 2016/11/21
        //jsonObject = com.iekun.ef.test.ui.UINotifyData.getSmsTemplate();
        jsonObject = smsServ.getSmsTemplate();

        return jsonObject;
    }

    @RequestMapping("/system/sms/setSmsTemplate")
    @ResponseBody
    public  JSONObject setSmsTemplate(
            @RequestParam(value = "smsTemplate", required = true)  String smsTemplate
    ) {
        JSONObject jsonObject = new JSONObject();
        //// TODO: 2016/11/21
        jsonObject = smsServ.setSmsTemplate(smsTemplate);

        return jsonObject;
    }


    @RequestMapping("/system/sms/history")
    public  String smsHistory( Map<String, Object> model ) {

        comInfoUtil.getTopHeaderInfo( model );
        comInfoUtil.getSystemInfo( model );

        //// TODO: 2016/11/21  
        long roleId = SigletonBean.getRoleId();
        resourceServ.getResourceInfoByRoleId(model, roleId);
        model.put("user", userService.getUserDetailInfo());
        
        return "system/smsHistory";
    }

    @RequestMapping("/system/sms/queryHistorySms")
    @ResponseBody
    public JSONObject queryHistorySms(
            @RequestParam(value = "draw", required = false ) Integer draw,
            @RequestParam(value = "length", required = false ) Integer length,
            @RequestParam(value = "start", required = false ) Integer start,
            @RequestParam(value = "startDate", required = false ) String startDate,
            @RequestParam(value = "endDate", required = false ) String endDate,
            @RequestParam(value = "notifyName", required = false ) String notifyName,
            @RequestParam(value = "notifyPhone", required = false ) String notifyPhone
    ) {
        JSONObject jsonObject = new JSONObject();

        //// TODO: 2016/11/21
        //jsonObject =  com.iekun.ef.test.ui.UINotifyData.queryHistorySms( draw, length, start, startDate,
        //        endDate, notifyName, notifyPhone);
        jsonObject =  smsServ.queryHistorySms( draw, length, start, startDate,
                endDate, notifyName, notifyPhone);

        return  jsonObject;
    }

    @RequestMapping("/system/email/template")
    public  String emailTemplate( Map<String, Object> model ) {

        comInfoUtil.getTopHeaderInfo( model );
        comInfoUtil.getSystemInfo( model );

        long roleId = SigletonBean.getRoleId();
        resourceServ.getResourceInfoByRoleId(model, roleId);
        model.put("user", userService.getUserDetailInfo());

        return "system/emailTemplate";
    }

    @RequestMapping("/system/email/getEmailTpls")
    @ResponseBody
    public JSONObject getEmailTpls( ) {

        JSONObject jsonObject = new JSONObject();
        JSONArray dataArray = new JSONArray();
        List<EmailTemplate> templates = emailService.getTemplates();

        for ( EmailTemplate template: templates ) {
            JSONObject emailTmp = new JSONObject();
            emailTmp.put("id", template.getId());
            emailTmp.put("eventType", template.getEventType());
            emailTmp.put("active", template.getActive());
            emailTmp.put("subject", template.getSubject());
            emailTmp.put("content", template.getContentTpl());
            emailTmp.put("remark", template.getRemark());
            emailTmp.put("createTime", TimeUtils.convertDateNoSepToSep( template.getCreateTime() ));
            dataArray.add(emailTmp);
        }

        jsonObject.put("status", true);
        jsonObject.put("message", "成功");
        jsonObject.put("data", dataArray);

        return jsonObject;
    }


    @RequestMapping("/system/email/delEmailTps")
    @ResponseBody
    public JSONObject delEmailTps( @RequestParam(value = "id[]", required = true ) Long[]  ids ) {
        JSONObject jsonObject = new JSONObject();

        for ( Long id: ids ) {

            int result =  emailService.deleteTemplate(id);
            if( 0 != result ){
                jsonObject.put("status", false);
                jsonObject.put("message", "删除模板失败！");
                return jsonObject;
            }

        }

        jsonObject.put("status", true);
        jsonObject.put("message", "成功");
        return jsonObject;
    }

    @RequestMapping("/system/email/createEmailTps")
    @ResponseBody
    public JSONObject createEmailTps(
            @RequestParam(value = "emailSubject", required = true ) String  emailSubject,
            @RequestParam(value = "contentTpl", required = false ) String  contentTpl,
            @RequestParam(value = "eventType", required = true ) Integer  eventType,
            @RequestParam(value = "emailRemark", required = false ) String  emailRemark
    ) {
        JSONObject jsonObject = new JSONObject();

        if( null==contentTpl ){
            contentTpl = "";
        }

        String createTime = TimeUtils.timeFormatterStr.format(new Date());
        EmailTemplate emailTpl = new EmailTemplate( eventType, 0, emailSubject, contentTpl, emailRemark, createTime );
        int result = emailService.createTemplate(emailTpl);
        if(0 == result ) {
            jsonObject.put("status", true);
            jsonObject.put("message", "成功");
        } else {
            jsonObject.put("status", false);
            jsonObject.put("message", "模板创建失败");
        }

        return jsonObject;
    }

    @RequestMapping("/system/email/updateEmailTps")
    @ResponseBody
    public JSONObject updateEmailTps(
            @RequestParam(value = "id", required = true ) Long  id,
            @RequestParam(value = "emailSubject", required = true ) String  emailSubject,
            @RequestParam(value = "contentTpl", required = false ) String  contentTpl,
            @RequestParam(value = "eventType", required = true ) Integer  eventType,
            @RequestParam(value = "emailRemark", required = false ) String  emailRemark
    ) {
        JSONObject jsonObject = new JSONObject();

        int result = emailService.updateTemplate( id, emailSubject, contentTpl,
                eventType, emailRemark);
        if( 0 == result ) {
            jsonObject.put("status", true);
            jsonObject.put("message", "成功");
        } else {
            jsonObject.put("status", false);
            jsonObject.put("message", "更新失败");
        }

        return jsonObject;
    }


    @RequestMapping("/system/email/setEmailTpsActive")
    @ResponseBody
    public JSONObject setEmailTpsActive(
            @RequestParam(value = "id", required = true ) Long  id,
            @RequestParam(value = "active", required = true ) Integer  active
    ) {
        JSONObject jsonObject = new JSONObject();
        int result = 1;
        if( 0 == active ) {
            //停用
            result = emailService.inactiveTemplate(id);
        } else {
            //启用
            result = emailService.activeTemplate(id);
        }

        if( 0 == result) {
            jsonObject.put("status", true);
            jsonObject.put("message", "成功");
        } else {
            jsonObject.put("status", false);
            jsonObject.put("message", "失败");
        }

        return jsonObject;
    }


    @RequestMapping("/system/email/history")
    public  String emailHistory( Map<String, Object> model ) {

        comInfoUtil.getTopHeaderInfo( model );
        comInfoUtil.getSystemInfo( model );

        long roleId = SigletonBean.getRoleId();
        resourceServ.getResourceInfoByRoleId(model, roleId);
        model.put("user", userService.getUserDetailInfo());

        return "system/emailHistory";
    }

    @RequestMapping("/system/email/queryHistoryEmail")
    @ResponseBody
    public JSONObject queryHistoryEmail(
            HttpServletRequest request
    ) {
        String formData = request.getParameter("formData");
        Map<String, String> params = (Map<String, String>)JSONObject.parse(formData);
        String startTime = params.get("startTime");
        String endTime = params.get("endTime");
        String email = params.get("email");
        JSONObject jsonObject = new JSONObject();

        //分页处理
        int iDisplayStart = M16Tools.stringToNumber(request.getParameter("iDisplayStart"));
        int iDisplayLength = M16Tools.stringToNumber(request.getParameter("iDisplayLength"));
        String sEcho = request.getParameter("sEcho");
        int startNum = iDisplayStart / iDisplayLength + 1;

        PageHelper.startPage(startNum, iDisplayLength);
        List<Outbox> outboxList = emailService.queryHistory( startTime, endTime, email);
        PageInfo pg = new PageInfo(outboxList);
        DataTablePager page = new DataTablePager();
        page.setDataResult(outboxList);
        page.setiTotalRecords(pg.getTotal());
        page.setiTotalDisplayRecords(pg.getTotal());
        page.setiDisplayLength(iDisplayLength);
        page.setsEcho(sEcho);

        return (JSONObject)JSONObject.toJSON(page);
    }

    @RequestMapping("/system/email/getEmailDetail")
    @ResponseBody
    public JSONObject getEmailDetail(
            @RequestParam(value = "id", required = true ) Long id
    ) {
        JSONObject jsonObject = new JSONObject();
        JSONObject emailObject = new JSONObject();

        Outbox email = emailService.getHistoryEmailDetail(id);
        if( null == email ) {
            jsonObject.put("status", false);
            jsonObject.put("message", "失败，没有邮件");
        } else {

            emailObject.put("id",  email.getId());
            emailObject.put("subject", email.getEmailSubject() );
            emailObject.put("sender", emailService.getEmailForm() );
            emailObject.put("receiver", email.getNotifyEmail() );
            emailObject.put("body", email.getContent());
            emailObject.put("sendTime", TimeUtils.convertDateNoSepToSep(email.getLastSendTime()) );

            jsonObject.put("status", true);
            jsonObject.put("message", "成功");
            jsonObject.put("data", emailObject);
        }
        return jsonObject;
    }

}
