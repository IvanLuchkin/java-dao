<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add car</title>
</head>
<body>
<h1>Please, enter the car details</h1>
<form method="post" action="${pageContext.request.contextPath}/cars/add" id="car_form"></form>
<table>
    <tr>
        <td>Model:</td>
        <td>
            <input type="text" name="car_model" form="car_form"><br>
        </td>
    </tr>
    <tr>
        <td>Manufacturer ID:</td>
        <td>
            <input type="number" name="manufacturer_id" form="car_form"><br>
        </td>
    </tr>
</table>
<button type="submit" form="car_form">Submit</button>
</body>
</html>
