package com.iekun.ef.dao;

import com.iekun.ef.model.SysParam;
import com.iekun.ef.model.SysParamExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SysParamMapper {
    int countByExample(SysParamExample example);

    int deleteByExample(SysParamExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SysParam record);

    int insertSelective(SysParam record);

    List<SysParam> selectByExample(SysParamExample example);

    SysParam selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SysParam record, @Param("example") SysParamExample example);

    int updateByExample(@Param("record") SysParam record, @Param("example") SysParamExample example);

    int updateByPrimaryKeySelective(SysParam record);

    int updateByPrimaryKey(SysParam record);
    
    int updateByParaKey(SysParam record);

    SysParam getSmsTemplate(String key);
    
    int updateSysPara(SysParam record);
    
}