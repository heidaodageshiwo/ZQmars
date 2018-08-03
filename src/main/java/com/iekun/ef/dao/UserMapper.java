package com.iekun.ef.dao;

import com.iekun.ef.model.User;
import com.iekun.ef.model.Role;
import com.iekun.ef.model.UserExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int countByExample(UserExample example);

    int deleteByExample(UserExample example);

    int deleteByPrimaryKey(Long id);

    int insert(User record);
    
    List<User> selectUserList();
    
    List<User> selectUserListByRoleId(Long id);
    
    List<Role> selectRoleList();
    
    int insertSelective(User record);

    List<User> selectByExample(UserExample example);

    User selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);

    int updateByExample(@Param("record") User record, @Param("example") UserExample example);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

	void updateById(User user);

	void updatePasswordById(User user);

	void setAccountActiveById(User user);

    User findByLoginName( String loginName );

	void updateUserSelfInfo(User user);

	long getUserIdByUserName(String userName);

	String getEmailByUid(long userId);

	
}