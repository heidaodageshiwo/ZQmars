package com.iekun.ef.service;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iekun.ef.dao.UserMapper;
import com.iekun.ef.dao.UserRoleMapper;
import com.iekun.ef.dao.RoleMapper;
import com.iekun.ef.dao.UeInfoMapper;
import com.iekun.ef.model.User;
import com.iekun.ef.model.UserRole;
import com.iekun.ef.model.Role;
import com.iekun.ef.service.PasswordHelper;
import com.iekun.ef.util.CommonConsts;
import com.iekun.ef.util.TimeUtils;
import com.iekun.ef.controller.SigletonBean;
import org.apache.shiro.SecurityUtils;

@Service
public class UserService {

	public static Logger logger = LoggerFactory.getLogger(UserService.class);
	
	  @Autowired
	  private UserMapper userMapper;
	  
	  @Autowired
	  private UeInfoMapper ueInfoMapper;
	  
	  @Autowired
	  private RoleMapper roleMapper;
	  
	  @Autowired
	  private UserRoleMapper userRoleMapper;
	  
	  @Autowired
	  private PasswordHelper passwordHelper;

	@Autowired
	private SessionDAO sessionDAO;
	  
	  private long userId;
	 /* public User getUserInfo(){
		  userId = 42;
		  User user=userMapper.selectByPrimaryKey(userId);
	      //User user=null;
	      return user;
	    }*/
	  public User  getUserDetailInfo(long userId)
	  {
		  User user = null;
		  
		  try {
			user=userMapper.selectByPrimaryKey(userId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  
		  return user;
	  }
	  	   
	  public List<User>  getUserListInfo(){
		  
		    //ueInfoMapper.notify();
		    List<User>  userList =(List<User>) userMapper.selectUserList();
	        //User user=null;
	        return userList;
	    }
	  
	  public List<Role> getListRoleInfo(){
		  
		  List<Role>  roleList =(List<Role>) roleMapper.selectRoleList();
	        //User user=null;
	      return roleList;
		  
	  }
	  
	  public String getRoleNameByRoleId(long id){
		  
		  String roleName = (String)roleMapper.getRoleNameByRoleId(id);
		  if (roleName == null)
		  {
			  roleName = "未知角色"; 
		  }
		  return roleName;
		  
	  }
	  	 
	  
	  public boolean delUsers(String[] userIds){
		  
		  for(int i =0; i < userIds.length; i++)
		  {
			  try {
				  
				userMapper.deleteByPrimaryKey(Long.parseLong(userIds[i]));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		  }
		  
		  return true;
	  }
		  
	  
	  private void filterUser(User user)throws Exception{
			if(user.getPassword() == null || user.getPassword().equals("")){
				user.setPassword(SigletonBean.pwd);
			}
			passwordHelper.encryptPassword(user);
			//user.setCreatorId(); 
			//todo,login模块登录时，才会使用session。setAttribute()保存当前登录用户的id,所以
			// login模块增加后才能使用缓存里面的userId。
			//long userId = 1;
			if((user.getId() == null ||(user.getId() == 0)))
			{
				user.setCreatorId(SigletonBean.getUserId());
			}
			
		}
		
		
		public boolean addUser(User user) 
		{
			try {
				
				this.filterUser(user);
				userMapper.insert(user);
				return true;
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			return false;
		}
		
	   public boolean updateUser(User user)
	   {
	     try {
			
	    	 userMapper.updateById(user);
			 return true;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   return false;
	   }
	   
	   public boolean updateUserPassword(User user)
	   {
		     try {
		    	 this.filterUser(user);
		    	 userMapper.updatePasswordById(user);
				return true;
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			   return false;
	   }
	   
	   public long getUseId(String userName)
	   {
		   long userId = 0;
		   try {
			userId = userMapper.getUserIdByUserName(userName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			userId = 0;
		}
		   return userId;
	   }
	   
	   
	   public boolean setAccountActive(User user)
	   {
	     try {
			
	    	userMapper.setAccountActiveById(user);
			return true;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   return false;
	   }

	public JSONObject login(String username, String password , boolean rememberMe ) {

		JSONObject jSONObject = new JSONObject();
		boolean success = true;

		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		token.setRememberMe(rememberMe);

		try{
			Subject currentUser = SecurityUtils.getSubject();
			
			if(/*currentUser.getSession() != null*/currentUser.isAuthenticated()){
				currentUser.logout();
			}
			this.beforeLogin(username);
			currentUser.login(token);
			this.setAttr(currentUser);
		}catch(Exception e){
			logger.info("登录异常：" + e.getMessage());
			success = false;
		}

		jSONObject.put("status",  success);

		if(!success){
			jSONObject.put("message", "用户名或密码错误");
		}
		else
		{
			jSONObject.put("message", "登陆成功");


		}

		return jSONObject;
	}

	private void beforeLogin(String username)throws Exception{
		Collection<Session> sessions = sessionDAO.getActiveSessions();
		for(Session session : sessions){
			if(username.equals(String.valueOf(session.getAttribute("loginName")))) {
				if(session.getAttribute("userId") != null && !session.getAttribute("userId").equals("")){
					//session.setTimeout(0);
					sessionDAO.delete(session);
					break;
				}
			}
		}
	}

	private void setAttr(Subject currentUser)throws Exception{
		User user = userMapper.findByLoginName((String)currentUser.getPrincipal());
		Session session = currentUser.getSession();
		session.setAttribute("userId", user.getId());
		session.setAttribute("loginName", user.getLoginName());
		session.setAttribute("userName", user.getUserName());
		session.setAttribute("roleId", user.getRoleId());
		SigletonBean.userIdSessionMaps.put(user.getId().toString(), session);
	}

	public JSONObject logout()  {

		JSONObject jSONObject = new JSONObject();
		String msg = "";

		boolean success = true;

		try{
			Subject currentUser = SecurityUtils.getSubject();
			if(currentUser.isAuthenticated()){
				SigletonBean.userIdSessionMaps.remove(currentUser.getSession().getAttribute("userId").toString());
				SigletonBean.showmaps.remove(currentUser.getSession().getAttribute("userId").toString());
				currentUser.logout();
			}
		}catch(Exception e){
			msg = "注销异常：" + e.getMessage();
			logger.info(msg);
			success = false;
		}

		jSONObject.put("success", success);

		if(!success){
			jSONObject.put("errorMsg", msg);
		}

		return jSONObject;
	}

	  /***
     * 获取当前用户信息
     * @return
     */
    public  Map<String, Object> getUserDetailInfo() {

    	long userId = SigletonBean.getUserId();
    	Map<String, Object> userInfo = new HashMap<String, Object>();
    	
    	User user=userMapper.selectByPrimaryKey(userId);
    	
        userInfo.put("id", userId);
        userInfo.put("userName", user.getUserName());
        userInfo.put("loginName", user.getLoginName());
        
        userInfo.put("roleId", user.getRoleId());
        userInfo.put("role", this.getRoleNameByRoleId(user.getRoleId().intValue()));
       
        userInfo.put("sexVal", user.getSex() );
        if(user.getSex() == 0)
        {
        	userInfo.put("sex", "男");
        }
        else
        {
        	userInfo.put("sex", "女");
        }
        userInfo.put("avata", "/img/avatar04.png"/*user.getAvator()*/);
        userInfo.put("email", user.getEmail());
        userInfo.put("mobilePhone", user.getMobilePhone());
        userInfo.put("remark", user.getRemark());
        userInfo.put("create_time", TimeUtils.getFormatTimeStr(user.getCreateTime()));
    

        return userInfo;
    }
    
    public List<User> getUserListByRoleId(String roleId)
    {
    	List<User>  userList =(List<User>) userMapper.selectUserListByRoleId(Long.parseLong(roleId));
    	
    	return userList;
    }

    
	public JSONObject updateUserSelfInfo(String username, Integer sex, String email, String mobilePhone,
			String password) {
	
		JSONObject jsonObject  = new JSONObject();
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		Date nowDate =  new Date();
		String noDateStr = df.format(nowDate);
		String updateTime;
		
		/*Map<String,Object> params=new HashMap<String, Object>();
		params.put("userId", SigletonBean.getUserId());*/
		updateTime = noDateStr;
		//params.put("updateTime", noDateStr);
		
		if (username == null || username.isEmpty())
        {
        	username = null;
        }
       
		
		/*if (sex == null || sex==0)
		{
		    	sex = null;
		}*/
		
		if (email == null || email.isEmpty())
        {
        	email = null;
        }
        
		
		if (mobilePhone == null || mobilePhone.isEmpty())
        {
			mobilePhone = null;
        }
       
		
		if (password == null || password.isEmpty())
        {
			password = null;
        }
		//( String userName, Integer sex, String password, String email,String mobilePhone, String updateTime)
		User user = new User(new Long(SigletonBean.getUserId()), username, sex, password, email, mobilePhone, updateTime);
		try {
			if(user.getPassword() != null){
				this.filterUser(user);
			}
			userMapper.updateUserSelfInfo(user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*try {
			this.filterUser(user);
			userMapper.updateUserSelfInfo(user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
				
		jsonObject.put("status",   true );
        jsonObject.put("message", "成功");
        return jsonObject;
		
	}

	public String getEmailByUserId(long userId) {
		String email = null;
		
		try {
			email = userMapper.getEmailByUid(userId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return email;
	}
	
	public boolean  checkLoginUserName(String loginName) {
		// TODO Auto-generated method stub
		
		User user = userMapper.findByLoginName(loginName);
		
		if (user == null)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
  
	public Long getUserIdByLoginUserName(String loginName) {
		
		User user = userMapper.findByLoginName(loginName);
		
		if (user == null)
		{
			return new Long(0);
		}
		else
		{
			return user.getId();
		}
	}
	
	 
}
