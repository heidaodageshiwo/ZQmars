package com.iekun.ef.dao;

import com.iekun.ef.model.Whitelist;

import java.util.List;
import java.util.Map;

public interface WhitelistMapper {

    /**
     * 查询所有白名单
     * */
    List<Whitelist> queryAll(long userId);

    /**
     * 判断要添加的IMSI号是否已经存在
     * */
    Whitelist queryWhitelistByImsi(String imsi);

    /**
     * 添加白名单
     * */
    void addWhitelist(Whitelist whitelist);

    /**
     * 修改白名单
     * */
    void updateWhitelist(Whitelist whitelist);

    /**
     * 删除白名单
     * */
    void delWhitelist(Map map);

    /**
     * 导入Excel
     * */
    void insertExcel(List<Whitelist> whitelists);

    /**
     * 查询所有IMSI号码
     * */
    List<String> queryAllImsiByUserId(long userId);
}
