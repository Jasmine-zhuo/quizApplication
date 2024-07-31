<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>View Message</title>
    <style>
        .container {
            width: 80%;
            margin: 0 auto;
            padding: 20px;
            border: 1px solid #ddd;
            border-radius: 5px;
            background-color: #f9f9f9;
        }
        h2 {
            margin-bottom: 20px;
        }
        label {
            font-weight: bold;
        }
        p {
            margin-bottom: 10px;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>Contact Message</h2>
    <p><label>Subject:</label> ${contact.subject}</p>
    <p><label>Email:</label> ${contact.email}</p>
    <p><label>Time:</label> ${contact.time}</p>
    <p><label>Message:</label></p>
    <p>${contact.message}</p>
    <a href="${pageContext.request.contextPath}/admin/contacts">Back to Contact Management</a>
</div>
</body>
</html>
