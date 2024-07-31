<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>User Management</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
        $(document).ready(function(){
            $("form.toggle-status").submit(function(event){
                event.preventDefault(); // Prevent the form from submitting the traditional way

                var form = $(this);
                var formData = form.serialize(); // Serialize the form data

                $.post(form.attr("action"), formData, function(response){
                    // Update the button text and status in the table
                    var userRow = form.closest("tr");
                    var statusCell = userRow.find("td:nth-child(5)");
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
<h2>User Management</h2>
<table border="1">
    <thead>
    <tr>
        <th>User ID</th>
        <th>Email</th>
        <th>First Name</th>
        <th>Last Name</th>
        <th>Status</th>
        <th>Action</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="user" items="${users}">
        <tr>
            <td>${user.userId}</td>
            <td>${user.email}</td>
            <td>${user.firstname}</td>
            <td>${user.lastname}</td>
            <td>${user.active ? 'Active' : 'Suspended'}</td>(Debug: ${user.active})</td>
            <td>
                <form action="${pageContext.request.contextPath}/admin/users/toggleStatus" method="post" class="toggle-status">
                    <input type="hidden" name="userId" value="${user.userId}" />
                    <button type="submit">${user.active ? 'Suspend' : 'Activate'}</button>
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>