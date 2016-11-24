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
    <title>Main page</title>
</head>
<body>
<h3>Welcome ${logged.name}!</h3>

<form action="/Library" method="post">
    <input type="submit" name="command" value="User list"/>
    <input type="submit" name="command" value="Book list"/>
    <input type="submit" name="command" value="Genre list"/>
    <input type="submit" name="command" value="Phone list"/>
</form>
</body>
</html>
