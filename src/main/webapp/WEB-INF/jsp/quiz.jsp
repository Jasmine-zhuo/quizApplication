<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Quiz</title>
</head>
<body>
<h2>Quiz</h2>
<form action="${pageContext.request.contextPath}/quiz" method="post">
    <c:forEach var="question" items="${questions}" varStatus="status">
        <div>
            <h3>Question ${status.index + 1}: ${question.description}</h3>
            <c:forEach var="choice" items="${question.choices}">
                <input type="radio" id="choice${choice.choiceId}" name="selectedChoiceIds_${status.index}" value="${choice.choiceId}" required>
                <label for="choice${choice.choiceId}">${choice.description}</label><br>
            </c:forEach>
        </div>
        <br>
    </c:forEach>
    <button type="submit">Submit Quiz</button>
</form>
</body>
</html>
