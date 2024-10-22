<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Order Confirmation</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css" rel="stylesheet">
    <style>
        <%@ include file="/styles/header.css" %>
    </style>
</head>
<body data-bs-theme="dark">

<%@ include file="../../../../../out/artifacts/prj321_asm1_war_exploded/header.jsp" %>

<div class="container my-5 p-5" style="min-height: 500px">
    <h2 class="text-center">Your order is being processed</h2>
    <p class="text-center">Thank you, <strong>${name}</strong>, for your order!</p>
    <ul class="list-group">
        <c:forEach var="item" items="${cartItems}">
            <li class="list-group-item d-flex justify-content-between align-items-center">
                    ${item.name}
                <span class="badge bg-primary rounded-pill">${item.number} pcs</span>
            </li>
        </c:forEach>
    </ul>
    <h3 class="my-4">Summary: ${total}</h3>
    <a href="/">Back to shopping</a>
</div>

<%@ include file="../../../../../out/artifacts/prj321_asm1_war_exploded/footer.jsp" %>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
