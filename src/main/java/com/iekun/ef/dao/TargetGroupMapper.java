package com.iekun.ef.dao;

import com.iekun.ef.model.*;
import org.apache.activemq.util.LongSequenceGenerator;

import java.lang.annotation.Target;
import java.util.List;
import java.util.Map;

public interface TargetGroupMapper {

    /**
     * 通过站点ID查询对应的站点信息
     * */
    List<Site> querySitesBySiteId(Long siteId);

    /**
     * 通过用户ID查询对应的站点
     * */
    List<Site> querySiteByUserId(long userId);

     /**
      * 查询分组信息
      * */
     List<TargetGroup> queryAllGroup(long userId);

     /**
      * 查询每个分组人数
      * */
     int queryGroupCount(String groupId);

     /**
      * 查询各个分组的黑名单人员信息
      * */
     List<TargetRule> queryBlacklist(String groupId);

     /**
      * 删除分组中单个黑名单人员信息
      * */
     int  delGroupTarget(TargetRule targetRule);

     /**
      * 通过用户ID查询对应的站点信息
      * */
     List<Site> queryAllSiteByUserId(long userId);

     /**
      * 获取创建人姓名
      * */

     String queryCreatorName(long creatorId);

     /**
      * 导入Excel
      * */
     int addExcelTargetPerson(TargetRule targetRule);

     /**
      * 新建分组
      * */
     long addGroup(TargetGroup targetGroup);

     /**
      * 新建分组的ID和对象的站点
      * */
     void insertSiteAndGroup(Map map);

     /**
      * 删除分组
      * */
     void delGrouplist(Map map);

     /**
      * 删除分组中的人员信息
      * */
     void delGroupPerson(Map map);

     /**
      * 删除分组和站点的映射关系
      * */

     void delGroupSite(Map map);
     /**
      * 通过站点ID查询站点名称
      * */
     String querySiteBySiteId(long siteId);

     /**
      * 补录黑名单人员
      **/
     void addOneTarget(TargetRule record);

     List<TargetRule> selectCountByGroupIdAndImsi(Map<String, String> params);

     /**
      * 通过站点名字查询站点ID
      * */
    long querySiteIdBySiteName(String siteName);

    /**
     * 更新分组信息
     * */
    void  updateGroup(TargetGroup targetGroup);

     /**
      * 删除分组站点映射关系
      */
    void  delSiteAndGroup(Map map);

    /**
     * 查看分组名字是否已经存在
     * */
    TargetGroup queryGroupByName(String groupName);

    /**
     * 查询此分组下所有的IMSI号码
     * */
    List<String> queryAllImsiByGroupId(String groupId);

    /**
     * 批量添加黑名单人员
     * */
    void insertExcel(List<TargetRule> targetRuleList);

    /**
     * 通过站点ID查询对象站点名字
     * */
    String querySiteNameById(Long siteId);

    /**
     * 更新时删除分组站点映射关系
     * */
    void delGroupSiteMappingByGroupId(String groupIdUp);

    /**
     * 通过分组ID查询对应的站点ID
     * */
    List<Long> querySiteIdsByGroupId(String groupId);
}
