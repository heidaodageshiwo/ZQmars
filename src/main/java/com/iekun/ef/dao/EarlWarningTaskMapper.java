package com.iekun.ef.dao;

import com.iekun.ef.model.Device;
import com.iekun.ef.model.Site;
import com.iekun.ef.model.TargetAlarm;
import com.iekun.ef.model.UeInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by java1 on 2017/7/25 0025.
 */
public interface EarlWarningTaskMapper {
	int insertUeinfo(Map<String, Object> map);

	List<TargetAlarm> getHeimingdan();

	List<TargetAlarm> gettableshuju(@Param("table") String table, @Param("condition") String condition);

	Site selectById(Long id);

	Device selectByDevSN(String sn);

	String getTableName(String date);
}
