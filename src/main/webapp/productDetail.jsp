<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.chaunhat.fluffyfunicular.model.Product" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%
  // Dynamically get the context path
  String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Product Detail</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet">
  <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css" rel="stylesheet">
  <style>
    <%@ include file="/styles/header.css" %>
  </style>
</head>
<body data-bs-theme="dark">
<%@ include file="header.jsp" %>

<% String errorMessage = (String) request.getAttribute("errorMessage"); %>
<% String infoMessage = (String) request.getAttribute("infoMessage"); %>
<% if (errorMessage != null) { %>
<div id="errorMessage" class="alert alert-danger text-center">${errorMessage}</div>
<% } %>
<% if (infoMessage != null) { %>
<div id="infoMessage" class="alert alert-info text-center">${infoMessage}</div>
<% } %>

<h1 class="text-center text-light" style="margin-top: 100px">Product Detail</h1>
<div class="container my-5 border border-secondary p-5">
  <div class="row">
    <div class="col-md-6">
      <img src="${product.src}" class="img-fluid" alt="${product.name}" />
    </div>
    <div class="col-md-6">
      <h1 class="text-light">${product.name}</h1>
      <p><strong>Price: </strong>$${product.price}</p>
      <p><strong>Description: </strong>${product.description}</p>
      <p><strong>Quantity Available: </strong>${product.number}</p>
      <p><strong>Type: </strong>${product.type}</p>
      <p><strong>Brand: </strong>${product.brand}</p>

      <form action="${pageContext.request.contextPath}/addToCart" method="post">
        <input type="hidden" name="productId" value="${product.id}">
        <label for="quantity">Quantity:</label>
        <input type="number" name="quantity" id="quantity" value="1" min="1" class="form-control w-25 mb-3">
        <button type="submit" class="btn btn-outline-danger"><i class="fa fa-cart-plus"></i> Add to Cart</button>
      </form>
    </div>
  </div>
</div>

<%@ include file="footer.jsp" %>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
