<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Menu</title>
    <style><%@include file="/WEB-INF/css/style.css"%></style></head>
<body>
<div>
<h1>Menu</h1>
    <table>
        <tr>
            <td><button onclick="window.location.href='drivers'">Drivers</button></td>
            <td><button onclick="window.location.href='cars'">Cars</button></td>
            <td><button onclick="window.location.href='manufacturers'">Manufacturers</button></td>
        </tr>
    </table>
    <button onclick="window.location.href='drivers/my-cars'">My cars</button>
</div>
</body>
</html>
