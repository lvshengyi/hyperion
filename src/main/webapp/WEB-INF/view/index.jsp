<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018-05-04
  Time: 2:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>test</title>
</head>
<body>
<form action="show" method="post">
    <label>输入用户ID:</label><input name="userId" type="text" />
    <br />
    <input type="submit" value="提交" />
</form>
<form action="add" method="post">
    <label>用户名:</label><input name="username" type="text" />
    <br />
    <label>密码:</label><input name="password" type="text" />
    <br />
    <label>年龄:</label><input name="age" type="text" />
    <br />
    <label>最大佣金:</label><input name="maxSalary" type="text" />
    <br />
    <input type="submit" value="新增" />
</form>
</body>
</html>
