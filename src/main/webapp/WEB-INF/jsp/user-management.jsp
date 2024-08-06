<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="admin-navbar.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <title>User Management</title>
    <style>
        /* Center the container and make it consistent */
        body {
            font-family: Arial, sans-serif;
            background-color: #f9f9f9;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
        }

        .container {
            background-color: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0px 0px 10px 0px #aaa;
            max-width: 900px;  /* Reduced width */
            width: 100%;
            text-align: center;
            margin: 20px;
        }

        h2 {
            margin-bottom: 20px;
            font-size: 24px;
        }

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
            padding: 8px;  /* Reduced padding */
            text-align: left;
        }

        th {
            background-color: #f2f2f2;
            font-weight: bold;
        }

        /* Style the action links */
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

        /* Ensure consistent layout on smaller screens */
        @media (max-width: 600px) {
            table {
                font-size: 14px;
            }

            th, td {
                padding: 6px;  /* Further reduced padding for smaller screens */
            }

            h2 {
                font-size: 20px;
            }
        }
    </style>
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
<div class="container">
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
                <td>${user.active ? 'Active' : 'Suspended'}</td>
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
</div>
</body>
</html>

<%--<%@ page contentType="text/html;charset=UTF-8" language="java" %>--%>
<%--<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>--%>
<%--<%@ include file="admin-navbar.jsp" %>--%>

<%--<!DOCTYPE html>--%>
<%--<html>--%>
<%--<head>--%>
<%--    <title>User Management</title>--%>
<%--    <style>--%>
<%--        /* Style the table */--%>
<%--        table {--%>
<%--            width: 100%;--%>
<%--            border-collapse: collapse;--%>
<%--            margin-bottom: 20px;--%>
<%--        }--%>

<%--        table, th, td {--%>
<%--            border: 1px solid #ddd;--%>
<%--        }--%>

<%--        th, td {--%>
<%--            padding: 12px;--%>
<%--            text-align: left;--%>
<%--        }--%>

<%--        th {--%>
<%--            background-color: #f2f2f2;--%>
<%--            font-weight: bold;--%>
<%--        }--%>

<%--        /* Style the action links */--%>
<%--        a, button {--%>
<%--            color: #007BFF;--%>
<%--            text-decoration: none;--%>
<%--            background-color: transparent;--%>
<%--            border: none;--%>
<%--            cursor: pointer;--%>
<%--        }--%>

<%--        a:hover, button:hover {--%>
<%--            text-decoration: underline;--%>
<%--        }--%>

<%--        /* Add some space above the table */--%>
<%--        h2 {--%>
<%--            margin-bottom: 20px;--%>
<%--        }--%>
<%--    </style>--%>
<%--    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>--%>
<%--    <script>--%>
<%--        $(document).ready(function(){--%>
<%--            $("form.toggle-status").submit(function(event){--%>
<%--                event.preventDefault(); // Prevent the form from submitting the traditional way--%>

<%--                var form = $(this);--%>
<%--                var formData = form.serialize(); // Serialize the form data--%>

<%--                $.post(form.attr("action"), formData, function(response){--%>
<%--                    // Update the button text and status in the table--%>
<%--                    var userRow = form.closest("tr");--%>
<%--                    var statusCell = userRow.find("td:nth-child(5)");--%>
<%--                    var actionButton = form.find("button");--%>

<%--                    if(statusCell.text() === 'Active') {--%>
<%--                        statusCell.text('Suspended');--%>
<%--                        actionButton.text('Activate');--%>
<%--                    } else {--%>
<%--                        statusCell.text('Active');--%>
<%--                        actionButton.text('Suspend');--%>
<%--                    }--%>
<%--                });--%>
<%--            });--%>
<%--        });--%>
<%--    </script>--%>
<%--</head>--%>
<%--<body>--%>
<%--<h2>User Management</h2>--%>
<%--<table border="1">--%>
<%--    <thead>--%>
<%--    <tr>--%>
<%--        <th>User ID</th>--%>
<%--        <th>Email</th>--%>
<%--        <th>First Name</th>--%>
<%--        <th>Last Name</th>--%>
<%--        <th>Status</th>--%>
<%--        <th>Action</th>--%>
<%--    </tr>--%>
<%--    </thead>--%>
<%--    <tbody>--%>
<%--    <c:forEach var="user" items="${users}">--%>
<%--        <tr>--%>
<%--            <td>${user.userId}</td>--%>
<%--            <td>${user.email}</td>--%>
<%--            <td>${user.firstname}</td>--%>
<%--            <td>${user.lastname}</td>--%>
<%--            <td>${user.active ? 'Active' : 'Suspended'}</td>--%>
<%--            <td>--%>
<%--                <form action="${pageContext.request.contextPath}/admin/users/toggleStatus" method="post" class="toggle-status">--%>
<%--                    <input type="hidden" name="userId" value="${user.userId}" />--%>
<%--                    <button type="submit">${user.active ? 'Suspend' : 'Activate'}</button>--%>
<%--                </form>--%>
<%--            </td>--%>
<%--        </tr>--%>
<%--    </c:forEach>--%>
<%--    </tbody>--%>
<%--</table>--%>
<%--</body>--%>