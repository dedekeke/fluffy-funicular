<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List, com.chaunhat.fluffyfunicular.model.Product" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Shopping Cart</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet">
  <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css" rel="stylesheet">
  <style>
    <%@ include file="/styles/header.css" %>
    <%@ include file="/styles/cart.css" %>
  </style>
</head>
<body  data-bs-theme="dark">
<%@ include file="../../../../../out/artifacts/prj321_asm1_war_exploded/header.jsp" %>

<div class="container mt-5 p-5" style="min-height: 500px">
  <h2 class="mb-4">Your Shopping Cart</h2>
  <div class="row">
    <div class="col-md-12">
      <%
        double total = 0;
      %>
      <c:choose>
        <c:when test="${not empty cartItems}">
          <table class="table table-bordered table-striped">
            <thead>
              <tr>
                <th>Product</th>
                <th>Price</th>
                <th>Quantity</th>
                <th>Subtotal</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
            <c:forEach var="item" items="${cartItems}">
              <tr>
                <td>${item.name}</td>
                <td>$<fmt:formatNumber value="${item.price}" type="number" minFractionDigits="2" maxFractionDigits="2"/></td>

                <td>
                  <form action="${pageContext.request.contextPath}/addToCart" method="post">
                    <input type="hidden" name="productId" value="${item.id}" />
                    <input type="hidden" name="action" value="update" />
                    <input type="number" name="quantity" value="${item.number}" min="1" class="form-control w-50 d-inline"/>
                    <button type="submit" class="btn btn-info btn-sm">
                      <i class="fa fa-refresh"></i> Update
                    </button>
                  </form>
                </td>
                <td>$<fmt:formatNumber value="${item.price * item.number}" type="number" minFractionDigits="2" maxFractionDigits="2"/></td>

                <td>
                  <form action="/deleteFromCart" method="post">
                    <input type="hidden" name="productId" value="${item.id}" />
                    <button type="submit" class="btn btn-danger btn-sm">
                      <i class="fa fa-trash"></i> Remove
                    </button>
                  </form>
                </td>
              </tr>
              <c:set var="total" value="${total + (item.price * item.number)}" />
            </c:forEach>
            </tbody>
          </table>

          <!-- Cart Summary -->
          <div class="float-right">
            <h4>Total: $${total}</h4>
            <a href="${pageContext.request.contextPath}/createOrder" class="checkout-btn">Checkout</a>
          </div>
        </c:when>
        <c:otherwise>
          <p>Your cart is empty. <a href="/">Continue shopping</a>.</p>
        </c:otherwise>
      </c:choose>
    </div>
  </div>
</div>

<%@ include file="../../../../../out/artifacts/prj321_asm1_war_exploded/footer.jsp" %>
<script src="<%= request.getContextPath() %>/static/js/bootstrap.bundle.min.js"></script>
</body>
</html>
