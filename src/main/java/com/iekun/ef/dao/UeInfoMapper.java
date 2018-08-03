package com.iekun.ef.dao;

import com.iekun.ef.model.UeInfo;
import com.iekun.ef.model.UeInfoExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface UeInfoMapper {
    int countByExample(UeInfoExample example);

    int deleteByExample(UeInfoExample example);

    int deleteByPrimaryKey(Long id);

    int insertUeInfo(UeInfo record);
    
    int insertSelective(UeInfo record);

    List<UeInfo> selectByExample(UeInfoExample example);

    UeInfo selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") UeInfo record, @Param("example") UeInfoExample example);

    int updateByExample(@Param("record") UeInfo record, @Param("example") UeInfoExample example);

    int updateByPrimaryKeySelective(UeInfo record);

    int updateByPrimaryKey(UeInfo record);

	List<UeInfo> selectUeInfoList(Map<String, Object> params);

	long getUeInfoCnt(Map<String, Object> params);

    long getUeInfoTotal();

    long getUeInfoCountByDay( Map<String, Object> params );

    long getUeInfoCountByHour( Map<String, Object> params );

    long getUeInfoCountByMin( Map<String, Object> params );

    List<Map<String, String>> getUeInfoBySuspect( @Param(value="startTime")  String startTime,
                                                  @Param(value="endTime") String endTime,
                                                  @Param(value="imsi") String imsi );


    List<UeInfo> selectUeInfoSql( @Param(value="selectSql") String selectSql );

    List<Map<String, String>> selectUeInfoAggrByMinForIMSIFollow( @Param(value="startTime")  String startTime,
                                         @Param(value="endTime")  String  endTime,
                                         @Param(value="imsi") String imsi );

    int createTempTable( @Param(value = "tableName") String tableName );

    int deleteTempTable( @Param(value = "tableName") String tableName );

    int existTempTable( @Param(value = "tableName") String tableName );

    int truncateTempTable( @Param(value = "tableName") String tableName );
    
    List<String> getTempImsiList(@Param(value = "tableName") String tableName);

    int insertIMSIFollowInterTable( Map<String, Object> params );

    List<Map<String, String>> getIMSIFollowRateList( @Param(value="tableName")  String tableName,
                                                     @Param(value="targetUeInfoTotal")  Integer  targetUeInfoTotal );


    int insertResidentPeopleInterTable(Map<String, Object> params);

    Long getTotalCountResidentPeople(  @Param(value="tableName")  String tableName );
    Long getFixedCountResidentPeople(  @Param(value="tableName")  String tableName,@Param(value="totalDay")  Long  totalDay  );
    Long getPeriodCountResidentPeople(  @Param(value="tableName")  String tableName, @Param(value="totalDay")  Long  totalDay  );

    List<Map<String, String>> getResidentPeopleFixedRateList( @Param(value="tableName")  String tableName,
                                                     @Param(value="totalDay")  Long  totalDay ,
                                                     @Param(value="start")  Integer start,
                                                     @Param(value="length")  Integer length );

    List<Map<String, String>> getResidentPeoplePeriodRateList( @Param(value="tableName")  String tableName,
                                                     @Param(value="totalDay")  Long  totalDay,
                                                   @Param(value="start")  Integer start,
                                                   @Param(value="length")  Integer length );

    int insertDataCollideInterTable( @Param(value="tableName")  String tableName, @Param(value="whereSql")  String whereSql);

    int insertDataCollideSumTable( @Param(value="tableName")  String tableName, @Param(value="whereSql")  String whereSql);

    int createDateCollideTempTable( @Param(value = "tableName") String tableName );

    List<UeInfo> getDataCollideUeInfoList( @Param(value = "sumUeIfnoTable") String sumUeIfnoTable, @Param(value = "tableName") String tableName );

}