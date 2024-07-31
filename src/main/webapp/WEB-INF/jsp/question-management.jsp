<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Question Management</title>
</head>
<body>
<h2>Question Management</h2>
<table border="1">
    <thead>
    <tr>
        <th>Category</th>
        <th>Question Description</th>
        <th>Status</th>
        <th>Action</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="questionDTO" items="${questions}">
        <tr>
            <td>${questionDTO.categoryName}</td>
            <td>${questionDTO.question.description}</td>
            <td>${questionDTO.question.active ? 'Active' : 'Suspended'}</td>
            <td>
                <a href="${pageContext.request.contextPath}/admin/question-management/edit/${questionDTO.question.questionId}">Edit</a>
                <form action="${pageContext.request.contextPath}/admin/question-management/toggle-status" method="post" style="display:inline;">
                    <input type="hidden" name="questionId" value="${questionDTO.question.questionId}"/>
                    <button type="submit">${questionDTO.question.active ? 'Suspend' : 'Activate'}</button>
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<a href="${pageContext.request.contextPath}/admin/question-management/add">Add New Question</a>
</body>
</html>
