<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>自动代码生成</title>
    <style>
        table, table tr th, table tr td {
            border: 1px solid red;
        }

        table {
            margin-top: 50px;
            align:center;
            width: 90%;

            text-align: center;
            border-collapse: collapse;
        }
    </style>
</head>
<body>
<table style="border:1px red;">
    <tr>
        <td>序号</td>
        <td>数据库类型</td>
        <td>配置</td>
        <td>代码生成</td>

    </tr>
    <tr>
        <td>1</td>
        <td>mysql</td>
        <td>dbc_driver=com.mysql.jdbc.Driver&jdbc_url=jdbc:mysql://192.168.7.108:3306/willow?useUnicode=true&characterEncoding=gbk&jdbc_username=root&jdbc_password=123456&database=willow</td>
        <td style="width: 160px;"><a href="${ctx}/list?jdbc_driver=com.mysql.jdbc.Driver&jdbc_url=jdbc:mysql://192.168.7.108:3306/willow?useUnicode=true&characterEncoding=gbk&jdbc_username=root&jdbc_password=123456&database=willow">选择table生成</a>
            <a href="${ctx}/createByReq?jdbc_driver=com.mysql.jdbc.Driver&jdbc_url=jdbc:mysql://192.168.7.108:3306/willow?useUnicode=true&characterEncoding=gbk&jdbc_username=root&jdbc_password=123456&database=willow">生成</a></td>
    </tr>

</table>
</body>
</html>
