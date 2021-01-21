<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add manufacturer</title>
    <style><%@include file="/WEB-INF/css/style.css"%></style></head>
<body>
<div>
    <h1>Please, enter the manufacturer details</h1>
    <form method="post" action="${pageContext.request.contextPath}/manufacturers/add" id="manufacturer_form"></form>
    <table>
        <tr>
            <td>Name:</td>
            <td>
                <input type="text" name="manufacturer_name" form="manufacturer_form"><br>
            </td>
        </tr>
        <tr>
            <td>Country:</td>
            <td>
                <input type="text" name="manufacturer_country" form="manufacturer_form"><br>
            </td>
        </tr>
    </table>
    <button type="submit" form="manufacturer_form">Submit</button>
</div>
</body>
</html>
