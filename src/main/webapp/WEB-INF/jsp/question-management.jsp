<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="admin-navbar.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <title>Question Management</title>
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

        /* Style the action links and buttons */
        a, button {
            color: #007BFF;
            text-decoration: none;
            background-color: transparent;
            border: none;
            cursor: pointer;
        }

        a:hover, button:hover {
            text-decoration: underline;
        }

        /* Add some space above the table */
        h2 {
            margin-bottom: 20px;
        }
    </style>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
        $(document).ready(function() {
            $("form.toggle-status").submit(function(event) {
                event.preventDefault(); // Prevent the form from submitting the traditional way

                var form = $(this);
                var formData = form.serialize(); // Serialize the form data

                $.post(form.attr("action"), formData, function(response) {
                    // Update the status in the table
                    var questionRow = form.closest("tr");
                    var statusCell = questionRow.find("td:nth-child(3)");
                    var actionButton = form.find("button");

                    if(statusCell.text() === 'Active') {
                        statusCell.text('Suspended');
                        actionButton.text('Activate');
                    } else {
                        statusCell.text('Active');
                        actionButton.text('Suspend');
                    }
                });
            });
        });
    </script>
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
                <a href="${pageContext.request.contextPath}/admin/questions/edit/${questionDTO.question.questionId}">Edit</a>
                <form action="${pageContext.request.contextPath}/admin/questions/toggleStatus" method="post" class="toggle-status">
                    <input type="hidden" name="questionId" value="${questionDTO.question.questionId}" />
                    <button type="submit">${questionDTO.question.active ? 'Suspend' : 'Activate'}</button>
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<a href="${pageContext.request.contextPath}/admin/questions/add">Add New Question</a>
</body>
</html>
