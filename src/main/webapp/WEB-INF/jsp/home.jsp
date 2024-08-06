<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="navbar.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <title>Home</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f9f9f9;
            margin: 0;
            padding: 20px;
        }

        .container {
            max-width: 800px;
            margin: auto;
            background-color: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0px 0px 10px 0px #aaa;
        }

        h2 {
            margin-bottom: 20px;
            text-align: center;
            font-size: 24px;
        }

        .success-message {
            color: green;
            margin-bottom: 20px;
            text-align: center;
            font-size: 18px;
            font-weight: bold;
        }

        h3 {
            margin-top: 30px;
            font-size: 20px;
            color: #333;
        }

        ul {
            list-style-type: none;
            padding: 0;
            margin: 0;
        }

        ul li {
            margin-bottom: 10px;
        }

        ul li a {
            text-decoration: none;
            color: #007BFF;
            font-weight: bold;
            font-size: 16px;
        }

        ul li a:hover {
            text-decoration: underline;
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
            padding: 12px;
            text-align: left;
            font-size: 16px;
        }

        th {
            background-color: #f2f2f2;
            font-weight: bold;
        }

        a.view-result {
            color: #007BFF;
            text-decoration: none;
            font-weight: bold;
        }

        a.view-result:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>Welcome, ${user.firstname} ${user.lastname}</h2>

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
    <table>
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
                <td><a class="view-result" href="${pageContext.request.contextPath}/quiz-result/${quiz.quizId}">View Result</a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>

<%--<%@ page contentType="text/html;charset=UTF-8" language="java" %>--%>
<%--<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>--%>
<%--<%@ include file="navbar.jsp" %>--%>

<%--<!DOCTYPE html>--%>
<%--<html>--%>
<%--<head>--%>
<%--    <title>Home</title>--%>
<%--    <style>--%>
<%--        body {--%>
<%--            font-family: Arial, sans-serif;--%>
<%--            margin: 20px;--%>
<%--        }--%>

<%--        h2 {--%>
<%--            margin-bottom: 20px;--%>
<%--        }--%>

<%--        nav ul {--%>
<%--            list-style-type: none;--%>
<%--            padding: 0;--%>
<%--        }--%>

<%--        nav ul li {--%>
<%--            display: inline;--%>
<%--            margin-right: 15px;--%>
<%--        }--%>

<%--        nav ul li a {--%>
<%--            text-decoration: none;--%>
<%--            color: #007BFF;--%>
<%--        }--%>

<%--        nav ul li a:hover {--%>
<%--            text-decoration: underline;--%>
<%--        }--%>

<%--        .success-message {--%>
<%--            color: green;--%>
<%--            margin-bottom: 20px;--%>
<%--        }--%>

<%--        h3 {--%>
<%--            margin-top: 30px;--%>
<%--        }--%>

<%--        table {--%>
<%--            width: 100%;--%>
<%--            border-collapse: collapse;--%>
<%--            margin-top: 10px;--%>
<%--        }--%>

<%--        table, th, td {--%>
<%--            border: 1px solid #ddd;--%>
<%--        }--%>

<%--        th, td {--%>
<%--            padding: 10px;--%>
<%--            text-align: left;--%>
<%--        }--%>

<%--        th {--%>
<%--            background-color: #f2f2f2;--%>
<%--            font-weight: bold;--%>
<%--        }--%>
<%--    </style>--%>
<%--</head>--%>
<%--<body>--%>
<%--<h2>Welcome, ${user.firstname} ${user.lastname}</h2>--%>
<%--<nav>--%>
<%--    <ul>--%>
<%--        <li><a href="${pageContext.request.contextPath}/logout">Logout</a></li>--%>
<%--        <li><a href="${pageContext.request.contextPath}/contact">Contact Us</a></li>--%>
<%--    </ul>--%>
<%--</nav>--%>
<%--<c:if test="${not empty successMessage}">--%>
<%--    <div class="success-message">--%>
<%--            ${successMessage}--%>
<%--    </div>--%>
<%--</c:if>--%>

<%--<h3>Quiz Categories</h3>--%>
<%--<ul>--%>
<%--    <c:forEach var="category" items="${categories}">--%>
<%--        <li><a href="${pageContext.request.contextPath}/quiz?categoryId=${category.categoryId}">${category.name}</a></li>--%>
<%--    </c:forEach>--%>
<%--</ul>--%>
<%--<h3>Recent Quiz Results</h3>--%>
<%--<table border="1">--%>
<%--    <thead>--%>
<%--    <tr>--%>
<%--        <th>Quiz Name</th>--%>
<%--        <th>Date Taken</th>--%>
<%--        <th>Result</th>--%>
<%--    </tr>--%>
<%--    </thead>--%>
<%--    <tbody>--%>
<%--    <c:forEach var="quiz" items="${recentQuizzes}">--%>
<%--        <tr>--%>
<%--            <td>${quiz.name}</td>--%>
<%--            <td>${quiz.timeEnd}</td>--%>
<%--            <td><a href="${pageContext.request.contextPath}/quiz-result/${quiz.quizId}">View Result</a></td>--%>
<%--        </tr>--%>
<%--    </c:forEach>--%>
<%--    </tbody>--%>
<%--</table>--%>
<%--</body>--%>
<%--</html>--%>
