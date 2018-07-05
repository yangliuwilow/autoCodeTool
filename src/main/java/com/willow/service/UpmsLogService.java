package com.willow.service;


import com.willow.entity.UpmsLog;
import java.util.List;

/**
* UpmsLogService接口
* Created by willow on 2018/1/30.
*/
public interface UpmsLogService {

        /**
         * 查询
         * @param upmsLog
         * @return  List<UpmsLog>
         */
        public List<UpmsLog> selectList(UpmsLog upmsLog);
        /**
         * 根据ID查询
         * @param id
         * @return  UpmsLog
         */
        public UpmsLog  selectById(Long id);
        /**
         * 保存
         * @param upmsLog
         * @return UpmsLog
         */
        public Integer saveUpmsLog(UpmsLog upmsLog);
        /**
        * 根据ID修改
        * @param upmsLog
        * @return UpmsLog
        */
        public Integer updateUpmsLog(UpmsLog upmsLog);
        /**
         * 根据ID删除
         * @param id
         * @return Long
         */
        public Long deleteUpmsLog(Long id);
}