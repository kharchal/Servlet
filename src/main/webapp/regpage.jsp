<%--
  Created by IntelliJ IDEA.
  User: Юля
  Date: 11.08.2016
  Time: 23:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="hav" uri="/WEB-INF/tlds/mytags" %>
<html>
<head>
    <style>
        .red{color: red}
    </style>
    <title>Registration page</title>
</head>
<body>
    <h3>${act}</h3>
    <div class="red">${msg}</div>
    <hav:form errors="${errors}" act="${act}" bean="${user}"/>
    <hav:copyright name="HAV" year="2016"/>
    <a href="/Library">go home</a>
    <br>
</body>
</html>
