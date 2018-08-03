package com.iekun.ef.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.iekun.ef.model.Message;
import com.iekun.ef.model.User;
import com.iekun.ef.service.*;
import com.iekun.ef.util.ComInfoUtil;
import com.iekun.ef.util.CommonConsts;
import com.iekun.ef.util.TimeUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by  jiangqi.yang on 2016/10/21.
 */

@Controller
public class UserController {

    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;
    
    @Autowired
	private ResourceService resourceServ;
    
    @Autowired
    private MainService mainServ;

    @Autowired
    private ComInfoUtil comInfoUtil;

    @Autowired
    private LicenseService licenseService;
    
    @Autowired
    private EmailService emailServ;

    @Autowired
    private MessageService  messageService;

    @RequestMapping("/user/login")
    public String login( Map<String, Object> model ) {

        //// TODO: 2016/10/26  
        //Map<String, Object> systemInfo = com.iekun.ef.test.ui.UITestData.getSystemInfo();
    	Map<String, Object> systemInfo = mainServ.getSystemInfo();
        model.put("systemInfo", systemInfo);

        return "user/login";
    }

    @RequestMapping( value = "/user/loginPost", method = RequestMethod.POST )
    @ResponseBody
    public JSONObject loginPost(
            Map<String, Object> model,
            @RequestParam(value = "username", required = true ) String username,
            @RequestParam(value = "password", required = true ) String password,
            @RequestParam(value = "remember", required = false ) String remember
    ) {

        JSONObject jsonObject  = new JSONObject();

        boolean rememberMe = false;
        if( remember !=null && remember.equals("on")){
            rememberMe = true;        }

        /*if( 0 == licenseService.verifyLicense() ) {
            jsonObject =  userService.login( username, password, rememberMe );
        } else {
            jsonObject.put("status",  false);
            jsonObject.put("message",  "系统没有授权,请查看授权信息");
        }*/
            jsonObject =  userService.login( username, password, rememberMe );

        return jsonObject;
    }


    @RequestMapping("/user/forgetPassword")
    public String forgetPassword( Map<String, Object> model ) {

        //// TODO: 2016/10/26
        //Map<String, Object> systemInfo = com.iekun.ef.test.ui.UITestData.getSystemInfo();
    	Map<String, Object> systemInfo = mainServ.getSystemInfo();
        model.put("systemInfo", systemInfo);

        return "user/forgetPassword";
    }

    @RequestMapping( value = "/user/findPwdPost", method = RequestMethod.POST )
    @ResponseBody
    public JSONObject findPwdPost(
            Map<String, Object> model,
            @RequestParam(value = "username", required = true ) String username,
            @RequestParam(value = "email", required = true ) String email
    ) {
        JSONObject jsonObject  = new JSONObject();
        boolean returnVal = false;
        long userId;
        String registerEmail = null;

        Map<String,Object> params=new HashMap<String, Object>();
        String pwd = CommonConsts.genRandomNum(6);
        
        userId = userService.getUseId(username);
        if (userId == 0)
        {
        	jsonObject.put("status",   false );
            jsonObject.put("message", "用户名错误!");
            return jsonObject;
        }
        
        registerEmail = userService.getEmailByUserId(userId);
        
        if((registerEmail == null) ||(!registerEmail.equals(email)))
        {
        	jsonObject.put("status",   false );
            jsonObject.put("message", "不是注册时的邮箱!");
            return jsonObject;
        }
        
        User user = new User();
        user.setUpdateTime(TimeUtils.timeFormatterStr.format(new Date())); 
        user.setPassword(pwd);
        user.setId(userId);
        returnVal = userService.updateUserPassword(user);
        if (true == returnVal)
        {
        	params.put("password", pwd);
        	params.put("user", username);
            returnVal = emailServ.createEmail(username, email, params, 3);
        }
        
        if (returnVal = true)
        {
        	jsonObject.put("status",   true );
            jsonObject.put("message", "密码已经发送到您注册邮箱，请查看!");
        }
        else
        {
        	jsonObject.put("status",   false );
            jsonObject.put("message", "找回密码失败");
        }
         
        return jsonObject;
    }

