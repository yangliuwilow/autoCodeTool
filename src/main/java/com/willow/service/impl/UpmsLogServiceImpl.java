package com.willow.service.impl;


import com.willow.dao.UpmsLogMapper;
import com.willow.entity.UpmsLog;
import com.willow.service.UpmsLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* UpmsLogService实现
* Created by willow on 2018/1/30.
*/
@Service(value = "upmsLogService")
@Transactional
public class UpmsLogServiceImpl  implements UpmsLogService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UpmsLogServiceImpl.class);

    @Autowired
    private UpmsLogMapper upmsLogMapper;

    public List<UpmsLog> selectList(UpmsLog upmsLog){
          return  upmsLogMapper.selectList(upmsLog);
    }

    public  UpmsLog selectById (Long id){
          return  upmsLogMapper.selectById(id);
    }


    public Integer saveUpmsLog (UpmsLog upmsLog){
         return  upmsLogMapper.saveUpmsLog(upmsLog);
    }

    public Integer updateUpmsLog (UpmsLog upmsLog){
        return    upmsLogMapper.updateUpmsLog(upmsLog);
    }

    public Long deleteUpmsLog (Long id){
        return  upmsLogMapper.deleteUpmsLog(id);
    }

}