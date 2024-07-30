<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Contact Us</title>
</head>
<body>
<h2>Contact Us</h2>
<form action="${pageContext.request.contextPath}/contact" method="post">
    <label for="subject">Subject:</label>
    <input type="text" id="subject" name="subject" required><br><br>
    <label for="email">Email:</label>
    <input type="email" id="email" name="email" required><br><br>
    <label for="message">Message:</label>
    <textarea id="message" name="message" required></textarea><br><br>
    <button type="submit">Send</button>
</form>
</body>
</html>
