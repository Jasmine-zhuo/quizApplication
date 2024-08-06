<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Admin Login</title>
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

        form {
            background-color: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0px 0px 10px 0px #aaa;
            max-width: 300px;
            width: 100%;
            text-align: left;
        }

        h2 {
            text-align: center;
            margin-bottom: 20px;
            font-size: 24px;
        }

        label {
            display: block;
            margin: 10px 0 5px;
            font-weight: bold;
        }

        input[type="email"], input[type="password"] {
            width: calc(100% - 22px);
            padding: 10px;
            margin-bottom: 15px;
            border: 1px solid #ddd;
            border-radius: 4px;
            font-size: 14px;
        }

        button {
            width: 100%;
            padding: 10px;
            background-color: #007BFF;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
        }

        button:hover {
            background-color: #0056b3;
        }
        .alert {
            color: white;
            padding: 10px;
            margin-bottom: 15px;
            border-radius: 4px;
        }

        .alert-danger {
            background-color: #ff4d4d;
        }
    </style>
</head>
<body>
<form action="${pageContext.request.contextPath}/admin/login" method="post">
    <h2>Admin Login</h2>
    <!-- Display error message if it exists -->
    <c:if test="${not empty error}">
        <div class="alert alert-danger">
                ${error}
        </div>
    </c:if>
    <label for="email">Email:</label>
    <input type="email" id="email" name="email" required>

    <label for="password">Password:</label>
    <input type="password" id="password" name="password" required>

    <button type="submit">Login</button>
</form>
</body>
</html>
