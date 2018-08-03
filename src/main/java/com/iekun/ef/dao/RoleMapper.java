package com.iekun.ef.dao;

import com.iekun.ef.model.Role;
import com.iekun.ef.model.RoleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RoleMapper {
    int countByExample(RoleExample example);

    int deleteByExample(RoleExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Role record);

    int insertSelective(Role record);

    List<Role> selectByExample(RoleExample example);

    Role selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Role record, @Param("example") RoleExample example);

    int updateByExample(@Param("record") Role record, @Param("example") RoleExample example);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

	List<Role> selectRoleList();

	List<Role> selectRoleListByRoleName(String name);

	long getRoleIdByRoleName(String roleName);
	
	String getRoleNameByRoleId(Long id);

	void clearDefaultRoleWithoutDefinedRoleId(long roleId);
}