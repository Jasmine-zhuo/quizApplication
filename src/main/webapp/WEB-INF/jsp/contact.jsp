<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="navbar.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <title>Contact Us</title>
    <style>
        /* Increase the width and height of the input fields */
        input[type="text"],
        input[type="email"],
        textarea {
            width: 100%; /* Full width */
            padding: 10px; /* Padding inside the input */
            margin-bottom: 20px; /* Space between fields */
            font-size: 16px; /* Larger font size */
            border-radius: 5px; /* Rounded corners */
            border: 1px solid #ccc; /* Light grey border */
        }

        textarea {
            height: 200px; /* Increase the height of the textarea */
            resize: vertical; /* Allow the user to resize vertically */
        }

        button {
            padding: 10px 20px; /* Add padding to the button */
            font-size: 16px; /* Larger font size for the button */
            border-radius: 5px; /* Rounded corners */
            border: none; /* Remove the border */
            background-color: #4CAF50; /* Green background color */
            color: white; /* White text color */
            cursor: pointer; /* Pointer cursor on hover */
        }

        button:hover {
            background-color: #45a049; /* Darker green on hover */
        }
    </style>
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
