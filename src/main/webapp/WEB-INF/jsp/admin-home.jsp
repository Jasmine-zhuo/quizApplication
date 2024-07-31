<!-- admin-home.jsp -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Admin Home</title>
</head>
<body>
<h2>Admin Home</h2>
<nav>
    <ul>
        <li><a href="${pageContext.request.contextPath}/admin/users">User Management</a></li>
        <li><a href="${pageContext.request.contextPath}/admin/quizzes">Quiz Result Management</a></li>
        <li><a href="${pageContext.request.contextPath}/admin/questions">Question Management</a></li>
        <li><a href="${pageContext.request.contextPath}/admin/contacts">Contact Us Management</a></li>
        <li><a href="${pageContext.request.contextPath}/logout">Logout</a></li>
    </ul>
</nav>
</body>
</html>
