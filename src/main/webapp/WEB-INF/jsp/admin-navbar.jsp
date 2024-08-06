<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<nav>
    <ul>
        <li><a href="${pageContext.request.contextPath}/admin/home">Admin Home</a></li>
        <li><a href="${pageContext.request.contextPath}/admin/users">User Management</a></li>
        <li><a href="${pageContext.request.contextPath}/admin/quizzes">Quiz Result Management</a></li>
        <li><a href="${pageContext.request.contextPath}/admin/questions">Question Management</a></li>
        <li><a href="${pageContext.request.contextPath}/admin/contacts">Contact Us Management</a></li>
        <li><a href="${pageContext.request.contextPath}/logout">Logout</a></li>
    </ul>
</nav>

<style>
    nav {
        width: 100%;
        background-color: #333;
        color: #fff;
        padding: 10px 0;
        position: fixed;
        top: 0;
        left: 0;
        z-index: 1000;
    }

    nav ul {
        list-style-type: none;
        padding: 0;
        margin: 0;
        display: flex;
        justify-content: center;
    }

    nav ul li {
        margin: 0 15px;
    }

    nav ul li a {
        color: #fff;
        text-decoration: none;
        font-weight: bold;
    }

    nav ul li a:hover {
        text-decoration: underline;
    }

    /* Add padding to the top of the body to avoid content being hidden under the navbar */
    body {
        padding-top: 50px;
    }
</style>
