package com.iekun.ef.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.iekun.ef.util.ComInfoUtil;
import com.iekun.ef.util.CommonConsts;
import com.iekun.ef.util.TimeUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSONObject;
import com.iekun.ef.dao.ResourceMapper;
import com.iekun.ef.dao.RoleMapper;
import com.iekun.ef.model.Resource;
import com.iekun.ef.model.Role;
import com.iekun.ef.model.User;
import com.iekun.ef.service.ResourceService;
import com.iekun.ef.service.RoleService;
import com.iekun.ef.service.UserService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by jiangqi.yang  on 2016/10/26.
 */

@Controller
public class UserManagerController {

    private static Logger logger = LoggerFactory.getLogger( UserManagerController.class );
    private SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
    //private String date=sdf.format(new Date());
    
    @Autowired
    private ResourceService resourceServ;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	

    @Autowired
    private ComInfoUtil comInfoUtil;
    
    @Autowired
	private ResourceMapper resourceMapper;
    
    @Autowired
	private RoleMapper roleMapper;

    @RequestMapping("/user/index")
    public String index(Map<String, Object> model ) {

        comInfoUtil.getTopHeaderInfo( model );
        comInfoUtil.getSystemInfo( model );

        //// TODO: 2016/10/31
    	long roleId = SigletonBean.getRoleId();
        resourceServ.getResourceInfoByRoleId(model, roleId);
        model.put("user", userService.getUserDetailInfo());

        return "user/user";
    }

    @RequestMapping("/user/role")
    public String roles(Map<String, Object> model ) {

        comInfoUtil.getTopHeaderInfo( model );
        comInfoUtil.getSystemInfo( model );

     	long roleId = SigletonBean.getRoleId();
        resourceServ.getResourceInfoByRoleId(model, roleId);
        model.put("user", userService.getUserDetailInfo());

        resourceServ.getResourceInfo(model);
        return "user/role";
    }

    @RequestMapping("/user/auth")
    public String auth(Map<String, Object> model ) {

        comInfoUtil.getTopHeaderInfo( model );
        comInfoUtil.getSystemInfo( model );

       	long roleId = SigletonBean.getRoleId();
        resourceServ.getResourceInfoByRoleId(model, roleId);
        model.put("user", userService.getUserDetailInfo());

        return "user/auth";
    }
    
    @RequestMapping("/user/getUsers")
    @ResponseBody
    public JSONObject getUsers() {

    	//User user = userService.getUserInfo();
    	
    	//String username = user.getUserName();
    	
    	List<User> userList=userService.getUserListInfo();
    	JSONObject jo=new JSONObject();
		JSONArray ja = new JSONArray();
	    for (User user : userList) {
	    	JSONObject userJo = new JSONObject();
	    	userJo.put("id", user.getId());
	    	userJo.put("loginName", user.getLoginName());
	    	userJo.put("userName", user.getUserName());
	    	userJo.put("sex", CommonConsts.getSexNameBySexId(user.getSex()));
	    	userJo.put("sexVal", user.getSex());
	    	userJo.put("email", user.getEmail());
	    	userJo.put("mobilePhone", user.getMobilePhone());
	    	userJo.put("role", userService.getRoleNameByRoleId(user.getRoleId().intValue()));
	    	userJo.put("roleId", user.getRoleId());
	    	userJo.put("createTime",user.getCreateTime());
	    	userJo.put("remark", user.getRemark());
	    	userJo.put("locked", user.getLocked());
	    	/*if (user.getLocked() == 1)
	    	{
	    		userJo.put("locked", "true");
	    	}
	    	else
	    	{
	    		userJo.put("locked", "false");
	    	}*/
	    	
	        ja.add(userJo);
		}
	    
	    jo.put("status", true);
	    jo.put("message", "成功");
	    jo.put("data", ja);
	  
		return jo;
       	
    }

