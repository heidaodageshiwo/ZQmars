package com.iekun.ef.dao;

import com.iekun.ef.model.OperCode;
import com.iekun.ef.model.OperCodeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OperCodeMapper {
    int countByExample(OperCodeExample example);

    int deleteByExample(OperCodeExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OperCode record);

    int insertSelective(OperCode record);

    List<OperCode> selectByExample(OperCodeExample example);

    OperCode selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OperCode record, @Param("example") OperCodeExample example);

    int updateByExample(@Param("record") OperCode record, @Param("example") OperCodeExample example);

    int updateByPrimaryKeySelective(OperCode record);

    int updateByPrimaryKey(OperCode record);

	List<OperCode> selectOperCodeList();
	
	OperCode getOperNameByOperCode(String operCode);
}