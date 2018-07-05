package com.willow.controller;

import com.willow.entity.UpmsLog;
import com.willow.service.UpmsLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * UpmsLogController
 * Created by willow on 2018/1/30.
 */
@Controller
@RequestMapping("/upmsLog")
public class UpmsLogController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UpmsLogController.class);

    @Autowired
    private UpmsLogService upmsLogService;

        /**
          * 显示列表
          */
        @RequestMapping
        public String list(Model model,UpmsLog upmsLog){
        List<UpmsLog> upmsLogs = upmsLogService.selectList(upmsLog);
            model.addAttribute("upmsLogs", upmsLogs);
            model.addAttribute("upmsLog", upmsLog);
            return "upmsLog/upmsLog_list";
        }

        /**
         * 请求新增页面
         */
        @RequestMapping(value="/new")
        public String toAdd(Model model){
            return "upmsLog/upmsLog_edit";
        }



        /**
        * 请求编辑页面
        */
        @RequestMapping(value="/toEdit/{id}")
        public String toEdit(Model model,@PathVariable Long id){
            UpmsLog upmsLog = upmsLogService.selectById(id);
            model.addAttribute("upmsLog", upmsLog);
            upmsLog.setBasePath("abc");
           // upmsLog.setLogId(null);
            toEdit(model, upmsLog);
            return "upmsLog/upmsLog_edit";
        }

        /**
        * 保存页面
        */
        @RequestMapping(value="/save")
         public String toEdit(Model model,UpmsLog upmsLog){
            if(upmsLog.getLogId()==null){    //save
                upmsLogService.saveUpmsLog(upmsLog);
                return "redirect:/upmsLog";
            } else{
                upmsLogService.updateUpmsLog(upmsLog);
               return "redirect:/upmsLog";
            }
        }





}