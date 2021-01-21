<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add manufacturer</title>
</head>
<body>
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
</body>
</html>
