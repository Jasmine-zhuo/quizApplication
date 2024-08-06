<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="navbar.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <title>Quiz Result</title>
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
            text-align: left;
        }

        h2 {
            text-align: center;
            margin-bottom: 20px;
            font-size: 24px;
        }

        h3, h4 {
            margin-bottom: 10px;
            font-size: 18px;
        }

        p {
            margin: 5px 0;
        }

        .correct {
            color: green;
            font-weight: bold;
        }

        .incorrect {
            color: red;
            font-weight: bold;
        }

        .result-info {
            margin-bottom: 20px;
            padding: 10px;
            background-color: #f1f1f1;
            border-radius: 4px;
        }

        .question-block {
            margin-bottom: 20px;
        }

        .correct-answer {
            color: green;
            font-weight: bold;
        }

        .take-another-quiz {
            text-align: center;
            margin-top: 20px;
        }

        .take-quiz-btn {
            padding: 10px 20px;
            background-color: #007BFF;
            color: white;
            border: none;
            border-radius: 4px;
            text-align: center;
            text-decoration: none;
            font-size: 16px;
            cursor: pointer;
            display: inline-block;
        }

        .take-quiz-btn:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>Quiz Result</h2>
    <div class="result-info">
        <p><strong>User:</strong> ${quizOwner.firstname} ${quizOwner.lastname}</p>
        <p><strong>Start Time:</strong> ${quiz.timeStart}</p>
        <p><strong>End Time:</strong> ${quiz.timeEnd}</p>
        <p><strong>Result:</strong> ${quizResult}</p>
    </div>

    <h3>Questions</h3>
    <c:forEach var="question" items="${questions}" varStatus="status">
        <div class="question-block">
            <h4>Question ${status.index + 1}: ${question.description}</h4>
            <c:forEach var="choice" items="${question.choices}">
                <c:if test="${choice.choiceId == selectedChoices[status.index]}">
                    <p class="${choice.correct ? 'correct' : 'incorrect'}">
                        Your Answer: ${choice.description} ${choice.correct ? '(Correct)' : '(Incorrect)'}
                    </p>
                    <c:if test="${!choice.correct}">
                        <c:forEach var="correctChoice" items="${question.choices}">
                            <c:if test="${correctChoice.correct}">
                                <p class="correct-answer">Correct Answer: ${correctChoice.description}</p>
                            </c:if>
                        </c:forEach>
                    </c:if>
                </c:if>
            </c:forEach>
        </div>
    </c:forEach>

    <c:if test="${!isAdmin}">
        <div class="take-another-quiz">
            <a href="${pageContext.request.contextPath}/home" class="take-quiz-btn">Take Another Quiz</a>
        </div>
    </c:if>
</div>
</body>
</html>

<%--<%@ page contentType="text/html;charset=UTF-8" language="java" %>--%>
<%--<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>--%>
<%--<!DOCTYPE html>--%>
<%--<html>--%>
<%--<head>--%>
<%--    <title>Quiz Result</title>--%>
<%--    <style>--%>
<%--        .correct { color: green; }--%>
<%--        .incorrect { color: red; }--%>
<%--    </style>--%>
<%--</head>--%>
<%--<body>--%>
<%--<h2>Result</h2>--%>
<%--<p>User: ${quizOwner.firstname} ${quizOwner.lastname}</p>--%>
<%--<p>Start Time: ${quiz.timeStart}</p>--%>
<%--<p>End Time: ${quiz.timeEnd}</p>--%>
<%--<p>Result: ${quizResult}</p>--%>

<%--<h3>Questions</h3>--%>
<%--<c:forEach var="question" items="${questions}" varStatus="status">--%>
<%--    <div>--%>
<%--        <h4>Question ${status.index + 1}: ${question.description}</h4>--%>
<%--        <c:forEach var="choice" items="${question.choices}">--%>
<%--            <c:if test="${choice.choiceId == selectedChoices[status.index]}">--%>
<%--                <p class="${choice.correct ? 'correct' : 'incorrect'}">--%>
<%--                    Your Answer: ${choice.description} ${choice.correct ? '(Correct)' : '(Incorrect)'}--%>
<%--                </p>--%>
<%--                <c:if test="${!choice.correct}">--%>
<%--                    <c:forEach var="correctChoice" items="${question.choices}">--%>
<%--                        <c:if test="${correctChoice.correct}">--%>
<%--                            <p class="correct">Correct Answer: ${correctChoice.description}</p>--%>
<%--                        </c:if>--%>
<%--                    </c:forEach>--%>
<%--                </c:if>--%>
<%--            </c:if>--%>
<%--        </c:forEach>--%>
<%--    </div>--%>
<%--</c:forEach>--%>
<%--<c:if test="${!isAdmin}">--%>
<%--    <a href="${pageContext.request.contextPath}/home">Take Another Quiz</a>--%>
<%--</c:if>--%>
<%--</body>--%>
<%--</html>--%>
