package com.auto.tool.util;

import com.auto.tool.generator.MybatisGeneratorUtil;
import com.auto.tool.util.PropertiesFileUtil;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/1/26.
 */
public class CreateJavaCodeFileUtil {

    // 根据命名规范，只修改此常量值即可
    private static String MODULE = PropertiesFileUtil.getInstance("application").get("project.module");
    private static String DATABASE = PropertiesFileUtil.getInstance("application").get("mysql.database");

    private static String PACKAGE_NAME = PropertiesFileUtil.getInstance("application").get("project.package.name");
    private static String JDBC_DRIVER = PropertiesFileUtil.getInstance("application").get("jdbc.driver");
    private static String JDBC_URL = PropertiesFileUtil.getInstance("application").get("jdbc.url");
    private static String JDBC_USERNAME = PropertiesFileUtil.getInstance("application").get("jdbc.username");
    private static String JDBC_PASSWORD = PropertiesFileUtil.getInstance("application").get("jdbc.password");
    // 需要insert后返回主键的表配置，key:表名,value:主键名
    private static Map<String, String> LAST_INSERT_ID_TABLES = new HashMap<String, String>();
    //手动执行main方法生成
    static {
        LAST_INSERT_ID_TABLES.put("upms_user", "user_id");
    }

    /**
     * 自动代码生成
     * @param args
     */
    public static void main(String[] args) throws Exception {
        String tableName="dept";
       MybatisGeneratorUtil.generator(JDBC_DRIVER, JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD, MODULE, DATABASE, tableName, PACKAGE_NAME, LAST_INSERT_ID_TABLES);
    }


    public static void create(String tableName) throws Exception {
        MybatisGeneratorUtil.generator(JDBC_DRIVER, JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD, MODULE, DATABASE, tableName, PACKAGE_NAME, LAST_INSERT_ID_TABLES);
    }



    public static void create(String  tableName,String  jdbc_driver,String jdbc_url,String jdbc_username,String jdbc_password,String database) throws Exception {
        if(StringUtils.isNotEmpty(jdbc_driver))
            JDBC_DRIVER=jdbc_driver;
        if(StringUtils.isNotEmpty(jdbc_url))
            JDBC_URL=jdbc_url;
        if(StringUtils.isNotEmpty(jdbc_username))
            JDBC_USERNAME=jdbc_username;
        if(StringUtils.isNotEmpty(jdbc_password))
            JDBC_PASSWORD=jdbc_password;
        if(StringUtils.isNotEmpty(database))
            DATABASE=database;

        MybatisGeneratorUtil.generator(JDBC_DRIVER, JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD, MODULE, DATABASE, tableName, PACKAGE_NAME, LAST_INSERT_ID_TABLES);
    }
}
