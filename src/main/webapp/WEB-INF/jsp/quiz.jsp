<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="navbar.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <title>Quiz</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f9f9f9;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }

        .container {
            background-color: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0px 0px 10px 0px #aaa;
            max-width: 600px;
            width: 100%;
        }

        .error {
            background-color: #f8d7da;
            color: #721c24;
            padding: 10px;
            border-radius: 4px;
            margin-bottom: 20px;
            text-align: center;
        }

        h2 {
            text-align: center;
            margin-bottom: 20px;
        }

        .questions {
            list-style-type: none;
            padding: 0;
        }

        .question {
            margin-bottom: 15px;
        }

        .choices {
            list-style-type: none;
            padding: 0;
        }

        .choice {
            margin: 5px 0;
        }

        .submit-btn {
            display: block;
            width: 100%;
            padding: 10px;
            background-color: #007BFF;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
            text-align: center;
        }

        .submit-btn:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>Quiz</h2>

    <!-- Display error message if there are no active questions -->
    <c:if test="${not empty error}">
        <div class="error">${error}</div>
    </c:if>

    <!-- Display quiz questions if available -->
    <c:if test="${not empty questions}">
        <form action="${pageContext.request.contextPath}/quiz" method="post">
            <c:forEach var="question" items="${questions}" varStatus="status">
                <div class="question">
                    <h3>Question ${status.index + 1}: ${question.description}</h3>
                    <c:forEach var="choice" items="${question.choices}">
                        <input type="radio" id="choice${choice.choiceId}" name="selectedChoiceIds_${status.index}" value="${choice.choiceId}" required>
                        <label for="choice${choice.choiceId}">${choice.description}</label><br>
                    </c:forEach>
                </div>
                <br>
            </c:forEach>
            <button type="submit" class="submit-btn">Submit Quiz</button>
        </form>
    </c:if>
</div>
</body>
</html>

<%--<%@ page contentType="text/html;charset=UTF-8" language="java" %>--%>
<%--<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>--%>
<%--<!DOCTYPE html>--%>
<%--<html>--%>
<%--<head>--%>
<%--    <title>Quiz</title>--%>
<%--</head>--%>
<%--<body>--%>
<%--<h2>Quiz</h2>--%>
<%--<form action="${pageContext.request.contextPath}/quiz" method="post">--%>
<%--    <c:forEach var="question" items="${questions}" varStatus="status">--%>
<%--        <div>--%>
<%--            <h3>Question ${status.index + 1}: ${question.description}</h3>--%>
<%--            <c:forEach var="choice" items="${question.choices}">--%>
<%--                <input type="radio" id="choice${choice.choiceId}" name="selectedChoiceIds_${status.index}" value="${choice.choiceId}" required>--%>
<%--                <label for="choice${choice.choiceId}">${choice.description}</label><br>--%>
<%--            </c:forEach>--%>
<%--        </div>--%>
<%--        <br>--%>
<%--    </c:forEach>--%>
<%--    <button type="submit">Submit Quiz</button>--%>
<%--</form>--%>
<%--</body>--%>
<%--</html>--%>
