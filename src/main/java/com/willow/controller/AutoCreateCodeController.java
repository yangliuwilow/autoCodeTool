package com.willow.controller;

import com.auto.tool.volocity.utils.CreateMyBatisUtil;
import com.auto.tool.volocity.utils.JdbcUtil;
import com.willow.entity.DBTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * AutoCreateCodeController
 * Created by willow on 2018/1/29.
 */
@Controller
public class AutoCreateCodeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AutoCreateCodeController.class);


    /**
     * 请求新增页面
     */
    @RequestMapping
    public String index(Model model) {

        return "index";
    }

    /**
     * 显示列表
     */
    @RequestMapping(value = "/list")
    public String list(Model model, String tableName, String jdbc_driver, String jdbc_url, String jdbc_username, String jdbc_password, String database) {
        try {
            String sql = "SELECT table_name ,table_comment ,table_schema,create_time,engine  FROM INFORMATION_SCHEMA.TABLES WHERE table_schema = '" + database + "' ";
           /* if(StringUtils.isNotEmpty(tablePrefix))
                sql+=" AND table_name LIKE '\" + tablePrefix + \"%'";*/
            JdbcUtil jdbcUtil = new JdbcUtil(jdbc_driver, jdbc_url, jdbc_username, jdbc_password);
            List<Map> result = jdbcUtil.selectByParams(sql, null);
            List<DBTable> dbTables=new ArrayList<>();
            DBTable dbTable=null;
            for(Map map:result){
                dbTable=new DBTable();
                dbTable.setEngine(map.get("ENGINE")!=null?map.get("ENGINE").toString():"");
                dbTable.setTable_comment(map.get("TABLE_COMMENT")!=null?map.get("TABLE_COMMENT").toString():"");
                dbTable.setTable_name(map.get("TABLE_NAME")!=null?map.get("TABLE_NAME").toString():"");
                dbTable.setTable_schema(map.get("TABLE_SCHEMA")!=null?map.get("TABLE_SCHEMA").toString():"");
                dbTable.setCreate_time(map.get("CREATE_TIME")!=null?map.get("CREATE_TIME").toString():"");
                dbTables.add(dbTable);
            }
            model.addAttribute("tableLists", dbTables);
            model.addAttribute("jdbc_driver", jdbc_driver);
            model.addAttribute("jdbc_url", jdbc_url);
            model.addAttribute("jdbc_username", jdbc_username);
            model.addAttribute("jdbc_password", jdbc_password);
            model.addAttribute("database", database);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "list";
    }

    /**
     * 请求新增页面
     */
    @ResponseBody
    @RequestMapping(value = "/create")
    public String create(Model model) {
        try {
            CreateMyBatisUtil.create();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "ok";
    }

    /**
     * 请求新增页面
     */
    @ResponseBody
    @RequestMapping(value = "/create/{tablename}")
    public String create(Model model, @PathVariable String tablename) {
        try {
            CreateMyBatisUtil.create(tablename);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "ok";
    }


    /**
     * 指定tableName生成
     */
    @ResponseBody
    @RequestMapping(value = "/createByReq")
    public String createByReq(Model model, String tableName, String jdbc_driver, String jdbc_url, String jdbc_username, String jdbc_password, String database, String tablePrefix) {
        try {

            CreateMyBatisUtil.create(tableName, jdbc_driver, jdbc_url, jdbc_username, jdbc_password, database);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "ok";
    }


}