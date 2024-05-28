<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List, com.chaunhat.prj321asm1.model.Product" %>
<%@ page import="java.util.Base64" %>
<%@ page import="jakarta.servlet.ServletContext" %>
<!DOCTYPE html>
<html>
<head>
    <title>Home</title>
    <%@ include file="includes.jsp" %>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css" rel="stylesheet">
</head>
<body>
<%@ include file="header.jsp" %>

<% ServletContext context = request.getServletContext(); %>
<%
    if (isLoggedIn) {
%>
<div aria-live="polite" aria-atomic="true" class="position-relative">
    <div class="toast-container position-absolute top-0 end-0 p-3">
        <div class="toast text-white bg-primary border-0" role="alert" aria-live="assertive" aria-atomic="true" data-bs-delay="3000" id="loggin-toast">
            <div class="toast-body">
                Welcome back, <%= name%>
            </div>
        </div>
    </div>
</div>
<%
    }
%>

<% List<Product> products = (List<Product>) context.getAttribute("products"); %>
<% int currentPage = 1; %>
<% int pageSize = 4; %>

<% if (products != null && !products.isEmpty()) { %>
    <div id="productCarousel" class="carousel slide" data-bs-ride="carousel">
        <div class="carousel-indicators">
            <% for (int i = 0; i < products.size(); i++) { %>
            <button type="button" data-bs-target="#productCarousel" data-bs-slide-to="<%= i %>" class="<%= i == 0 ? "active" : "" %>" aria-current="<%= i == 0 ? "true" : "false" %>" aria-label="Slide <%= i + 1 %>"></button>
            <% } %>
        </div>
        <div class="carousel-inner">
            <% int count = 0; %>
            <% for (Product product : products) { %>
            <div class="carousel-item <%= count == 0 ? "active" : "" %>">
                <img src="data:image/jpeg;base64,<%= Base64.getEncoder().encodeToString(product.image()) %>" class="d-block w-100" alt="<%= product.name() %>">
                <div class="carousel-caption d-none d-md-block">
                    <h5><%= product.name() %></h5>
                    <p><%= product.description() %></p>
                </div>
            </div>
            <% count++; %>
            <% } %>
        </div>
        <button class="carousel-control-prev" type="button" data-bs-target="#productCarousel" data-bs-slide="prev">
            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
            <span class="visually-hidden">Previous</span>
        </button>
        <button class="carousel-control-next" type="button" data-bs-target="#productCarousel" data-bs-slide="next">
            <span class="carousel-control-next-icon" aria-hidden="true"></span>
            <span class="visually-hidden">Next</span>
        </button>
    </div>
<% } else { %>
<p>No products available.</p>
<% } %>

<div class="container">
    <div class="row">
        <div class="col-md-8">
            <h2 class="mb-4">Our Products</h2>
            <div class="row row-cols-1 row-cols-md-2 g-4">
                <% if (products != null && !products.isEmpty()) { %>
                <% String pageParam = request.getParameter("page"); %>
                <% if (pageParam != null) { %>
                <% currentPage = Integer.parseInt(pageParam); %>
                <% } %>
                <% int start = (currentPage - 1) * pageSize; %>
                <% int end = Math.min(start + pageSize, products.size()); %>
                <% for (int i = start; i < end; i++) { %>
                <div class="col">
                    <div class="card product-card h-100">
                        <img src="data:image/jpeg;base64,<%= Base64.getEncoder().encodeToString(products.get(i).image()) %>" class="card-img-top" alt="<%= products.get(i).name() %>">
                        <div class="card-body">
                            <h5 class="card-title"><%= products.get(i).name() %></h5>
                            <p class="card-text"><%= products.get(i).description() %></p>
                            <p class="card-text">Quantity: <%= products.get(i).quantity() %></p>
                        </div>
                    </div>
                </div>
                <% } %>
                <% } else { %>
                <p>No products available.</p>
                <% } %>
            </div>
            <nav aria-label="Product Pagination">
                <ul class="pagination justify-content-center mt-4">
                    <% if (products != null && !products.isEmpty()) { %>
                    <% int totalPages = (int) Math.ceil((double) products.size() / pageSize); %>
                    <li class="page-item <%= currentPage == 1 ? "disabled" : "" %>">
                        <a class="page-link" href="?page=<%= currentPage - 1 %>" tabindex="-1" aria-disabled="<%= currentPage == 1 %>">Previous</a>
                    </li>
                    <% for (int i = 1; i <= totalPages; i++) { %>
                    <li class="page-item <%= i == currentPage ? "active" : "" %>">
                        <a class="page-link" href="?page=<%= i %>"><%= i %></a>
                    </li>
                    <% } %>
                    <li class="page-item <%= currentPage == totalPages ? "disabled" : "" %>">
                        <a class="page-link" href="?page=<%= currentPage + 1 %>" aria-disabled="<%= currentPage == totalPages %>">Next</a>
                    </li>
                    <% } else { %>
                    <p>No products available.</p>
                    <% } %>
                </ul>
            </nav>
        </div>
        <div class="col-md-4">
            <div class="card cart">
                <div class="card-header">
                    <h2>Shopping Cart</h2>
                </div>
                <div class="cart-summary">
                    <h3>Cart Summary</h3>
                    <p class="subtotal">Subtotal: $<span id="subtotal">0.00</span></p>
                    <p class="tax">Tax: $<span id="tax">0.00</span></p>
                    <p class="total">Total: $<span id="total">0.00</span></p>
                    <br/>
                    <button class="checkout-btn float-end">Checkout</button>
                </div>
            </div>
            <h5 class="mt-4">Popular Products</h5>
            <div class="row row-cols-1 row-cols-md-2 g-2 pbanner">
                <div class="col">
                    <img src="https://w0.peakpx.com/wallpaper/410/221/HD-wallpaper-full-black-screen-black-car-thumbnail.jpg" width="100px" height="100px" alt="Product 1">
                </div>
                <div class="col">
                    <img src="https://i.etsystatic.com/21543272/r/il/05b6fd/5219117617/il_fullxfull.5219117617_j7ap.jpg" alt="Product 2" width="100px" height="100px">
                </div>
                <div class="col">
                    <img src="https://xboxdesignlab.xbox.com/media/wysiwyg/standard_wireless_controller/whats_new/XDL_Standard-Wireless-Controller_Sync-Slider-768_01_1083x609_02.jpg" alt="Product 3" width="100px" height="100px">
                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="footer.jsp" %>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"></script>
<%--script for toasting welcome message--%>
<script>
    const toast = document.getElementById('loggin-toast');
    const toastBS = new bootstrap.Toast(toast);

    <% if (isLoggedIn) { %>
        toastBS.show();
    <% } %>
</script    >
</body>
</html>
