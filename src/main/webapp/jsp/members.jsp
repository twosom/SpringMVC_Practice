<%@ page import="hello.servlet.domain.memer.MemberRepository" %>
<%@ page import="hello.servlet.domain.memer.Member" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: desirel
  Date: 2021/03/13
  Time: 3:13 오전
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    MemberRepository memberRepository = MemberRepository.getInstance();
    List<Member> members = memberRepository.findAll();
%>
<html>
<head>
    <meta charset="UTF-8"/>
    <title>Title</title>
</head>
<body>
<a href="/index.html">메인</a>
<table>
    <thead>
    <th>id</th>
    <th>username</th>
    <th>age</th>
    </thead>
    <%
        for (Member member : members) {
            out.write("<tr>");
            out.write("     <td>" + member.getId() + "</td>");
            out.write("     <td>" + member.getUsername() + "</td>");
            out.write("     <td>" + member.getAge() + "</td>");
            out.write("</tr>");
        }
    %>
</table>

</body>
</html>
