package com.iekun.ef.dao;

import com.iekun.ef.model.ShardingTabInfo;
import com.iekun.ef.model.Site;
import com.iekun.ef.model.UeInfo;
import org.apache.commons.collections.map.HashedMap;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by sword.yu on 2017/1/18.
 */

@Component
public class AnalysisShardingDao {

    private final static Logger logger = LoggerFactory.getLogger(AnalysisShardingDao.class);

    private final SqlSession sqlSession;

    @Autowired
    private ShardingTabInfoMapper shardingTabInfoMapper;

    public AnalysisShardingDao(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    /**
     * 创建分析临时表
     * @param tableName 临时表名
     * @return
     */
    public int createTempTable( String tableName ){
        Map<String, Object> params = new HashedMap();
        params.put("tableName", tableName);
        return this.sqlSession.update("createTempTableSharding",params);
    }

    /**
     * 删除分析临时表
     * @param tableName 临时表名
     * @return
     */
    public int deleteTempTable( String tableName ){
        Map<String, Object> params = new HashedMap();
        params.put("tableName", tableName);
        return this.sqlSession.update("deleteTempTableSharding",params);
    }

    /**
     * 判断分析临时表是否存在
     * @param tableName 临时表名
     * @return
     */
    public int existTempTable(  String tableName ){
        Map<String, Object> params = new HashedMap();
        params.put("tableName", tableName);
        return this.sqlSession.selectOne("existTempTableSharding",params);
    }

    /**
     * 清空分析临时表数据
     * @param tableName 临时表名
     * @return
     */
    public int truncateTempTable( String tableName ){
        Map<String, Object> params = new HashedMap();
        params.put("tableName", tableName);
        return this.sqlSession.update("truncateTempTableSharding",params);
    }


    /**
     *  按天统计，返回当天指定条件个数
     * @param params key-value类型条件。key取值，‘statisticsTime’是必须参数
     * @return
     */
    public Long getUeInfoCountByDay( Map<String, Object> params  ){

        String dayDate = (String)params.get("statisticsTime");

        ShardingTabInfo  shardingTabInfo = shardingTabInfoMapper.selectByOneDay(dayDate);
        if( null == shardingTabInfo ) {
            return 0L;
        }

        params.put("tableName", shardingTabInfo.getTableName());

        Long count = this.sqlSession.selectOne("getUeInfoShardingCountByDay", params);

        return count;
    }
    
    public Long getUeInfoTotalCnt()
    {
    	Long totalCnt = new Long(0);
    	Long count = new Long(0);
    	Map<String, Object> params = new HashedMap();
    	
    	List<ShardingTabInfo> shardingTabInfoList = shardingTabInfoMapper.selectAll();
    	for(ShardingTabInfo shardingTabInfo: shardingTabInfoList)
    	{
    		 params.put("tableName", shardingTabInfo.getTableName());

    	     count = this.sqlSession.selectOne("getUeInfoTotalCntOfShardingTab", params);
    	     
    	     totalCnt = totalCnt + count;
    	}
 
    	return totalCnt;
    	
    }

    /**
     *  按小时统计，返回小时内指定条件个数
     * @param params key-value类型条件。key取值，‘statisticsTime’是必须参数
     * @return
     */
    public Long getUeInfoCountByHour( Map<String, Object> params  ){
        String dayDate = (String)params.get("statisticsTime");
        dayDate = dayDate.substring(0,10);
        ShardingTabInfo  shardingTabInfo = shardingTabInfoMapper.selectByOneDay(dayDate);
        if( null == shardingTabInfo ) {
            return 0L;
        }

        params.put("tableName", shardingTabInfo.getTableName());

        Long count = this.sqlSession.selectOne("getUeInfoShardingCountByHour", params);

        return count;
    }

    /**
     *  按分钟统计，返回分钟内指定条件个数
     * @param params key-value类型条件。key取值，‘statisticsTime’是必须参数
     * @return
     */
    public Long getUeInfoCountByMin(  Map<String, Object> params  ) {
        String dayDate = (String)params.get("statisticsTime");
        dayDate = dayDate.substring(0,10);
        ShardingTabInfo  shardingTabInfo = shardingTabInfoMapper.selectByOneDay(dayDate);
        if( null == shardingTabInfo ) {
            return 0L;
        }

        params.put("tableName", shardingTabInfo.getTableName());

        Long count = this.sqlSession.selectOne("getUeInfoShardingCountByMin", params);

        return count;
    }

    /**
     * 嫌疑人轨迹分析数据获取
     * @param startTime  开始分析时间
     * @param endTime   结束分析时间
     * @param imsi   目标IMSI
     * @return  嫌疑人轨迹站点数据列表
     */
    public List<Map<String, String>> getUeInfoBySuspect( String startTime, String endTime, String imsi, List<Site> filterSiteList ){

        List<Map<String, String>> targetSuspectTrailList = new LinkedList<>();

        //中间查询表插入数据
        String startDate = startTime.substring(0, 10) ;
        String endDate   = endTime.substring(0, 10);
        List<ShardingTabInfo> shardingTabInfoList =  shardingTabInfoMapper.selectByDate( startDate, endDate );

        for ( ShardingTabInfo shardingTabInfo: shardingTabInfoList ) {
            Map<String, Object> params  = new HashedMap();
            params.put("tableName", shardingTabInfo.getTableName());
            params.put("startTime", startTime);
            params.put("endTime", endTime);
            params.put("imsi", imsi);

            List<Map<String, String>> targetList = this.sqlSession.selectList("getUeInfoShardingBySuspect", params);

            targetList = this.filterTimeSpaceCondition(targetList, filterSiteList);
           
            targetSuspectTrailList.addAll(targetList);
        }

        return targetSuspectTrailList;
    }


    /**
     * 根据日期和站点，把每天的IMSI加入临时表
     * @param params 参数条件，包含日期信息
     */
    public void insertResidentPeopleInterTable( Map<String,Object> params ){

        String dayDate = (String)params.get("loopTime");
        ShardingTabInfo  shardingTabInfo = shardingTabInfoMapper.selectByOneDay(dayDate);
        if( null == shardingTabInfo ) {
            //无数据
            return;
        }

        params.put("dayTableName", shardingTabInfo.getTableName() );

        this.sqlSession.insert("insertResidentPeopleInterTableSharding", params);

    }

    /**
     * 获取当前常驻人口分析，总的人口数量
     * @param tableName 临时分析表名
     * @return 个数
     */
    public  Long getTotalCountResidentPeople(  String tableName ){
        Map<String,Object> params = new HashedMap();
        params.put("tableName", tableName);
        return this.sqlSession.selectOne("getTotalCountResidentPeopleSharding", params);
    }

    /**
     * 获取当前分析常驻人口数量
     * @param tableName 临时分析表名
     * @param totalDay  总分析天数
     * @return 个数
     */
    public Long getFixedCountResidentPeople( String tableName,   Long  totalDay  ){
        Map<String,Object> params = new HashedMap();
        params.put("tableName", tableName);
        params.put("totalDay", totalDay);
        return this.sqlSession.selectOne("getFixedCountResidentPeopleSharding", params);
    }

    /**
     * 非常驻人口数量
     * @param tableName 临时分析表名
     * @param totalDay 总分析天数
     * @return 个数
     */
    public Long getPeriodCountResidentPeople(  String tableName, Long  totalDay  ){
        Map<String,Object> params = new HashedMap();
        params.put("tableName", tableName);
        params.put("totalDay", totalDay);
        return this.sqlSession.selectOne("getPeriodCountResidentPeopleSharding", params);
    }

    /**
     * 获取常驻人口IMSI列表
     * @param tableName 临时分析表名
     * @param totalDay 总分析天数
     * @param start  开始条数
     * @param length  返回条数
     * @return IMSI列表
     */
    public List<Map<String, String>> getResidentPeopleFixedRateList(  String tableName,
                                        Long  totalDay ,  Integer start, Integer length ){
        Map<String,Object> params = new HashedMap();
        params.put("tableName", tableName);
        params.put("totalDay", totalDay);
        params.put("start", start);
        params.put("length", length);

        return this.sqlSession.selectList("getResidentPeopleFixedRateListSharding", params);
    }

    /**
     * 非常驻人口IMSI列表
     * @param tableName 临时分析表名
     * @param totalDay 总分析天数
     * @param start  开始条数
     * @param length 返回条数
     * @return  IMSI列表
     */
    public List<Map<String, String>> getResidentPeoplePeriodRateList( String tableName,
                                         Long  totalDay, Integer start, Integer length ){
        Map<String,Object> params = new HashedMap();
        params.put("tableName", tableName);
        params.put("totalDay", totalDay);
        params.put("start", start);
        params.put("length", length);
        return this.sqlSession.selectList("getResidentPeoplePeriodRateListSharding", params);
    }

    /**
     * 查询目标所过站点和时间
     * @param startTime  开始时间
     * @param endTime    结束时间
     * @param imsi   目标IMSI
     * @return  站点和时间集合的列表
     */
    public List<Map<String, String>> selectUeInfoAggrByMinForIMSIFollow(  String startTime,
                                                                String  endTime, String imsi, List<Site> filterSiteList ){

        List<Map<String, String>> targetList = new LinkedList<>();

        //中间查询表插入数据
        String startDate = startTime.substring(0, 10) ;
        String endDate   = endTime.substring(0, 10);
        List<ShardingTabInfo> shardingTabInfoList =  shardingTabInfoMapper.selectByDate( startDate, endDate );

        for ( ShardingTabInfo shardingTabInfo: shardingTabInfoList ) {

            Map<String, Object> params  = new HashedMap();
            params.put("tableName", shardingTabInfo.getTableName());
            params.put("startTime", startTime);
            params.put("endTime", endTime);
            params.put("imsi", imsi);

            List<Map<String, String>> subList = this.sqlSession.selectList("selectUeInfoAggrByMinForIMSIFollowSharding", params);
            
            subList = this.filterTimeSpaceCondition(subList, filterSiteList);
            targetList.addAll(subList);
        }

        return targetList;
    }
    
    private List<Map<String, String>> filterTimeSpaceCondition(List<Map<String, String>> subList, List<Site> filterSiteList)
    {
    	List<Map<String, String>> filteredsubList;
    	//int subList
    	
    	Iterator<Map<String, String>> it = subList.iterator();
    	while(it.hasNext())
    	{
	    	Map<String, String> x = it.next();
	    	    
	    	int i;
	    	for(i=0; i < filterSiteList.size(); i++)
	        {
	    		if (x.get("siteSN").equals(filterSiteList.get(i).getSn()))
	    		{
	    			break;
	    		}
	    		
	    	}
	    	
	    	if(i == filterSiteList.size())
    		{
    			it.remove();
    		}
    	}
    	
    	filteredsubList = subList;
    	return filteredsubList;
    }

    /**
     *  跟随分析临时数据创建实现
     * @param params 分析参数
     */
    public void insertIMSIFollowInterTable( Map<String, Object> params ) {

        String startDate = (String) params.get("startTime");
        startDate = startDate.substring(0,10);
        String endDate   = (String) params.get("endTime");
        endDate = endDate.substring(0,10);
        List<ShardingTabInfo> shardingTabInfoList =  shardingTabInfoMapper.selectByDate( startDate, endDate );
        if( null == shardingTabInfoList ) {
            //没有数据
            return;
        }

        for ( ShardingTabInfo shardingTabInfo : shardingTabInfoList ) {
            params.put("srcTableName", shardingTabInfo.getTableName());
            this.sqlSession.insert("insertIMSIFollowInterTableSharding", params);
        }

    }

    /**
     * 获取跟随目标的IMSI可能性列表，返回IMSI和百分比
     * @param tableName  分析临时表名称
     * @param targetUeInfoTotal  目标驻留点数量
     * @return
     */
    public  List<Map<String, String>> getIMSIFollowRateList( String tableName, Integer  targetUeInfoTotal ){
        Map<String, Object> params = new HashedMap();
        params.put("tableName", tableName);
        params.put("targetUeInfoTotal", targetUeInfoTotal);

        return this.sqlSession.selectList("getIMSIFollowRateListSharding",params );
    }


    /**
     * 创建碰撞分析临时表
     * @param tableName 临时表名
     * @return
     */
    public int createDateCollideTempTable( String tableName ){
        Map<String, Object> params = new HashedMap();
        params.put("tableName", tableName);
        return this.sqlSession.update("createDateCollideTempTableSharding",params);
    }

    /**
     * 碰撞分析中间临时数据生成
     * @param tableName  临时表表名
     * @param whereSql   查询条件
     * @param startTime  开始时间
     * @param endTime   结束时间
     */
    public void insertDataCollideInterTable( String tableName, String whereSql, String startTime, String endTime ) {
        String startDate = startTime.substring(0,10);
        String endDate = endTime.substring(0,10);
        List<ShardingTabInfo> shardingTabInfoList =  shardingTabInfoMapper.selectByDate( startDate, endDate );
        if( null == shardingTabInfoList ) {
            //没有数据
            return;
        }

        for ( ShardingTabInfo shardingTabInfo : shardingTabInfoList ) {
            Map<String, Object> params  = new HashedMap();
            params.put("tableName",tableName);
            params.put("whereSql",whereSql);
            params.put("srcTableName", shardingTabInfo.getTableName());
            this.sqlSession.insert("insertDataCollideInterTableSharding", params);
        }

    }

    /**
     * 碰撞分析并集UE信息收集
     * @param tableName  并集UE保存表
     * @param whereSql   查询条件
     * @param startTime 开始时间
     * @param endTime  结束时间
     */
    public void insertDataCollideSumTableSharding( String tableName, String whereSql, String startTime, String endTime  ) {
        String startDate = startTime.substring(0,10);
        String endDate = endTime.substring(0,10);
        List<ShardingTabInfo> shardingTabInfoList =  shardingTabInfoMapper.selectByDate( startDate, endDate );
        if( null == shardingTabInfoList ) {
            //没有数据
            return;
        }

        for ( ShardingTabInfo shardingTabInfo : shardingTabInfoList ) {
            Map<String, Object> params  = new HashedMap();
            params.put("tableName",tableName);
            params.put("whereSql",whereSql);
            params.put("srcTableName", shardingTabInfo.getTableName());
            this.sqlSession.insert("insertDataCollideSumTableSharding", params);
        }
    }

    /**
     * 碰撞分析交集数据获取
     * @param sumUeIfnoTable  并集UE信息保存表
     * @param tableName  交集IMSI信息表
     * @return UE信息list
     */
    public List<UeInfo> getDataCollideUeInfoList( String sumUeIfnoTable,  String tableName ) {
        Map<String, Object> params = new HashedMap();
        params.put("sumUeIfnoTable", sumUeIfnoTable);
        params.put("tableName", tableName);
        return this.sqlSession.selectList("getShardingDataCollideUeInfoList", params);
    }

}
