<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register</title>
    <style><%@include file="/WEB-INF/css/style.css"%></style></head>
<body>
<div>
    <h1>Registration</h1>
    <h4 style="color:darkred">${message}</h4>
    <form method="post" action="${pageContext.request.contextPath}/register" id="register_form"></form>
    <table>
        <tr>
            <td>Name:</td>
            <td>
                <input type="text" name="driver_name" value="${name}" form="register_form"><br>
            </td>
        </tr>
        <tr>
            <td>License number:</td>
            <td>
                <input type="text" name="driver_license" value="${license}" form="register_form"><br>
            </td>
        </tr>
        <tr>
            <td>Driver login:</td>
            <td>
                <input type="text" name="driver_login" value="${login}" form="register_form"><br>
            </td>
        </tr>
        <tr>
            <td>Password:</td>
            <td>
                <input type="password" name="password" form="register_form"><br>
            </td>
        </tr>
        <tr>
            <td>Repeat password:</td>
            <td>
                <input type="password" name="pwd-repeat" form="register_form"><br>
            </td>
        </tr>
    </table>
    <button type="submit" form="register_form">Register</button>
</div>
</body>
</html>
