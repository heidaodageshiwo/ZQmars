package com.iekun.ef.dao;

import com.iekun.ef.model.OamParam;
import com.iekun.ef.model.OamParamExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OamParamMapper {
    int countByExample(OamParamExample example);

    int deleteByExample(OamParamExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OamParam record);

    int insertSelective(OamParam record);

    List<OamParam> selectByExample(OamParamExample example);

    OamParam selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OamParam record, @Param("example") OamParamExample example);

    int updateByExample(@Param("record") OamParam record, @Param("example") OamParamExample example);

    int updateByPrimaryKeySelective(OamParam record);

    int updateByPrimaryKey(OamParam record);
}