package com.iekun.ef.dao;

import com.iekun.ef.controller.SigletonBean;
import com.iekun.ef.model.ShardingQueryInfo;
import com.iekun.ef.model.ShardingTabInfo;
import com.iekun.ef.model.Site;
import com.iekun.ef.model.UeInfo;
import com.iekun.ef.util.TimeUtils;
import com.iekun.ef.util.Utils;

import org.apache.commons.collections.map.HashedMap;
import org.apache.ibatis.session.SqlSession;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.stereotype.Component;
import sun.security.provider.MD5;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by sword.yu on 2017/1/16.
 */


@Component
public class UeInfoShardingDao {

    private final static Logger logger = LoggerFactory.getLogger(UeInfoShardingDao.class);

    private final SqlSession sqlSession;

    @Autowired
    private ShardingTabInfoMapper shardingTabInfoMapper;
    
    @Autowired
    private SiteMapper siteMapper;

    @Autowired
    private ShardingQueryInfoMapper shardingQueryInfoMapper;

    public UeInfoShardingDao(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    /**
     *  初始化UEInfo分表DAO方法
     */
    public void initUeInfoDao() {

        shardingQueryInfoMapper.createQueryTab();
        Date nowDay = new Date();
        String nowDayText = TimeUtils.dayFormatter.format( nowDay );
        Calendar cal = Calendar.getInstance();
        cal.setTime( nowDay );
        cal.add(Calendar.DATE, -1);
        Date yesterday = cal.getTime();
        String yesterdayText = TimeUtils.dayFormatter.format( yesterday );
        //今天数据表
        this.createUeInfoTab(nowDayText);
        //昨天数据表
        this.createUeInfoTab(yesterdayText);

    }

    /**
     * 根据日期创建分表,如果存在，就不创建
     * @param day 日期时间
     * @return 0-成功，其他异常
     */
    public int createUeInfoTab( String day ){

        if( isValidDate(day) ) {
            try{

                String tableName = "t_ue_info_" + day.replace("-", "_");
                ShardingTabInfo shardingTabInfo = new ShardingTabInfo( tableName, day);
                Map map = new HashMap();
                map.put("tableName", tableName );
                this.sqlSession.update("createUeInfoTab", map );
                shardingTabInfoMapper.insertTabInfo(shardingTabInfo);

            } catch ( DuplicateKeyException e ) {
                logger.info("表存在情况下，insert分表信息忽略异常");
            } catch ( Exception e ) {
                e.printStackTrace();
                return -1;
            }


        }

        return 0;
    }

    /**
     *  根据日期，批量插入数据到分表中
     * @param ueInfos ueinfo数据列表
     * @param day   日期时间
     * @return  0-成功，其他异常
     */
    public int batchInsertUeInfo( List<UeInfo> ueInfos, String day ){


        String tableName = "t_ue_info_" + day.replace("-", "_");

        if( isValidDate(day) ) {

            try{

                Map map = new HashMap();
                map.put("tableName", tableName );
                map.put("ueInfos", ueInfos );
                this.sqlSession.insert("batchInsertShardingUeInfo", map);

            } catch ( BadSqlGrammarException e ) {
                Throwable exceptionCause =  e.getCause();
                String errDetailMessage = exceptionCause.getMessage();
                if( errDetailMessage.equals("Table 'efence2." + tableName +  "' doesn't exist")){
                    logger.info(  errDetailMessage );
                    logger.info("表不存在，不能插入输入，忽略本异常");
                } else {
                    e.printStackTrace();
                }
            }

        }

        return 0;
    }


    /**
     *  获取数据库count值,如果参数为空值，表示不进行判断
     * @param startTime   捕获时间上限
     * @param endTime     捕获时间下限
     * @param operator    运营商
     * @param homeOwnership    归属地
     * @param siteSN      站点序号
     * @param deviceSN    设备序号
     * @param imsi         IMSI值
     * @return 总ueinfo数
     */
    public Long getCount( String startTime, String endTime, String operator, String homeOwnership,  String siteSN, String deviceSN, String imsi, long userId ){

        Long ueCount = 0L;
        String siteListStr = this.getSiteStrCurrentUser();
       /* String tableUpdateMd5Val = this.getMd5ValOfTableUpdateAccordingTimeSpan(startTime, endTime);*/
        String tableUpdateMd5Val = this.calcTotalWithinAllTable(startTime, endTime);
        //long lastTableTotalCnt = this.getTotalCntWithinLastTable(endTime);
        String queryCondition = startTime + endTime + operator + homeOwnership + siteSN + deviceSN + imsi + tableUpdateMd5Val + siteListStr /*+ lastTableTotalCnt*/;
       
        String queryConditionMd5 =  calcQueryMd5(queryCondition);

        ShardingQueryInfo shardingQueryInfo =  shardingQueryInfoMapper.selectByQueryInfo(queryConditionMd5);
        if( null == shardingQueryInfo ) {

            shardingQueryInfo = new ShardingQueryInfo();
            String currentTime = TimeUtils.timeFormatterStr.format(new Date());
            String tmpTabName = "tmpqry_" +  queryConditionMd5 + "_" + currentTime;
            shardingQueryInfo.setQueryCondition( queryConditionMd5 );
            shardingQueryInfo.setShardingTmpName( tmpTabName );
            shardingQueryInfo.setCreateTime( TimeUtils.timeFormatter.format(new Date()) );

            this.creatTempQueryTab(shardingQueryInfo, startTime, endTime,
                    operator, homeOwnership,  siteSN, deviceSN, imsi,userId);

        }

        String  tableName = shardingQueryInfo.getShardingTmpName();
        Map map = new HashMap();
        map.put("tableName", tableName );
        ueCount = getShardingUeInfoCount(map);

        return ueCount;
    }
    
    private long getTotalCntWithinLastTable(String endTime)
    {
    	String endDate   = endTime.isEmpty()?"": endTime.substring(0, 10);
    	String endDateCmp = endDate.replace("-","");
    	String table_name = "t_ue_info_";
    	/* 判断查询的最后时间是否大于当前时间，如果大于，则以当天时间的数据表记录为准，否则则是输入的最后时间为准 */
    	String noDateStrCmp =  TimeUtils.daySimpleFormatter.format( new Date());
    	
    	if((endDateCmp.compareTo(noDateStrCmp) > 0))
    	{
    		String noDateStr = TimeUtils.dayFormatter.format( new Date());
    		table_name = table_name + noDateStr.replace("-","_");
    	}
    	else
    	{
    		table_name = table_name + endDate.replace("-","_");
    	}
    	
    	Map map = new HashMap();
        map.put("tableName", table_name );
        
    	long totalCnt = this.getShardingUeInfoCount(map);
    	
    	return totalCnt;
    }
    
    private String getSiteStrCurrentUser()
    {
    	String listSiteStr = null;
		Long userId = SigletonBean.getUserId();
	    List<Site> sites= siteMapper.selectSiteListByUserId(userId);
	    if (sites.size() != 0)
	    {
	    	 listSiteStr =  Utils.getSitelistStr(sites);
	    }
	    
	    return listSiteStr;
    }

    /*public String getMd5ValOfTableUpdateAccordingTimeSpan(String startTime, String endTime)*/
    public String calcTotalWithinAllTable(String startTime, String endTime)
    {
    	String md5Val = null;
    	String sql 	  = null; 
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd"); 
    	SimpleDateFormat timeSdf = new SimpleDateFormat("yyyyMMddHHmmss");
    	String timeStr = "timeSpan";
    	String updateTimeIntem = null;
    	long totalCnt = 0;
    	
    	/*List<Date> listDate = this.getDateList(startTime, endTime);*/
    	/*for(Date date:listDate){    
		  /*	String table_name = "t_ue_info_" + sdf.format(date);*/
		  	/*Map<String, Object> params  = new HashedMap();
            params.put("tableName", table_name);
           
    		List<Map<String, String>> TabUpdateTimeList = this.sqlSession.selectList("getTableUpdateTime", params);
    		for ( Map<String, String> TabUpdateTime : TabUpdateTimeList ) {
    			
    			updateTimeIntem = timeSdf.format(TabUpdateTime.get("UPDATE_TIME"));
    			
    		}
    		
    		timeStr = timeStr + updateTimeIntem;*/
    	    String startDate = startTime.isEmpty()?"": startTime.substring(0, 10) ;
            String endDate   = endTime.isEmpty()?"": endTime.substring(0, 10);
    	    List<ShardingTabInfo> shardingTabInfoList =  shardingTabInfoMapper.selectByDate( startDate, endDate );
    	    for ( ShardingTabInfo shardingTabInfo: shardingTabInfoList ) {
            Map map = new HashMap();
            map.put("tableName", shardingTabInfo.getTableName() );
            
        	totalCnt = totalCnt + this.getShardingUeInfoCount(map);
        	
		   } 
    	
    	/*md5Val =  calcQueryMd5(timeStr);*/
    	md5Val =  calcQueryMd5(String.valueOf(totalCnt));//calcQueryMd5(totalCnt.);
    	
    	return md5Val;
    	
    }
    
    /**
     * 根据开始时间和结束时间返回时间段内的时间集合
     * @param beginDate
     * @param endDate
     * @return List
     */
    public  List getDatesBetweenTwoDate(Date beginDate, Date endDate) {    
        List lDate = new ArrayList();
        lDate.add(beginDate);//把开始时间加入集合
        Calendar cal = Calendar.getInstance();
        //使用给定的 Date 设置此 Calendar 的时间
        cal.setTime(beginDate);
        boolean bContinue = true;
        while (bContinue) {
            //根据日历的规则，为给定的日历字段添加或减去指定的时间量
            cal.add(Calendar.DAY_OF_MONTH, 1);
            // 测试此日期是否在指定日期之后
            if (endDate.after(cal.getTime())) {
                lDate.add(cal.getTime());
            } else {
                break;
            }
        }
        lDate.add(endDate);//把结束时间加入集合
        return lDate;
    }
    
    
    public  List<Date> getDateList(String start, String end) {
    	
        Calendar cal = Calendar.getInstance();  
        List<Date> listDate = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
        
        try {
			Date dBegin =sdf.parse(start); 
			Date dEnd = sdf.parse(end); 
			listDate = getDatesBetweenTwoDate(dBegin, dEnd);    
			for(Date date:listDate){    
			    System.out.println(sdf.format(date));    
			}    
			return listDate;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return listDate;
   }
    /**
     *  根据条件获取UEInfo列表，如果参数为空值，表示不进行判断
     * @param startTime  捕获时间上限
     * @param endTime    捕获时间下限
     * @param operator   运营商
     * @param homeOwnership   归属地
     * @param siteSN     站点序号
     * @param deviceSN   设备序号
     * @param imsi        IMSI值
     * @return  UEInfo数据列表
     */
   /* public List<UeInfo> getUeInfo( String startTime, String endTime, String operator, String homeOwnership,  String siteSN, String deviceSN, String imsi, Integer start, Integer length,long userId ){

    	String siteListStr = this.getSiteStrCurrentUser();
    	*//*String tableUpdateMd5Val = this.getMd5ValOfTableUpdateAccordingTimeSpan(startTime, endTime);*//*
    	String tableUpdateMd5Val = this.calcTotalWithinAllTable(startTime, endTime);
        String queryCondition = startTime + endTime + operator + homeOwnership + siteSN + deviceSN + imsi + tableUpdateMd5Val + siteListStr;
        String queryConditionMd5 =  calcQueryMd5(queryCondition);

        ShardingQueryInfo shardingQueryInfo =  shardingQueryInfoMapper.selectByQueryInfo(queryConditionMd5);
        if( null == shardingQueryInfo ) {

            shardingQueryInfo = new ShardingQueryInfo();
            String currentTime = TimeUtils.timeFormatterStr.format(new Date());
            String tmpTabName = "tmpqry_" + queryConditionMd5 + "_" + currentTime;
            shardingQueryInfo.setQueryCondition( queryConditionMd5 );
            shardingQueryInfo.setShardingTmpName( tmpTabName );
            shardingQueryInfo.setCreateTime( TimeUtils.timeFormatter.format(new Date()) );

            this.creatTempQueryTab(shardingQueryInfo, startTime, endTime,
                    operator, homeOwnership,  siteSN, deviceSN, imsi,userId  );

        }

        String  tableName = shardingQueryInfo.getShardingTmpName();
        Map map = new HashMap();
        map.put("tableName", tableName );
        map.put("start", start );
        map.put("length", length );

        return getShardingUeInfoList(map);

    }*/
   /* public List<UeInfo> getUeInfo( String startTime, String endTime, String operator, String homeOwnership,  String siteSN, String deviceSN, String imsi,long userId ){

        String siteListStr = this.getSiteStrCurrentUser();
    	*//*String tableUpdateMd5Val = this.getMd5ValOfTableUpdateAccordingTimeSpan(startTime, endTime);*//*
        String tableUpdateMd5Val = this.calcTotalWithinAllTable(startTime, endTime);
        String queryCondition = startTime + endTime + operator + homeOwnership + siteSN + deviceSN + imsi + tableUpdateMd5Val + siteListStr;
        String queryConditionMd5 =  calcQueryMd5(queryCondition);

        ShardingQueryInfo shardingQueryInfo =  shardingQueryInfoMapper.selectByQueryInfo(queryConditionMd5);
        if( null == shardingQueryInfo ) {

            shardingQueryInfo = new ShardingQueryInfo();
            String currentTime = TimeUtils.timeFormatterStr.format(new Date());
            String tmpTabName = "tmpqry_" + queryConditionMd5 + "_" + currentTime;
            shardingQueryInfo.setQueryCondition( queryConditionMd5 );
            shardingQueryInfo.setShardingTmpName( tmpTabName );
            shardingQueryInfo.setCreateTime( TimeUtils.timeFormatter.format(new Date()) );

            this.creatTempQueryTab(shardingQueryInfo, startTime, endTime,
                    operator, homeOwnership,  siteSN, deviceSN, imsi,userId  );

        }

        String  tableName = shardingQueryInfo.getShardingTmpName();
        Map map = new HashMap();
        map.put("tableName", tableName );
       *//* map.put("start", start );
        map.put("length", length );*//*

        return getShardingUeInfoList(map);

    }*/
    public Map getUeInfo( String startTime, String endTime, String operator, String homeOwnership,  String siteSN, String deviceSN, String imsi,long userId ){

        String siteListStr = this.getSiteStrCurrentUser();
    	/*String tableUpdateMd5Val = this.getMd5ValOfTableUpdateAccordingTimeSpan(startTime, endTime);*/
        String tableUpdateMd5Val = this.calcTotalWithinAllTable(startTime, endTime);
        String queryCondition = startTime + endTime + operator + homeOwnership + siteSN + deviceSN + imsi + tableUpdateMd5Val + siteListStr;
        String queryConditionMd5 =  calcQueryMd5(queryCondition);

        ShardingQueryInfo shardingQueryInfo =  shardingQueryInfoMapper.selectByQueryInfo(queryConditionMd5);
        if( null == shardingQueryInfo ) {

            shardingQueryInfo = new ShardingQueryInfo();
            String currentTime = TimeUtils.timeFormatterStr.format(new Date());
            String tmpTabName = "tmpqry_" + queryConditionMd5 + "_" + currentTime;
            shardingQueryInfo.setQueryCondition( queryConditionMd5 );
            shardingQueryInfo.setShardingTmpName( tmpTabName );
            shardingQueryInfo.setCreateTime( TimeUtils.timeFormatter.format(new Date()) );

            this.creatTempQueryTab(shardingQueryInfo, startTime, endTime,
                    operator, homeOwnership,  siteSN, deviceSN, imsi,userId  );

        }

        String  tableName = shardingQueryInfo.getShardingTmpName();
        Map map = new HashMap();
        map.put("tableName", tableName );
       /* map.put("start", start );
        map.put("length", length );*/

        //return getShardingUeInfoList(map);
    return map;
    }






    /**
     * 校验日期是否正确  , 日期格式为“2017-01-01”
     * @param dateText
     * @return  true - 正确, false - 错误
     */
    private boolean isValidDate( String dateText )
    {
        String datePattern1 = "\\d{4}-\\d{2}-\\d{2}";
        String datePattern2 = "^((\\d{2}(([02468][048])|([13579][26]))"
                + "[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|"
                + "(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?"
                + "((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?("
                + "(((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?"
                + "((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";

        if (( dateText != null)) {
            Pattern pattern = Pattern.compile(datePattern1);
            Matcher match = pattern.matcher(dateText);
            if (match.matches()) {
                pattern = Pattern.compile(datePattern2);
                match = pattern.matcher(dateText);
                return match.matches();
            }
            else {
                return false;
            }
        }

        return false;

    }

    //生成md5值
    private String calcQueryMd5( String queryCondition ) {
       String queryMd5 = new SimpleHash("md5", queryCondition).toHex();
       return queryMd5;
    }

    //获取ue个数
    private Long  getShardingUeInfoCount( Map map ) {
        Long count = this.sqlSession.selectOne("getShardingUeInfoCnt", map);
        return count;
    }

/*    //获取UE列表
    private List<UeInfo> getShardingUeInfoList( Map map ) {
        return this.sqlSession.selectList("selectShardingUeInfoList", map);
    }*/
//获取UE列表
public List<UeInfo> getShardingUeInfoList( Map map ) {
    return this.sqlSession.selectList("selectShardingUeInfoList1", map);
}
    //创建临时查询表
    private void creatTempQueryTab(  ShardingQueryInfo shardingQueryInfo, String startTime, String endTime, String operator,
                                    String homeOwnership,  String siteSN, String deviceSN, String imsi,long userId  ) {

        //创建临时查询中间表
        Map createUeInfoTmpParams = new HashMap();
        createUeInfoTmpParams.put("tableName", shardingQueryInfo.getShardingTmpName() );
        this.sqlSession.update("createUeInfoTab", createUeInfoTmpParams );
        //记录临时查询中间表信息
        shardingQueryInfoMapper.insertQueryInfo(shardingQueryInfo);

        //中间查询表插入数据
        String startDate = startTime.isEmpty()?"": startTime.substring(0, 10) ;
        String endDate   = endTime.isEmpty()?"": endTime.substring(0, 10);
        List<ShardingTabInfo> shardingTabInfoList =  shardingTabInfoMapper.selectByDate( startDate, endDate );

        Map queryParams  = new HashedMap();
        queryParams.put("tempTableName", shardingQueryInfo.getShardingTmpName());
        queryParams.put("startTime", startTime);
        queryParams.put("endTime", endTime);
        queryParams.put("operator", operator);
        queryParams.put("homeOwnership", homeOwnership);
        queryParams.put("deviceSn", deviceSN);
        queryParams.put("siteSn", siteSN);
        queryParams.put("imsi", imsi);
        queryParams.put("userId", userId);

        for ( ShardingTabInfo shardingTabInfo: shardingTabInfoList ) {
            queryParams.put("shardingTabName", shardingTabInfo.getTableName());
            this.sqlSession.insert("selectShardingAndInsertTempTab", queryParams);
        }

    }
}
