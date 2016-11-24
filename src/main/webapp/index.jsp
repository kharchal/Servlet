<%--
  Created by IntelliJ IDEA.
  User: Юля
  Date: 11.08.2016
  Time: 0:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<style>
    .red{color: red}
</style>
<head>
    <title>Title</title>
</head>
<body>


<div class="red">${msg}</div>
<form action="/Library" method="post">
    Login: <input type="text" name="login"/>
    Password: <input type="password" name="password"/>
    <input type="submit" name="command" value="Login">
</form>
<form action="/Library" method="post">
<input type="submit" name="command" value="Reg page"/>
</form>
</body>
</html>