    @RequestMapping( value = "/user/logout" )
    public String logout() {
        userService.logout();
        return "redirect:/user/login";
    }


    @RequestMapping("/user/profile")
    public String profile( Map<String, Object> model ) {

        comInfoUtil.getTopHeaderInfo( model );
        comInfoUtil.getSystemInfo( model );

        //// TODO: 2016/11/5
    	long roleId = SigletonBean.getRoleId();
        resourceServ.getResourceInfoByRoleId(model, roleId);
        model.put("user", userService.getUserDetailInfo());
        
    	return "user/profile";
    }


    @RequestMapping("/user/updateSelfInfo")
    @ResponseBody
    public JSONObject updateSelfInfo(
            @RequestParam(value = "username", required = false ) String username,
            @RequestParam(value = "sex", required = false ) Integer sex,
            @RequestParam(value = "email", required = false ) String email,
            @RequestParam(value = "mobilePhone", required = false ) String mobilePhone,
            @RequestParam(value = "newPassword", required = false ) String password,
            @RequestParam(value = "reNewPassword", required = false ) String rePassword
    ) {

        JSONObject jsonObject  = new JSONObject();
        
        if ( password!= null && password != "")
        {
        	if(!(password.equals(rePassword)))
        	{
        		jsonObject.put("status",   false );
                jsonObject.put("message", "失败");
                return jsonObject;
        	}
        		
        }
        
        jsonObject = userService.updateUserSelfInfo(username, sex, email, mobilePhone, password);
  
        return jsonObject;
    }

    @RequestMapping("/user/notifications")
    public String notifications(  Map<String, Object> model  ){

        comInfoUtil.getTopHeaderInfo( model );
        comInfoUtil.getSystemInfo( model );

        long roleId = SigletonBean.getRoleId();
        resourceServ.getResourceInfoByRoleId(model, roleId);
        model.put("user", userService.getUserDetailInfo());

        return "user/notifications";
        
    }

    @RequestMapping("/user/getNotifications")
    @ResponseBody
    public JSONObject getNotifications(
            @RequestParam(value = "isRead", required = true ) Integer isRead,
            @RequestParam(value = "draw", required = true ) Integer draw,
            @RequestParam(value = "length", required = true ) Integer length,
            @RequestParam(value = "start", required = true ) Integer start
    ) {
        JSONObject jsonObject = new JSONObject();
        JSONArray recordArray = new JSONArray();

        try {
            long userId = SigletonBean.getUserId();
            Integer total =  messageService.getMessageCountByUserIsRead( userId, isRead );
            List<Message> messageList = messageService.getMessageByUserIsRead( userId, start, length, isRead );

            jsonObject.put("draw", draw);
            jsonObject.put("recordsTotal", total);
            jsonObject.put("recordsFiltered", total);
            jsonObject.put("data", recordArray );
            for ( Message message: messageList ) {

                JSONObject recordObject = new JSONObject();
                recordObject.put("id", message.getId());
                recordObject.put("message", message.getContent());
                recordObject.put("isRead", message.getIsRead() );
                String createTimeText  =  message.getCreateTime();
                Date createTime =  TimeUtils.timeFormatterStr.parse(createTimeText);
                recordObject.put("createTime", TimeUtils.timeFormatter.format(createTime));

                recordArray.add(recordObject);
            }
        } catch ( Exception e ) {
            e.printStackTrace();
            jsonObject.put("draw", draw);
            jsonObject.put("recordsTotal", 0);
            jsonObject.put("recordsFiltered", 0);
            jsonObject.put("data", recordArray );
        }
        return jsonObject;
    }

    @RequestMapping("/user/markNotificationRead")
    @ResponseBody
    public JSONObject markNotificationRead(
            @RequestParam(value = "ids[]", required = true ) Long[] ids
    ){
        JSONObject jsonObject = new JSONObject();
        try{

            for ( Long id: ids ) {
                messageService.markReadMessage( id );
            }

            jsonObject.put("status", true);
            jsonObject.put("message", "成功");

        } catch ( Exception e ) {
            jsonObject.put("status", false);
            jsonObject.put("message", "设置已读标记失败!");
        }

        return jsonObject;
    }

}
