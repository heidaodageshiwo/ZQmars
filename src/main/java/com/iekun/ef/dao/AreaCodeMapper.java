package com.iekun.ef.dao;

import com.iekun.ef.model.AreaCode;
import com.iekun.ef.model.AreaCodeExample;
import com.iekun.ef.model.SelfAreaCode;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AreaCodeMapper {
    int countByExample(AreaCodeExample example);

    int deleteByExample(AreaCodeExample example);

    int deleteByPrimaryKey(Long id);

    int insert(AreaCode record);

    int insertSelective(AreaCode record);

    List<AreaCode> selectByExample(AreaCodeExample example);

    AreaCode selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") AreaCode record, @Param("example") AreaCodeExample example);

    int updateByExample(@Param("record") AreaCode record, @Param("example") AreaCodeExample example);

    int updateByPrimaryKeySelective(AreaCode record);

    int updateByPrimaryKey(AreaCode record);

	List<AreaCode> selectProvinceList();

	List<AreaCode> selectTownList(long cityId);

	List<AreaCode> selectCityList(long provinceId);

	List<SelfAreaCode> selectCityCodeList(Integer zoneLevel);

    List<AreaCode> selectAreaCodeList();

}