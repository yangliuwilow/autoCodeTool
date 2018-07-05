package com.willow.dao;


import com.willow.entity.UpmsLog;

import java.util.List;

public interface UpmsLogMapper {

    public List<UpmsLog> selectList(UpmsLog upmsLog);

    public  UpmsLog selectById (Long id);

    public Integer saveUpmsLog (UpmsLog upmsLog);

    public Integer updateUpmsLog (UpmsLog upmsLog);

    public Long deleteUpmsLog (Long id);
}