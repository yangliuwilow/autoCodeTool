package com.willow.controller;

import com.auto.tool.volocity.main.VelocityMain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * AutoCreateCodeController
 * Created by willow on 2018/1/29.
 */
@Controller
@RequestMapping
public class AutoCreateCodeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AutoCreateCodeController.class);
        /**
          * 显示列表
          */
        @RequestMapping
        public String list(Model model){
            return "upmsLog/upmsLog_list";
        }

        /**
         * 请求新增页面
         */
        @ResponseBody
        @RequestMapping(value="/create")
        public String create(Model model){
            try {
                VelocityMain.create();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "ok";
        }

    /**
     * 请求新增页面
     */
    @ResponseBody
    @RequestMapping(value="/create/{tablename}")
    public String create(Model model,@PathVariable String tablename){
        try {
            VelocityMain.create(tablename);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "ok";
    }


}