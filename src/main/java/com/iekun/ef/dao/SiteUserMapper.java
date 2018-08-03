package com.iekun.ef.dao;

import com.iekun.ef.model.SiteUser;
import com.iekun.ef.model.SiteUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SiteUserMapper {
    int countByExample(SiteUserExample example);

    int deleteByExample(SiteUserExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SiteUser record);

    int insertSelective(SiteUser record);

    List<SiteUser> selectByExample(SiteUserExample example);

    SiteUser selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SiteUser record, @Param("example") SiteUserExample example);

    int updateByExample(@Param("record") SiteUser record, @Param("example") SiteUserExample example);

    int updateByPrimaryKeySelective(SiteUser record);

    int updateByPrimaryKey(SiteUser record);
    
    List<SiteUser>  selectSUListBySIdAndUId(SiteUser record);

	void update(SiteUser siteUser);
    
    
    
}