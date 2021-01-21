<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add driver to car</title>
    <style><%@include file="/WEB-INF/css/style.css"%></style>
</head>
<body>
<div>
    <h1>Please, enter the driver and car IDs</h1>
    <form method="post" action="${pageContext.request.contextPath}/cars/add-driver" id="driver_to_car_form"></form>
    <table>
        <tr>
            <td>Car ID:</td>
            <td>
                <input type="number" name="car_id" form="driver_to_car_form"><br>
            </td>
        </tr>
        <tr>
            <td>Driver ID:</td>
            <td>
                <input type="number" name="driver_id" form="driver_to_car_form"><br>
            </td>
        </tr>
    </table>
    <button type="submit" form="driver_to_car_form">Submit</button>
</div>
</body>
</html>
