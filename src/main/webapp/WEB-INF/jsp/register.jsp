<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Register</title>
</head>
<body>
<h2>Register</h2>
<form action="${pageContext.request.contextPath}/register" method="post">
    <label for="firstname">First Name:</label>
    <input type="text" id="firstname" name="firstname" required><br><br>
    <label for="lastname">Last Name:</label>
    <input type="text" id="lastname" name="lastname" required><br><br>
    <label for="email">Email:</label>
    <input type="email" id="email" name="email" required><br><br>
    <label for="password">Password:</label>
    <input type="password" id="password" name="password" required><br><br>
    <button type="submit">Register</button>
</form>
</body>
</html>
