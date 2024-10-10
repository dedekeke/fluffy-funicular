<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List, com.chaunhat.fluffyfunicular.model.Product" %>
<%@ page import="java.util.Base64" %>
<!DOCTYPE html>
<html>
<head>
    <title>Search Results</title>
    <%@ include file="includes.jsp" %>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css" rel="stylesheet">
</head>
<body>

<% List<Product> products = (List<Product>) request.getAttribute("results"); %>
<%@ include file="header.jsp" %>

<div class="container mt-5 p-5">
    <h2 class="mb-4">Search Results</h2>
    <div class="row row-cols-1 row-cols-md-2 g-4">
        <%
            int currentPage = 1;
            int pageSize = 4;
            String pageParam = request.getParameter("page");
            if (pageParam != null) {
                currentPage = Integer.parseInt(pageParam);
            }
            int start = (currentPage - 1) * pageSize;
            int end = Math.min(start + pageSize, products.size());

            if (products != null && !products.isEmpty()) {
        %>
        <% for (int i = start; i < end; i++) { %>
        <div class="col">
            <div class="card product-card h-100">
                <img src="<%= products.get(i).getSrc() %>" class="card-img-top" alt="<%= products.get(i).getName() %>" style="height: 100%">
                <div class="card-body">
                    <h5 class="card-title"><%= products.get(i).getName() %></h5>
                    <p class="card-text"><%= products.get(i).getDescription() %></p>
                    <p class="card-text">Quantity: <%= products.get(i).getNumber() %></p>
                </div>
            </div>
        </div>
        <% } %>
        <% } else { %>
        <p>No products found.</p>
        <% } %>
    </div>

    <nav aria-label="Search Product Pagination">
        <ul class="pagination justify-content-center mt-4" data-bs-theme="dark">
            <%
                if (products != null && !products.isEmpty()) {
                    int totalPages = (int) Math.ceil((double) products.size() / pageSize);
            %>
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
            <% } %>
        </ul>
    </nav>
</div>

<%@ include file="footer.jsp" %>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
