<!-- admin-home.jsp -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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
        }

        h2 {
            color: #333;
            margin-bottom: 20px;
            text-align: center;
        }

        nav {
            margin-bottom: 20px;
            text-align: center;
        }

        nav ul {
            list-style-type: none;
            padding: 0;
        }

        nav ul li {
            display: inline;
            margin-right: 15px;
        }

        nav ul li a {
            text-decoration: none;
            padding: 10px 20px;
            color: white;
            background-color: #007BFF;
            border-radius: 5px;
            transition: background-color 0.3s ease;
        }

        nav ul li a:hover {
            background-color: #0056b3;
        }

        nav ul li a.active {
            background-color: #0056b3;
        }

        /* Ensure consistent layout on smaller screens */
        @media (max-width: 600px) {
            nav ul li {
                display: block;
                margin: 10px 0;
            }

            nav ul li a {
                display: block;
                width: 100%;
                text-align: center;
            }
        }
    </style>
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
