<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
            width: 80%;
            min-height: 25px;
            line-height: 25px;
            text-align: center;
            border-collapse: collapse;
        }
    </style>
</head>
<body>
<table style="border:1px red;">
    <tr>
        <td>序号</td>
        <td>数据库</td>
        <td>表名</td>
        <td>备注</td>
        <td>类型</td>
        <td>修改时间</td>
        <td>修改</td>
    </tr>

    <c:forEach items="${tableLists}" var="table"  varStatus="status">
        <tr>
            <td>${status.index + 1}</td>
            <td>${table.table_schema}</td>
            <td>${table.table_name}</td>
            <td>${table.table_comment}</td>
            <td>${table.engine}</td>
            <td>${table.create_time}</td>
            <td>
                <a href="${ctx}/createByReq?jdbc_driver=${jdbc_driver}&jdbc_url=${jdbc_url}&jdbc_username=${jdbc_username}&jdbc_password=${jdbc_password}&database=${database}&tableName=${table.table_name}">生成</a> </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
