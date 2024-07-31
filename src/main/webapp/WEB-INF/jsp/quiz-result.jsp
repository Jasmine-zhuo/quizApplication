<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Quiz Result</title>
    <style>
        .correct { color: green; }
        .incorrect { color: red; }
    </style>
</head>
<body>
<h2>Result</h2>
<p>User: ${quizOwner.firstname} ${quizOwner.lastname}</p>
<p>Start Time: ${quiz.timeStart}</p>
<p>End Time: ${quiz.timeEnd}</p>
<p>Result: ${quizResult}</p>

<h3>Questions</h3>
<c:forEach var="question" items="${questions}" varStatus="status">
    <div>
        <h4>Question ${status.index + 1}: ${question.description}</h4>
        <c:forEach var="choice" items="${question.choices}">
            <c:if test="${choice.choiceId == selectedChoices[status.index]}">
                <p class="${choice.correct ? 'correct' : 'incorrect'}">
                    Your Answer: ${choice.description} ${choice.correct ? '(Correct)' : '(Incorrect)'}
                </p>
                <c:if test="${!choice.correct}">
                    <c:forEach var="correctChoice" items="${question.choices}">
                        <c:if test="${correctChoice.correct}">
                            <p class="correct">Correct Answer: ${correctChoice.description}</p>
                        </c:if>
                    </c:forEach>
                </c:if>
            </c:if>
        </c:forEach>
    </div>
</c:forEach>
<c:if test="${!isAdmin}">
    <a href="${pageContext.request.contextPath}/home">Take Another Quiz</a>
</c:if>
</body>
</html>
