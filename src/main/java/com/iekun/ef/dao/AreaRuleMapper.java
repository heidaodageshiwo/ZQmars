package com.iekun.ef.dao;

import com.iekun.ef.jms.vo.send.SendImsi;
import com.iekun.ef.jms.vo.send.SendLocation;
import com.iekun.ef.model.AreaRule;
import com.iekun.ef.model.AreaRuleExample;
import com.iekun.ef.model.TargetRule;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface AreaRuleMapper {
    int countByExample(AreaRuleExample example);

    int deleteByExample(AreaRuleExample example);

    int deleteByPrimaryKey(Long id);

    int insert(AreaRule record);

    int insertSelective(AreaRule record);

    List<AreaRule> selectByExample(AreaRuleExample example);

    AreaRule selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") AreaRule record, @Param("example") AreaRuleExample example);

    int updateByExample(@Param("record") AreaRule record, @Param("example") AreaRuleExample example);

    int updateByPrimaryKeySelective(AreaRule record);

    int updateByPrimaryKey(AreaRule record);

	List<AreaRule> getDiscripInfoFromAreaRule(String cityName);
	
	List<AreaRule> getAreaRuleList(Map<String, Object> params);

	void updateAreaRuleReceivers(Map<String, Object> params);

	void updateDeleteFlagByPrimaryKey(long parseLong);

	List<SendLocation> getAllAreaRules();
	
	List<String> getTargetAreaCodeListByUserId(Map<String, Object> params);
}