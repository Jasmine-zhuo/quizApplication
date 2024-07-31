<!-- contact-management.jsp -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Contact Us Management</title>
</head>
<body>
<h2>Contact Us Management</h2>
<table border="1">
    <thead>
    <tr>
        <th>Subject</th>
        <th>Email</th>
        <th>Time</th>
        <th>Message</th>
        <th>Action</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="contact" items="${contacts}">
        <tr>
            <td>${contact.subject}</td>
            <td>${contact.email}</td>
            <td>${contact.time}</td>
            <td>${contact.message}</td>
            <td><a href="${pageContext.request.contextPath}/admin/contact-management/view/${contact.contactId}">View</a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
