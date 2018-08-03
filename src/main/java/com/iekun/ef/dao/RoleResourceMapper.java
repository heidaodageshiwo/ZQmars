package com.iekun.ef.dao;

import com.iekun.ef.model.RoleResource;
import com.iekun.ef.model.RoleResourceExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface RoleResourceMapper {
    int countByExample(RoleResourceExample example);

    int deleteByExample(RoleResourceExample example);

    int deleteByPrimaryKey(Long id);

    int insert(RoleResource record);

    int insertSelective(RoleResource record);

    List<RoleResource> selectByExample(RoleResourceExample example);

    RoleResource selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") RoleResource record, @Param("example") RoleResourceExample example);

    int updateByExample(@Param("record") RoleResource record, @Param("example") RoleResourceExample example);

    int updateByPrimaryKeySelective(RoleResource record);

    int updateByPrimaryKey(RoleResource record);

	void deleteByRoleId(long roleId);

	List<RoleResource> getRecordListByParentId(long parentId);

	RoleResource selectByResourceId(Map<String, Object> params);
}