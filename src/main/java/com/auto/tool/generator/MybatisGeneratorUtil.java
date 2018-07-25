package com.auto.tool.generator;

import com.auto.tool.util.JdbcUtil;
import com.auto.tool.util.PropertiesFileUtil;
import com.auto.tool.util.StringUtil;
import com.auto.tool.util.VelocityUtil;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.VelocityContext;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 代码生成类
 * Created by   on 2017/1/10.
 */
public class MybatisGeneratorUtil {

    private static final Logger logger = LoggerFactory.getLogger(MybatisGeneratorUtil.class);

    private static String generatorConfig_vm = "/template/generatorConfig.vm";
    // generatorConfig模板路径
    // Service模板路径
    private static String service_vm = "/template/Service.vm";
    // service模板路径
    private static String mapper_vm = "/template/mapper.vm";
    // ServiceMock模板路径
    private static String serviceMock_vm = "/template/ServiceMock.vm";
    // ServiceImpl模板路径
    private static String serviceImpl_vm = "/template/ServiceImpl.vm";
    // ServiceImpl模板路径
    private static String controller_vm = "/template/Controller.vm";

    private static String TABLE_PREFIX = PropertiesFileUtil.getInstance("application").get("mysql.data.table.prefix");

    /**
     * 根据模板生成generatorConfig.xml文件
     *
     * @param jdbcDriver   驱动路径
     * @param jdbcUrl      链接
     * @param jdbcUsername 帐号
     * @param jdbcPassword 密码
     * @param module       项目模块
     * @param database     数据库
     * @param tableName    表前缀
     * @param packageName  包名
     */
    public static void generator(
            String jdbcDriver,
            String jdbcUrl,
            String jdbcUsername,
            String jdbcPassword,
            String module,
            String database,
            String tableName,
            String packageName,
            Map<String, String> lastInsertIdTables) throws Exception {

		/*String os = System.getProperty("os.name");
		if (os.toLowerCase().startsWith("win")) {
			generatorConfig_vm = MybatisGeneratorUtil.class.getResource(generatorConfig_vm).getPath().replaceFirst("/", "");
			service_vm = MybatisGeneratorUtil.class.getResource(service_vm).getPath().replaceFirst("/", "");
			serviceMock_vm = MybatisGeneratorUtil.class.getResource(serviceMock_vm).getPath().replaceFirst("/", "");
			serviceImpl_vm = MybatisGeneratorUtil.class.getResource(serviceImpl_vm).getPath().replaceFirst("/", "");
		} else {
			generatorConfig_vm = MybatisGeneratorUtil.class.getResource(generatorConfig_vm).getPath();
			service_vm = MybatisGeneratorUtil.class.getResource(service_vm).getPath();
			serviceMock_vm = MybatisGeneratorUtil.class.getResource(serviceMock_vm).getPath();
			serviceImpl_vm = MybatisGeneratorUtil.class.getResource(serviceImpl_vm).getPath();
		}*/

        String targetProject = module + "/" + module + "-dao";
        String basePath = MybatisGeneratorUtil.class.getResource("/").getPath().replace("/target/classes/", "").replace(targetProject, "").replaceFirst("/", "").replace("Tool", "");
        String generatorConfigXml = MybatisGeneratorUtil.class.getResource("/").getPath() + "/generatorConfig.xml";
        if (basePath.contains("jar")) {
            basePath = PropertiesFileUtil.getInstance("application").get("project.dir");
            generatorConfigXml = basePath + "generatorConfig.xml";
            logger.info("basePath:" + basePath);
        }
        targetProject = basePath + targetProject;
        if (StringUtils.isEmpty(database)) {
            database = jdbcUrl.substring(jdbcUrl.lastIndexOf("/") + 1, jdbcUrl.lastIndexOf("?"));
        }
        //String sql = "SELECT table_name FROM INFORMATION_SCHEMA.TABLES WHERE table_schema = '" + database + "' AND table_name LIKE '" + tableName + "%';";
        String sql = "SELECT table_name FROM INFORMATION_SCHEMA.TABLES WHERE table_schema = '" + database + "' ";
        if (StringUtils.isNotEmpty(tableName)) {
            sql += "  AND table_name LIKE '" + tableName + "%' ";
        }
        logger.info("========== 开始生成generatorConfig.xml文件 ==========");

        List<Map<String, Object>> tables = new ArrayList<Map<String, Object>>();
        try {
            VelocityContext context = new VelocityContext();
            Map<String, Object> table;
            // 查询定制前缀项目的所有表
            JdbcUtil jdbcUtil = new JdbcUtil(jdbcDriver, jdbcUrl, jdbcUsername, jdbcPassword);
            List<Map> result = jdbcUtil.selectByParams(sql, null);
            if (result == null || result.size() == 0) {
                logger.info("未找到相应的数据库表tableName：" + tableName);
                return;
            }
            for (Map map : result) {
                logger.info("table_name:", map.get("TABLE_NAME"));
                table = new HashMap<String, Object>(2);
                table.put("table_name", map.get("TABLE_NAME"));
                table.put("model_name", StringUtil.lineToHump(ObjectUtils.toString(map.get("TABLE_NAME"))));
                tables.add(table);
            }
            jdbcUtil.release();

            String targetProjectSqlMap = basePath + module + "/" + module + "-rpc-service";
            String targetProjectApiMap = basePath + module + "/" + module + "-rpc-api";
            context.put("tables", tables);
            context.put("generator_javaModelGenerator_targetPackage", packageName + ".entity");
            context.put("generator_javaClientGenerator_targetPackage", packageName + ".mapper");
            context.put("targetProject", targetProjectSqlMap);
            context.put("targetProjectApiMap", targetProjectApiMap);
            context.put("generator_jdbc_password", jdbcPassword);
            context.put("last_insert_id_tables", lastInsertIdTables);
            createDir(new File(targetProjectApiMap + "/src/main/java/" + packageName.replaceAll("\\.", "/") + "/entity"));
            createDir(new File(targetProjectSqlMap + "/src/main/java/" + packageName.replaceAll("\\.", "/") + "/mapper"));
            deleteDir(new File(targetProjectSqlMap + "/src/main/resources/mybatis"));
            createDir(new File(targetProjectSqlMap + "/src/main/resources/mybatis"));
            VelocityUtil.generate(generatorConfig_vm, generatorConfigXml, context);
            // 删除旧代码
            //deleteDir(new File(targetProject + "/src/main/java/" + packageName.replaceAll("\\.", "/") + "/mapper"));
            deleteDir(new File(targetProjectSqlMap + "/src/main/resources/" + packageName.replaceAll("\\.", "/") + "/mapper"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("========== 结束生成generatorConfig.xml文件 ==========");

        logger.info("========== 开始运行MybatisGenerator ==========");
        List<String> warnings = new ArrayList<String>();
        File configFile = new File(generatorConfigXml);
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(configFile);
        config.getContexts().get(0).getTableConfigurations();
        DefaultShellCallback callback = new DefaultShellCallback(true);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);
        for (String warning : warnings) {
            logger.info(warning);
        }

        logger.info("========== 结束运行MybatisGenerator ==========");
        // 主键
        //IntrospectedColumn pkColumn = introspectedTable.getPrimaryKeyColumns().get(0);
        logger.info("========== 开始生成Service ==========");
        String ctime = new SimpleDateFormat("yyyy/M/d").format(new Date());
        String servicePath = basePath + module + "/" + module + "-rpc-api" + "/src/main/java/" + packageName.replaceAll("\\.", "/") + "/service";
        String mapperPath = basePath + module + "/" + module + "-rpc-service" + "/src/main/java/" + packageName.replaceAll("\\.", "/") + "/mapper";
        String serviceImplPath = basePath + module + "/" + module + "-rpc-service" + "/src/main/java/" + packageName.replaceAll("\\.", "/") + "/service/impl";
        String controllerPath = basePath + module + "/" + module + "-rpc-service" + "/src/main/java/" + packageName.replaceAll("\\.", "/") + "/web/controller";
        createDir(new File(servicePath));
        createDir(new File(serviceImplPath));
        createDir(new File(controllerPath));
        createDir(new File(serviceImplPath));
        //  TODO 自己修改jar
        List<IntrospectedTable> IntrospectedTables = myBatisGenerator.configuration.getContexts().get(0).introspectedTables;

        for (int i = 0; i < tables.size(); i++) {
            String model = StringUtil.lineToHump(ObjectUtils.toString(tables.get(i).get("table_name")));
            String service = servicePath + "/" + model + "Service.java";
            String serviceImpl = serviceImplPath + "/" + model + "ServiceImpl.java";
            String mapperFile = mapperPath + "/" + model + "Mapper.java";
            //config.getContexts().get(0).getGenerationSteps()
            //获取 解析的表主键列名和名称
            //  TODO 自己修改jar
            IntrospectedTable introspectedTable = IntrospectedTables.get(i);
            IntrospectedColumn primaryKeyColumn = introspectedTable.primaryKeyColumns.get(0);
            logger.info("主键类型" + primaryKeyColumn.getFullyQualifiedJavaType());
            VelocityContext context = new VelocityContext();
            context.put("package_name", packageName);
            context.put("model", model);
            //  TODO 自己修改jar  start
            context.put("primaryKeyColumn", primaryKeyColumn.getJavaProperty());
            context.put("toUpperCaseprimaryKeyColumn", toUpperCaseFirstOne(primaryKeyColumn.getJavaProperty()));
            context.put("primaryKeyColumnJavaType", primaryKeyColumn.getFullyQualifiedJavaType());
            // TODO END
            context.put("param", StringUtil.toLowerCaseFirstOne(model));
            context.put("mapper", StringUtil.toLowerCaseFirstOne(model));
            context.put("ctime", ctime);
            // 生成service
            VelocityUtil.generate(service_vm, service, context);
            logger.info(service);
            // 生成mapper
            VelocityUtil.generate(mapper_vm, mapperFile, context);
            logger.info(mapperFile);
            // 生成serviceImpl
            VelocityUtil.generate(serviceImpl_vm, serviceImpl, context);
            logger.info(serviceImpl);
            // 生成 controller
            String controller = controllerPath + "/" + model + "Controller.java";
            VelocityUtil.generate(controller_vm, controller, context);
            logger.info(controller);

        }
        logger.info("========== 结束生成Service ==========");
    }

    //首字母转大写
    public static String toUpperCaseFirstOne(String s) {
        if (Character.isUpperCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
    }

    // 递归删除非空文件夹
    public static void deleteDir(File dir) {
        if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            for (int i = 0; i < files.length; i++) {
                deleteDir(files[i]);
            }
        }
        dir.delete();
    }

    // 递归删除非空文件夹
    public static void createDir(File dir) {
        logger.info("create files dir" + dir.getPath());
        dir.mkdirs();
    }
}
