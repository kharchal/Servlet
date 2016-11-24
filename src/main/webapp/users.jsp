<%--
  Created by IntelliJ IDEA.
  User: Юля
  Date: 11.08.2016
  Time: 0:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="hav" uri="/WEB-INF/tlds/mytags" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<hav:beanTable list="${users}" action="/Library" search="${searchUser}"/>
<hr>

<a href="/Library">go home</a>
<hav:copyright name="HAV" year="2016"/>
</body>
</html>
