<%@ page import="biz.model.domain.User" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018-05-07
  Time: 14:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <% User user = (User)request.getAttribute("user");%>
    <% if (user == null){
        %>查无此数据<%
    }else {
        %>结果是:<%
            out.print("用户id：" + user.getId() + "\n");
            out.print("用户名：" + user.getUsername() + "\n");
            out.print("用户年龄：" + user.getAge() + "\n");
            out.print("最大薪资：" + user.getMaxSalary() + "\n");
    }%>
    <br />
    <form type="get" action="index">
        <input type="submit" value="返回" />
    </form>
</body>
</html>
