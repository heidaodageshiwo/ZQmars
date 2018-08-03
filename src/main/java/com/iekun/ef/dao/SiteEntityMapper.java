package com.iekun.ef.dao;

import com.iekun.ef.model.SiteEntity;

import java.util.List;

public interface SiteEntityMapper {
    List<SiteEntity> getSiteEntityList(Long userId);
}
