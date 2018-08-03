package com.iekun.ef.dao;

import com.iekun.ef.model.VersionLib;
import com.iekun.ef.model.VersionLibExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface VersionLibMapper {
    int countByExample(VersionLibExample example);

    int deleteByExample(VersionLibExample example);

    int deleteByPrimaryKey(Long id);

    int insert(VersionLib record);

    int insertSelective(VersionLib record);

    List<VersionLib> selectByExample(VersionLibExample example);

    VersionLib selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") VersionLib record, @Param("example") VersionLibExample example);

    int updateByExample(@Param("record") VersionLib record, @Param("example") VersionLibExample example);

    int updateByPrimaryKeySelective(VersionLib record);

    int updateByPrimaryKey(VersionLib record);

	VersionLib getVesionInfoByVersionId(Long versionId);

	List<VersionLib> getVersionLibList();

	void insertVersionLib(Map<String, Object> params);

	void deleteLibByPrimaryKey(Long id);

    String queryUserNameById(String userId);
}