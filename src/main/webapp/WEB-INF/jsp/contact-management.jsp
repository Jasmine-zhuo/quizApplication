<!-- contact-management.jsp -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="admin-navbar.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <title>Contact Us Management</title>
    <style>
        /* Style the table */
        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
        }

        table, th, td {
            border: 1px solid #ddd;
        }

        th, td {
            padding: 12px;
            text-align: left;
        }

        th {
            background-color: #f2f2f2;
            font-weight: bold;
        }

        /* Style the action links */
        a {
            color: #007BFF;
            text-decoration: none;
        }

        a:hover {
            text-decoration: underline;
        }

        /* Truncate long messages */
        .truncate {
            max-width: 200px;
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
        }

        /* Add some space above the table */
        h2 {
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
<h2>Contact Us Management</h2>
<table border="1">
    <thead>
    <tr>
        <th>Subject</th>
        <th>Message</th>
        <th>Time</th>
        <th>Email</th>
        <th>Action</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="contact" items="${contacts}">
        <tr>
            <td>${contact.subject}</td>
            <td>${contact.email}</td>
            <td>${contact.time}</td>
<%--            <td>${contact.message}</td>--%>
            <td class="truncate">${contact.message}</td>
            <td><a href="${pageContext.request.contextPath}/admin/contact-management/view/${contact.contactId}">View</a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