    @RequestMapping("/user/getRoles")
    @ResponseBody
    public JSONObject getRoles() {

    	List<Role> roleList=userService.getListRoleInfo();
    	JSONObject jo=new JSONObject();
		JSONArray ja = new JSONArray();
	    for (Role role : roleList) {
	    	JSONObject roleJo = new JSONObject();
	    	roleJo.put("id", role.getId());
	    	roleJo.put("name", role.getName());
	    	roleJo.put("text", role.getRemark());
	       	roleJo.put("remark", role.getRemark());
	    	roleJo.put("defaultFlag", role.getDefaultFlag());
	    	roleJo.put("createTime", role.getCreateTime());
	    	JSONArray jaResources = new JSONArray();
	    	List<Resource>  resourceList =(List<Resource>) resourceMapper.selectSourceListByRoleId(role.getId());
	        for (Resource resource : resourceList)
	        {
	        	 JSONObject resourcesObj = new JSONObject();
	        	 resourcesObj.put("name", resource.getName());
	        	 resourcesObj.put("value", resource.getId());
	        	 jaResources.add(resourcesObj);
	        }
	        roleJo.put("resources", jaResources);
	        

	        ja.add(roleJo);
		}

	    jo.put("status", true);
	    jo.put("message", "成功");
	    jo.put("data", ja);
        return jo;

	}

    @RequestMapping("/user/setAccountActive")
    @ResponseBody
    public JSONObject setAccountActive(
            @RequestParam(value = "id", required = true ) String userId,
            @RequestParam(value = "locked", required = true ) String locked
    ) {
    	String date=sdf.format(new Date());
        JSONObject jsonObject  = new JSONObject();
        boolean returnVal = false;
        int lockSwitch = 0;
        if (locked.equals("true"))
        {
        	lockSwitch = 1;
        }
        User user = new User(Long.parseLong(userId),lockSwitch);
        user.setUpdateTime(date); 
        returnVal = userService.setAccountActive(user);
        if(returnVal == true)
        {
        	jsonObject.put("status", true);
            jsonObject.put("message", "成功");
            
        }
        else
        {
        	jsonObject.put("status", false);
            jsonObject.put("message", "创建用户失败");
        }
     
        return jsonObject;
    }

    @RequestMapping("/user/delUser")
    @ResponseBody
    public JSONObject delUser( @RequestParam(value = "id[]", required = true ) String[] userIds ) {
        JSONObject jsonObject  = new JSONObject();
        boolean returnVal = false;
        
        returnVal = userService.delUsers(userIds);
        //System.out.printf("userIds:%s",userIds);
        if(returnVal == true)
        {
        	jsonObject.put("status", true);
            jsonObject.put("message", "成功");
            
        }
        else
        {
        	jsonObject.put("status", false);
            jsonObject.put("message", "删除用户失败");
        }
  
        return jsonObject;
    }

    @RequestMapping("/user/updatePassword")
    @ResponseBody
    public JSONObject updatePassword(
            @RequestParam(value = "id", required = true ) String userId,
            @RequestParam(value = "newPassword", required = true ) String newPassword,
            @RequestParam(value = "reNewPassword", required = true ) String reNewPassword
    ) {
    	String date=sdf.format(new Date());
        JSONObject jsonObject  = new JSONObject();
        
        boolean returnVal = false;
        
        if (!(reNewPassword.matches(newPassword)))
		{
			  jsonObject.put("status", false);
		      jsonObject.put("message", "两次输入的密码不一致");
		      return jsonObject;
	    }
        
        User user = new User(Long.parseLong(userId),newPassword);
        user.setUpdateTime(date); 
        returnVal = userService.updateUserPassword(user);
  		if(returnVal == true)
        {
        	jsonObject.put("status", true);
            jsonObject.put("message", "成功");
            
        }
        else
        {
        	jsonObject.put("status", false);
            jsonObject.put("message", "修改密码失败");
        }

        return jsonObject;
    }

