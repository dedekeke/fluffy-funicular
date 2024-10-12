<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List, com.chaunhat.fluffyfunicular.model.Product" %>
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

<%
    if (isLoggedIn) {
%>
<div aria-live="polite" aria-atomic="true" class="position-relative">
    <div class="toast-container position-absolute top-0 end-0 p-3">
        <div class="toast text-white bg-primary border-0" role="alert" aria-live="assertive" aria-atomic="true" data-bs-delay="2000" id="loggin-toast">
            <div class="toast-body">
                Welcome back, <%= name%>
            </div>
        </div>
    </div>
</div>
<%
    }
%>

<% List<Product> products = (List<Product>) request.getAttribute("products"); %>
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
                <img src="<%= product.getSrc()%>" class="d-block w-100" alt="<%= product.getName() %>">
                <div class="carousel-caption d-none d-md-block">
                    <h5><%= product.getName() %></h5>
                    <p><%= product.getDescription() %></p>
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

<div class="container" data-bs-theme="dark">
    <div class="row">
        <h2 class="my-4">Our Products</h2>
        <div class="col-md-8">
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
                    <a href="productDetail?id=<%= products.get(i).getId() %>" style="text-decoration: none">
                        <div class="card product-card h-100">
                            <img src="<%= products.get(i).getSrc()%>" class="card-img-top" alt="<%= products.get(i).getName() %>">
                            <div class="card-body">
                                <h5 class="card-title"><%= products.get(i).getName() %></h5>
                                <p class="card-text"><%= products.get(i).getDescription() %></p>
                                <p class="card-text">Description: <%= products.get(i).getDescription() %></p>
                            </div>
                        </div>
                    </a>
                </div>
                <% } %>
                <% } else { %>
                <p>No products available.</p>
                <% } %>
            </div>
            <nav aria-label="Product Pagination">
                <ul class="pagination justify-content-center mt-4" data-bs-theme="dark">
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
            <div class="card my-4">
                <img src="https://file.hstatic.net/200000722513/file/khuyen_mai_t10_500x250.png"  alt="banner1"/>
            </div>
            <div class="card my-4">
                <img src="https://file.hstatic.net/200000722513/file/thang_10_layout_web_-08.png"  alt="banner2"/>
            </div>
            <div class="card my-4">
                <img src="https://file.hstatic.net/200000722513/file/thang_10_artboard_12_copy_10.png"  alt="banner2"/>
            </div>
            <div class="card my-4">
                <img src="https://file.hstatic.net/200000722513/file/thang_10_layout_web_-06.png"  alt="banner2"/>
            </div>
        </div>
    </div>
</div>

<%@ include file="footer.jsp" %>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"></script>
<script>
    const toast = document.getElementById('loggin-toast');
    const toastBS = new bootstrap.Toast(toast);

    <% if (isLoggedIn) { %>
        toastBS.show();
    <% } %>
</script>
</body>
</html>
