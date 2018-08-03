package com.iekun.ef.dao;

import com.iekun.ef.model.Upgrade;
import com.iekun.ef.model.UpgradeExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface UpgradeMapper {
    int countByExample(UpgradeExample example);

    int deleteByExample(UpgradeExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Upgrade record);

    int insertSelective(Upgrade record);

    List<Upgrade> selectByExample(UpgradeExample example);

    Upgrade selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Upgrade record, @Param("example") UpgradeExample example);

    int updateByExample(@Param("record") Upgrade record, @Param("example") UpgradeExample example);

    int updateByPrimaryKeySelective(Upgrade record);

    int updateByPrimaryKey(Upgrade record);

	List<Upgrade> getUpgradeList();

	List<Upgrade> selectUpgradeList(Map<String, Object> params);

	long getUpgradeCnt(Map<String, Object> params);

	Upgrade getVesionInfoByVersionId(Long versionId);

	void updateUpgrade(Map<String, Object> params);

	List<Upgrade> selectUnsuccDvVerList(Map<String, Object> params);

	void updateUpgradeOfMaxTryTimes(Map<String, Object> params);
}