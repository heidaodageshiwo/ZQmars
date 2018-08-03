package com.iekun.ef.dao;

import com.iekun.ef.jms.vo.send.SendImsi;
import com.iekun.ef.model.TargetRule;
import com.iekun.ef.model.TargetRuleExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface TargetRuleMapper {
    int countByExample(TargetRuleExample example);

    int deleteByExample(TargetRuleExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TargetRule record);

    int insertSelective(TargetRule record);

    List<TargetRule> selectByExampleWithBLOBs(TargetRuleExample example);

    List<TargetRule> selectByExample(TargetRuleExample example);

    TargetRule selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TargetRule record, @Param("example") TargetRuleExample example);

    int updateByExampleWithBLOBs(@Param("record") TargetRule record, @Param("example") TargetRuleExample example);

    int updateByExample(@Param("record") TargetRule record, @Param("example") TargetRuleExample example);

    int updateByPrimaryKeySelective(TargetRule record);

    int updateByPrimaryKeyWithBLOBs(TargetRule record);

    int updateByPrimaryKey(TargetRule record);

	List<TargetRule> getTargetRuleList(Map<String, Object> params);

	void updateTargetRuleReceivers(Map<String, Object> params);

	List<TargetRule> getDiscripInfoFromTargetRule(String imsi);

	void updateDeleteFlagByPrimaryKey(long parseLong);
    void updateAllDeleteFlagByPrimaryKey();

	List<TargetRule> getAllTargetRule();

	void updateTarget(TargetRule targetRule);

	List<SendImsi> getAllTargetRules();
	
	List<String> getTargetImsiListByUserId(Map<String, Object> params);
}