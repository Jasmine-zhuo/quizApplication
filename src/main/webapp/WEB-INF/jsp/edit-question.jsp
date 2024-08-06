<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="admin-navbar.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <title>Edit Question</title>
    <style>
        body {
            font-family: Arial, sans-serif;
        }

        h2 {
            margin-bottom: 20px;
        }

        form {
            width: 100%;
            max-width: 600px;
            margin: 0 auto;
        }

        label {
            font-weight: bold;
        }

        input[type="text"], textarea, select {
            width: 100%;
            padding: 8px;
            margin: 10px 0;
            border: 1px solid #ddd;
            border-radius: 4px;
        }

        textarea {
            height: 100px;
        }

        .choice {
            margin-bottom: 10px;
        }

        .choice input[type="radio"] {
            margin-left: 10px;
        }

        button {
            background-color: #007BFF;
            color: white;
            padding: 10px 15px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            margin-top: 10px;
        }

        button:hover {
            background-color: #0056b3;
        }
        #description {
            width: 100%; /* Makes the textarea take the full width of the container */
            height: 100px; /* Increases the height of the textarea */
        }
    </style>
</head>
<body>
<h2>Edit Question</h2>
<form action="${pageContext.request.contextPath}/admin/questions/edit" method="post">
    <input type="hidden" name="questionId" value="${question.questionId}" />
    <div>
        <label for="description">Question Description:</label>
        <textarea id="description" name="description">${question.description}</textarea>
    </div>
    <div>
        <label>Choices:</label>
        <c:forEach var="choice" items="${choices}" varStatus="status">
            <div>
                <input type="hidden" name="choiceIds" value="${choice.choiceId}" />
                <input type="text" name="choiceDescriptions" value="${choice.description}" />
                <input type="radio" name="correctChoiceId" value="${choice.choiceId}" ${choice.correct ? 'checked' : ''} />
                <label>Correct Choice</label>
            </div>
        </c:forEach>
    </div>
    <button type="submit">Save</button>
</form>
</body>
</html>
