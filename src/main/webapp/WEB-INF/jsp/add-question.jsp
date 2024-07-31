<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add New Question</title>
    <style>
        #description {
            width: 100%;
            height: 100px;
        }
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
    </style>
</head>
<body>
<h2>Add New Question</h2>
<form action="${pageContext.request.contextPath}/admin/questions/add" method="post">
    <div>
        <label for="category">Category:</label>
        <select id="category" name="categoryId">
            <c:forEach var="category" items="${categories}">
                <option value="${category.categoryId}">${category.name}</option>
            </c:forEach>
        </select>
    </div>
    <div>
        <label for="description">Question Description:</label>
        <textarea id="description" name="description"></textarea>
    </div>
    <div>
        <label>Choices:</label>
        <div>
            <input type="text" name="choiceDescriptions" placeholder="Choice 1" />
            <input type="radio" name="correctChoiceIndex" value="0" />
            <label>Correct Choice</label>
        </div>
        <div>
            <input type="text" name="choiceDescriptions" placeholder="Choice 2" />
            <input type="radio" name="correctChoiceIndex" value="1" />
            <label>Correct Choice</label>
        </div>
        <div>
            <input type="text" name="choiceDescriptions" placeholder="Choice 3" />
            <input type="radio" name="correctChoiceIndex" value="2" />
            <label>Correct Choice</label>
        </div>
        <div>
            <input type="text" name="choiceDescriptions" placeholder="Choice 4" />
            <input type="radio" name="correctChoiceIndex" value="3" />
            <label>Correct Choice</label>
        </div>
    </div>
    <button type="submit">Add Question</button>
</form>
</body>
</html>
