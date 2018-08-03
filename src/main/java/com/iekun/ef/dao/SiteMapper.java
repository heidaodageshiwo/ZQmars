package com.iekun.ef.dao;

import com.iekun.ef.model.Site;
import com.iekun.ef.model.SiteExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface SiteMapper {
    int countByExample(SiteExample example);

    int deleteByExample(SiteExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Site record);

    int insertSelective(Site record);

    List<Site> selectByExample(SiteExample example);

    Site selectByPrimaryKey(Long id);
    
    Site selectBySiteSn(String sn);

    int updateByExampleSelective(@Param("record") Site record, @Param("example") SiteExample example);

    int updateByExample(@Param("record") Site record, @Param("example") SiteExample example);

    int updateByPrimaryKeySelective(Site record);

    int updateByPrimaryKey(Site record);

	List<Site> selectSiteList();
	
	List<Site> selectSiteListByUserId(Long userId);
	
	void update(Site site);

	Site getSiteDetails(String deviceSn);

    List<Site> selectSitesByAreaId( Map<String, Object> params );

	List<Site> selectAllSiteList();

    void deleteSiteUserMapping(Long siteId);

}