<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="/jsp/members/save.jsp" method="post">
    username: <input type="text" name="username" id="username"/>
    age: <input type="text" name="age" id="age"/>
    <!--    <button type="submit">전송</button>-->
    <%--    <button type="submit">전송</button>--%>
    <input type="button" value="전송" onclick="add()"/>
</form>
<script>

    function add() {
        let form = document.createElement('form');
        let age = document.getElementById('age');
        let username = document.getElementById('username');

        form.setAttribute('charset', 'UTF-8');
        form.setAttribute('method', 'POST');
        form.setAttribute('action', '/jsp/members/save.jsp');

        let input = document.createElement('input');
        input.setAttribute('type', 'hidden');
        input.setAttribute('name', 'age');
        input.setAttribute('value', age.value);
        form.appendChild(input);

        input = document.createElement('input');
        input.setAttribute('type', 'hidden');
        input.setAttribute('name', 'username');
        input.setAttribute('value', username.value);
        form.appendChild(input);

        document.body.appendChild(form);
        form.submit();
    }


</script>
</body>
</html>