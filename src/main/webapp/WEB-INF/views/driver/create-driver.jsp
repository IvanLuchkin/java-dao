<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add driver</title>
    <style><%@include file="/WEB-INF/css/style.css"%></style>
</head>
<body>
<div>
    <h1>Please, enter the driver details</h1>
    <form method="post" action="${pageContext.request.contextPath}/drivers/add" id="driver_form"></form>
    <table>
        <tr>
            <td>Name:</td>
            <td>
                <input type="text" name="driver_name" form="driver_form"><br>
            </td>
        </tr>
        <tr>
            <td>License number:</td>
            <td>
                <input type="text" name="driver_license" form="driver_form"><br>
            </td>
        </tr>
    </table>
    <button type="submit" form="driver_form">Submit</button>
    </div>
</body>
</html>