    @RequestMapping("/user/createUser")
    @ResponseBody
    public JSONObject createUser(
            @RequestParam(value = "loginName", required = true ) String loginName,
            @RequestParam(value = "role", required = true ) Integer roleId,
            @RequestParam(value = "password", required = true ) String password,
            @RequestParam(value = "rePassword", required = true ) String rePassword,
            @RequestParam(value = "userName", required = true ) String userName,
            @RequestParam(value = "sex", required = true ) Integer sex,
            @RequestParam(value = "email", required = false ) String email,
            @RequestParam(value = "mobilePhone", required = false ) String mobilePhone,
            @RequestParam(value = "remark", required = false ) String remark,
            @RequestParam(value = "locked", required = false ) String locked
    ) {
    	String date=sdf.format(new Date());
        JSONObject jsonObject  = new JSONObject();
        boolean returnVal = false;
        
        boolean isExist = false;
    	isExist = userService.checkLoginUserName(loginName); 
    	if(isExist == true)
    	{
			 jsonObject.put("status", false);
			 jsonObject.put("message", "登录用户名已经存在!");
			 return jsonObject;
    	}
    	
        if (!(rePassword.matches(password)))
		{
			  jsonObject.put("status", false);
		      jsonObject.put("message", "两次输入的密码不一致");
		      return jsonObject;
	    }
        
        int lockSwitch = 1;
        if (locked == null)
        {
        	lockSwitch = 0;
        }
        
        User user = new User(loginName,roleId,userName, sex, password, email,mobilePhone,remark,lockSwitch);
        user.setCreateTime(date); 
        returnVal = userService.addUser(user);
		if(returnVal == true)
        {
        	jsonObject.put("status", true);
            jsonObject.put("message", "成功");
            
        }
        else
        {
        	jsonObject.put("status", false);
            jsonObject.put("message", "创建用户失败");
        }
     
        return jsonObject;
    }

    @RequestMapping("/user/updateUser")
    @ResponseBody
    public JSONObject updateUser(
            @RequestParam(value = "id", required = true ) String id,
            @RequestParam(value = "role", required = true ) Integer roleId,
            @RequestParam(value = "userName", required = true ) String userName,
            @RequestParam(value = "sex", required = true ) Integer sex,
            @RequestParam(value = "email", required = false ) String email,
            @RequestParam(value = "mobilePhone", required = false ) String mobilePhone,
            @RequestParam(value = "remark", required = false ) String remark,
            @RequestParam(value = "locked", required = false ) String locked
    ) {
    	String date=sdf.format(new Date());
        JSONObject jsonObject  = new JSONObject();
        boolean returnVal = false;
        int lockSwitch = 1;
        if (locked == null)
        {
        	lockSwitch = 0;
        }
       
        User user = new User(Long.parseLong(id),roleId,userName, sex, email,mobilePhone,remark, lockSwitch);
 		returnVal = userService.updateUser(user);
		user.setUpdateTime(date); 
		if(returnVal == true)
        {
        	jsonObject.put("status", true);
            jsonObject.put("message", "成功");
            
        }
        else
        {
        	jsonObject.put("status", false);
            jsonObject.put("message", "创建用户失败");
        }
     
        return jsonObject;
    }


    @RequestMapping("/user/createRole")
    @ResponseBody
    public JSONObject createRole(
            @RequestParam(value = "roleName", required = true ) String roleName,
            @RequestParam(value = "remark", required = true ) String remark,
            @RequestParam(value = "resources", required = false ) String[] resources
    ){
    	/*一、检查roleName是否重名； 二、更新两个表，1：t_role 表的REMARK字段和roleName字段 ； 2： t_role_resource */
        JSONObject jsonObject = new JSONObject();
        boolean isExist = false;
        isExist = roleService.selectRoleListByRoleName(roleName);
        if (isExist == true)
        {
        	 jsonObject.put("status", false );
             jsonObject.put("message", "角色名已存在，请更换角色名");
             return jsonObject;
        }
        
        Role role = new Role();
        role.setName(roleName);
        role.setRemark(remark);
        role.setCreateTime(TimeUtils.timeFormatterStr.format(new Date()));
        role.setCreatorId(SigletonBean.getUserId());
        role.setDefaultFlag(false);
        role.setDeleteFlag(false);
        
        roleService.inserRoleInfo(role);
         
        long roleId = roleService.getRoleIdByRoleName(roleName);
        
        resourceServ.insertNewRoleResource(roleId,resources);
        
        jsonObject.put("status", true );
        jsonObject.put("message", "成功");
        return jsonObject;
    }

