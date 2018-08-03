package com.iekun.ef.dao;

import com.iekun.ef.model.DeviceLicense;
import com.iekun.ef.model.DeviceLicenseExample;
import com.iekun.ef.model.Upgrade;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface DeviceLicenseMapper {
    int countByExample(DeviceLicenseExample example);

    int deleteByExample(DeviceLicenseExample example);

    int deleteByPrimaryKey(Long id);

    int insert(DeviceLicense record);

    int insertSelective(DeviceLicense record);

    List<DeviceLicense> selectByExample(DeviceLicenseExample example);

    DeviceLicense selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") DeviceLicense record, @Param("example") DeviceLicenseExample example);

    int updateByExample(@Param("record") DeviceLicense record, @Param("example") DeviceLicenseExample example);

    int updateByPrimaryKeySelective(DeviceLicense record);

    int updateByPrimaryKey(DeviceLicense record);

	List<DeviceLicense> getDeviceLicenseList();

	void insertDeviceLicense(Map<String, Object> params);

	long getDvLicenseCnt(Map<String, Object> params);

	List<DeviceLicense> selectDvLicenseList(Map<String, Object> params);

	void updateUpgrade(Map<String, Object> params);

	List<DeviceLicense> selectUnsendDvLicenseList(Map<String, Object> params);
}