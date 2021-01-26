<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
    <style><%@include file="/WEB-INF/css/style.css"%></style></head>
<body>
<div>
    <h1>Login</h1>
    <h4 style="color:darkred">${message}</h4>
    <form method="post" action="${pageContext.request.contextPath}/login" id="login_form"></form>
    <table>
        <tr>
            <td>Driver login:</td>
            <td>
                <input type="text" name="login" form="login_form"><br>
            </td>
        </tr>
        <tr>
            <td>Password:</td>
            <td>
                <input type="password" name="password" form="login_form"><br>
            </td>
        </tr>
    </table>
    <button type="submit" form="login_form">Login</button>
</div>
</body>
</html>
