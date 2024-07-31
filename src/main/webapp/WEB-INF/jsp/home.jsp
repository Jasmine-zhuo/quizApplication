<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Home</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
        }

        h2 {
            margin-bottom: 20px;
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
            color: #007BFF;
        }

        nav ul li a:hover {
            text-decoration: underline;
        }

        .success-message {
            color: green;
            margin-bottom: 20px;
        }

        h3 {
            margin-top: 30px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 10px;
        }

        table, th, td {
            border: 1px solid #ddd;
        }

        th, td {
            padding: 10px;
            text-align: left;
        }

        th {
            background-color: #f2f2f2;
            font-weight: bold;
        }
    </style>
</head>
<body>
<h2>Welcome, ${user.firstname} ${user.lastname}</h2>
<nav>
    <ul>
        <li><a href="${pageContext.request.contextPath}/logout">Logout</a></li>
        <li><a href="${pageContext.request.contextPath}/contact">Contact Us</a></li>
    </ul>
</nav>
<c:if test="${not empty successMessage}">
    <div class="success-message">
            ${successMessage}
    </div>
</c:if>

<h3>Quiz Categories</h3>
<ul>
    <c:forEach var="category" items="${categories}">
        <li><a href="${pageContext.request.contextPath}/quiz?categoryId=${category.categoryId}">${category.name}</a></li>
    </c:forEach>
</ul>
<h3>Recent Quiz Results</h3>
<table border="1">
    <thead>
    <tr>
        <th>Quiz Name</th>
        <th>Date Taken</th>
        <th>Result</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="quiz" items="${recentQuizzes}">
        <tr>
            <td>${quiz.name}</td>
            <td>${quiz.timeEnd}</td>
            <td><a href="${pageContext.request.contextPath}/quiz-result/${quiz.quizId}">View Result</a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
