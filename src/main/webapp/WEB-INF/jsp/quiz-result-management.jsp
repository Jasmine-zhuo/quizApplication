<!-- quiz-result-management.jsp -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Quiz Result Management</title>
    <style>
        /* Style the table */
        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
        }

        table, th, td {
            border: 1px solid #ddd;
        }

        th, td {
            padding: 12px;
            text-align: left;
        }

        th {
            background-color: #f2f2f2;
            font-weight: bold;
        }

        /* Style the action links */
        a, button {
            color: #007BFF;
            text-decoration: none;
            background-color: transparent;
            border: none;
            cursor: pointer;
        }

        a:hover, button:hover {
            text-decoration: underline;
        }

        /* Add some space above the table */
        h2 {
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
<h2>Quiz Result Management</h2>

<!-- Filter Form -->
<form method="get" action="${pageContext.request.contextPath}/admin/quizzes">
    <label for="categoryFilter">Filter by Category:</label>
    <select name="categoryId" id="categoryFilter">
        <option value="">All</option>
        <c:forEach var="category" items="${categories}">
            <option value="${category.categoryId}" ${param.categoryId == category.categoryId ? 'selected' : ''}>
                    ${category.name}
            </option>
        </c:forEach>
    </select>

    <label for="userFilter">Filter by User:</label>
    <select name="userId" id="userFilter">
        <option value="">All</option>
        <c:forEach var="user" items="${users}">
            <option value="${user.userId}" ${param.userId == user.userId ? 'selected' : ''}>
                    ${user.firstname} ${user.lastname}
            </option>
        </c:forEach>
    </select>

    <button type="submit">Filter</button>
</form>

<table border="1">
    <thead>
    <tr>
        <th>Taken Time</th>
        <th>Category</th>
        <th>User Full Name</th>
        <th>No. of Questions</th>
        <th>Score</th>
        <th>Action</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="quiz" items="${quizResults}">
        <tr>
            <td>${quiz.timeEnd}</td>
            <td>${quiz.categoryName}</td>
            <td>${quiz.firstname} ${quiz.lastname}</td>
            <td>${quiz.numQuestions}</td>
            <td>${quiz.score}</td>
            <td><a href="${pageContext.request.contextPath}/quiz-result/${quiz.quizId}">View Result</a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
