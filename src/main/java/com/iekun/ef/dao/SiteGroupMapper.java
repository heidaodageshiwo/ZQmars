package com.iekun.ef.dao;

import com.iekun.ef.model.Site;
import com.iekun.ef.model.SiteUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by java1 on 2017/11/28 0028.
 */
public interface SiteGroupMapper {

    /**
     * 通过用户ID查询对应的站点
     * */
    List<Site> querySiteByUserId(long userId);
    List<SiteUser> querysiteUserList(long userId);


   //void insertData(@Param("siteid") String siteid,@Param("userid") String userid);

    //void deleteData(@Param("siteid") String siteid,@Param("userid") String userid);

    void deleteData(Map<String, Object> map);

    void insertData(List<SiteUser> sitelist);
}
