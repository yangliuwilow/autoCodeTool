1.修改配置文件
   application.properties  或者配置 index.jsp 页面配置

2:打成jar执行命令
   java -jar autoCodeTool.jar  或者启动Application

生成方法一：
      访问：http://localhost:8080/


生成方法二：

3.访问路径执行创建 ：
   http://localhost:8080/create             #创建全部
   http://localhost:8080/create/tableName   #tableName  指定talbe名称

执行main方法生成java代码
      启动（com.auto.tool.Application）  访问
      http://localhost:8080/create
      http://localhost:8080/create/tableName


第一种:指定运行类:
1 java -cp auto.jar com.auto.tool.volocity.CreateMyBatisUtil



生成方法三：
   //手动执行CreateMyBatisUtil.main()方法生成

注意：mybatis-generator-core version=1.3.5 自己修改源码jar