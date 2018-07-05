1.修改配置文件
   application.properties
2:打成jar执行命令
   java -jar auto.jar
3.访问路径执行创建 ：
   http://localhost:8080/create
   http://localhost:8080/create/tableName

执行main方法生成java代码
      启动（com.auto.tool.Application）  访问
      http://localhost:8080/create
      http://localhost:8080/create/tableName
第一种:指定运行类:
1 java -cp auto.jar com.auto.tool.volocity.main.VelocityMain