    @RequestMapping("/user/updateRole")
    @ResponseBody
    public JSONObject updateRole(
            @RequestParam(value = "id", required = true ) Long id,
//            @RequestParam(value = "roleName", required = true ) String roleName,
            @RequestParam(value = "remark", required = true ) String remark,
            @RequestParam(value = "resources", required = false ) String[] resources
    ){
    	/*更新两个表，1：t_role 表的REMARK字段； 2： t_role_resource */
        JSONObject jsonObject = new JSONObject();
        Role role = new Role();
        role.setRemark(remark);
        role.setId(id);
        role.setUpdateTime(TimeUtils.timeFormatterStr.format( new Date() ));
        roleMapper.updateByPrimaryKeySelective(role);
        
        resourceServ.updateRoleResource(id,resources);
        jsonObject.put("status", true );
        jsonObject.put("message", "成功");
        return jsonObject;
    }

    @RequestMapping("/user/delRole")
    @ResponseBody
    public JSONObject delRole(
            @RequestParam(value = "id[]", required = true ) String[] roleIds
    ){
        JSONObject jsonObject = new JSONObject();
        boolean isExistUsers = false;
        for(int i=0; i < roleIds.length; i++)
        {
        	isExistUsers = this.getUserByRoleId(roleIds[i]);
        	if (isExistUsers == true)
        	{
        		jsonObject.put("status", false );
                jsonObject.put("message",roleService.getRoleNameByRoleId(roleIds[i]) + "角色下存在用户" +  ", 不能删除");
                return jsonObject;
        	}
        }
        
        boolean retVal = roleService.delRoles(roleIds);
        if(retVal == false)
        {
        	jsonObject.put("status", false );
            jsonObject.put("message", "删除失败");
            return jsonObject;
        }
        
        jsonObject.put("status", true );
        jsonObject.put("message", "成功");
        return jsonObject;
    }
    
    private boolean getUserByRoleId(String roleId)
    {
    	List<User>  userList = userService.getUserListByRoleId(roleId);
    	if(userList.size() == 0)
    	{
    		return false;
    	}
    	return true;
    }

    @RequestMapping("/user/setRoleDefault")
    @ResponseBody
    public JSONObject setRoleDefault(
            @RequestParam(value = "id", required = true ) String roleId
    ){
        JSONObject jsonObject = new JSONObject();
        boolean retVal = roleService.setDefaultRole(roleId);
        
        if(retVal == false)
        {
        	jsonObject.put("status", false );
            jsonObject.put("message", "失败");
            return jsonObject;
        }
        
        jsonObject.put("status", true );
        jsonObject.put("message", "成功");
        return jsonObject;
    }

    @RequestMapping("/user/checkRoleName")
    @ResponseBody
    public String checkRoleName(
            @RequestParam(value = "roleName", required = true ) String roleName
    ){
    	boolean isExist = false;
    	isExist = roleService.selectRoleListByRoleName(roleName);
        if (isExist == true)
        {
        	return "false";
        }
        else
        {
        	return "true";
        }
    }
    
    @RequestMapping("/user/checkLoginName")
    @ResponseBody
    public String checkLoginName(
    		@RequestParam(value = "loginName", required = true ) String loginName
   ){
    	boolean isExist = false;
    	isExist = userService.checkLoginUserName(loginName);
        if (isExist == true)
        {
        	return "false";
        }
        else
        {
        	return "true";
        }
    }


}
