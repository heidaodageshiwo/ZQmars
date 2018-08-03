package com.iekun.ef.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iekun.ef.dao.RoleMapper;
import com.iekun.ef.dao.RoleResourceMapper;
import com.iekun.ef.model.Role;

@Service
public class RoleService {

	@Autowired
	private RoleMapper roleMapper;
	
	@Autowired
	private RoleResourceMapper roleSourceMapper;
	
	
	public boolean  selectRoleListByRoleName(String Name) {
		// TODO Auto-generated method stub
		
		List<Role> roleList = roleMapper.selectRoleListByRoleName(Name);
		
		if (roleList.isEmpty())
		{
			return false;
		}
		else
		{
			return true;
		}
	}

	public void inserRoleInfo(Role role) {
		// TODO Auto-generated method stub
		
		roleMapper.insertSelective(role);
		
	}
	
	public long getRoleIdByRoleName(String RoleName){
		
		long roleId = roleMapper.getRoleIdByRoleName(RoleName);
		
		return roleId;
	}
	
	public String getRoleNameByRoleId(String roleId){
		
		String RoleName = roleMapper.getRoleNameByRoleId(Long.parseLong(roleId));
		
		return RoleName;
	}

	public boolean delRoles(String[] roleIds) {
		// TODO Auto-generated method stub
		
		for(int i =0; i < roleIds.length; i++)
		{
			roleSourceMapper.deleteByRoleId(Long.parseLong(roleIds[i]));
			
			roleMapper.deleteByPrimaryKey(Long.parseLong(roleIds[i]));
			
		}
				
		return true;
	}

	public boolean setDefaultRole(String roleId) {
		// TODO Auto-generated method stub
		Role role = new Role();
		role.setDefaultFlag(true);
		role.setId(Long.parseLong(roleId));
		
		roleMapper.updateByPrimaryKeySelective(role);
		
		roleMapper.clearDefaultRoleWithoutDefinedRoleId(Long.parseLong(roleId));
		
		return true;
	}
			
}
