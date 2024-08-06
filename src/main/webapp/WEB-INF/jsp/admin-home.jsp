<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="admin-navbar.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <title>Admin Home</title>
    <style>
        /* Apply basic styling */
        body {
            font-family: Arial, sans-serif;
            background-color: #f9f9f9;
            margin: 0;
            padding: 20px;
            padding-top: 80px; /* Add padding at the top to avoid content overlap with the navbar */
        }

        h2 {
            color: #333;
            margin-bottom: 20px;
            text-align: center;
        }

        .admin-actions {
            display: flex;
            justify-content: center;
            flex-wrap: wrap;
            margin-top: 20px;
        }

        .admin-actions a {
            display: inline-block;
            margin: 10px;
            padding: 15px 25px;
            color: white;
            background-color: #007BFF;
            text-decoration: none;
            border-radius: 5px;
            transition: background-color 0.3s ease;
            text-align: center;
            min-width: 200px; /* Ensure buttons have a consistent size */
        }

        .admin-actions a:hover {
            background-color: #0056b3;
        }

        /* Ensure consistent layout on smaller screens */
        @media (max-width: 600px) {
            .admin-actions a {
                width: 100%;
            }
        }
    </style>
</head>
<body>
<h2>Admin Home</h2>

<div class="admin-actions">
    <a href="${pageContext.request.contextPath}/admin/users">User Management</a>
    <a href="${pageContext.request.contextPath}/admin/quizzes">Quiz Result Management</a>
    <a href="${pageContext.request.contextPath}/admin/questions">Question Management</a>
    <a href="${pageContext.request.contextPath}/admin/contacts">Contact Us Management</a>
    <a href="${pageContext.request.contextPath}/logout">Logout</a>
</div>

</body>
</html>
