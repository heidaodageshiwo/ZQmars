package com.iekun.ef.dao;

import com.iekun.ef.model.*;

import java.util.List;
import java.util.Map;

public interface OwnershipSiteMapping {

    /**
     * 通过用户ID查询对应的站点信息
     * */
    List<Site> queryAllSiteByUserId(long userId);

    /**
     * 通过id查询对应的站点信息
     * */
    String querySiteName(long siteId);

    /**
     *将站点ID和分组ID存入映射
     * */
    void insertWarningSite(SiteWarningMapping sm);

    /**
     *将区域ID和分组ID存入映射
     * */
    int insertWarningArea(SiteAreaMapping siteAreaMapping);

    /**
     * 添加分组
     * */
    void insertGroup(OwnershipSite os);

    /**
     * 查询所有分组
     * */
    List<OwnershipSite> queryAllGroup(long creatorId);

    /**
     * 删除分组
     * */
    void delGrouplist(Map map);


    List<SelfAreaCode> queryOwnershipNameById(long provinceId,long cityId);

    /**
     * 删除分组站点映射关系
     * */
    void delSiteMapping(Map map);

    /**
     * 删除分组区域映射关系
     * */
    void delAreaMapping(Map map);

    /**
     * 更新分组
     * */
    void updateGroup(OwnershipSite os);

    /**
     * 更新时删除分组站点映射
     * */
    void delupSiteMapping(String groupId);

    /**
     * 更新时删除分组归属地映射
     * */
    void delupAreaMapping(String groupId);

    /**
     * 通过分组ID查询站点ID
     */
    List<Map<String,Long>> querySiteIdByGroupId(String groupId);

    /**
     * 通过分组ID查询预警归属地ID
     */
    List<Map<String,String>> queryOwnershipIdByGroupId(String groupId);

}
