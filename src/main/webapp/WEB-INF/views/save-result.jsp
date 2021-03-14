<%@ page import="hello.servlet.domain.memer.Member" %><%--
  Created by IntelliJ IDEA.
  User: desirel
  Date: 2021/03/13
  Time: 2:26 오후
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
성공
<ul>
    <li>id=${savedMember.id}</li>
    <li>username=${savedMember.username}</li>
    <li>age=${savedMember.age}</li>
</ul>
<a href="/">메인</a>

</body>
</html>
