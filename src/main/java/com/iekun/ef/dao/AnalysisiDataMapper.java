package com.iekun.ef.dao;

import com.iekun.ef.model.AnalysisData;
import com.iekun.ef.model.Device;
import com.iekun.ef.model.DeviceDetailsCount;
import com.iekun.ef.model.Site;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AnalysisiDataMapper {


//    List<Device> selectDeviceDetailsBySiteId(Long siteId);
//    //List<DeviceDetailsCount> getDeviceDetailsCountlist(String deviceSn);
//    List<DeviceDetailsCount> getDeviceDetailsCountlist(@Param("devicesn") String devicesn, @Param("table") String table);
//    String getTableName(String date);
//    List<Site> selectSiteListByUserId(Long userId);


    List<AnalysisData> getTueinfoData(@Param("condition") String condition,@Param("condition2") String condition2);

    List<AnalysisData> getTueinfoDataBydevicesn(@Param("id") String id,@Param("condition3") String condition3);
   // List<AnalysisData> queryAnalysisiDataAjaxByImsi(String id);
   List<AnalysisData> queryAnalysisiDataAjaxByImsi(@Param("id") String id,@Param("condition2") String condition2);
/*
    List<AnalysisData> queryAnalysisiDataAjaxByAll(@Param("condition") String condition);
*/

    List<AnalysisData> queryAnalysisiDataAjaxByAll(@Param("condition") String condition,@Param("condition2") String condition2);

    List<AnalysisData> getBMD();
}