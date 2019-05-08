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
        <td>数据库类型</td>
        <td>配置</td>
        <td>生成</td>

    </tr>
    <tr>
        <td>1</td>
        <td>mysql</td>
        <td>${emp.empNo}</td>
        <td><a href="index.jsp">生成</a> </td>
    </tr>

</table>
</body>
</html>
