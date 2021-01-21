<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Drivers</title>
    <style><%@include file="/WEB-INF/css/style.css"%></style>
</head>
<body>
<div>
    <h1>Drivers</h1>
    <button onclick="window.location.href='menu'">Menu</button>
    <table border="1">
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>License number</th>
        </tr>
        <c:forEach var="driver" items="${drivers}">
            <tr>
                <td>
                    <c:out value="${driver.id}"/>
                </td>
                <td>
                    <c:out value="${driver.name}"/>
                </td>
                <td>
                    <c:out value="${driver.licenseNumber}"/>
                </td>
                <td>
                    <a href="${pageContext.request.contextPath}/drivers/delete?id=${driver.id}">Delete</a>
                </td>
            </tr>
        </c:forEach>
    </table>
    <button onclick="window.location.href='drivers/add'">Add</button>
</div>
</body>
</html>
