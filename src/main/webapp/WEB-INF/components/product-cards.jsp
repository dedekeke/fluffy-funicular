<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.Base64" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Products</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <h2 class="mb-4">Our Products</h2>
    <div class="row row-cols-1 row-cols-md-2 g-4">
        <c:if test="${products != null && !products.isEmpty()}">
            <c:forEach var="product" items="${products}">
                <div class="col">
                    <div class="card product-card h-100">
                        <img src="data:image/jpeg;base64,${Base64.getEncoder().encodeToString(product.image())}" class="card-img-top" alt="${product.name()}">
                        <div class="card-body">
                            <h5 class="card-title">${product.name()}</h5>
                            <p class="card-text">${product.description()}</p>
                            <p class="card-text">Quantity: ${product.quantity()}</p>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </c:if>
        <c:if test="${products == null || products.isEmpty()}">
            <p>No products available.</p>
        </c:if>
    </div>

    <nav aria-label="Product Pagination">
        <ul class="pagination justify-content-center mt-4">
            <c:if test="${products != null && !products.isEmpty()}">
                <c:set var="totalPages" value="${(products.size() / 4) + (products.size() % 4 == 0 ? 0 : 1)}" />
                <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                    <a class="page-link" href="?page=${currentPage - 1}&view=cards" tabindex="-1" aria-disabled="${currentPage == 1}">Previous</a>
                </li>
                <c:forEach var="i" begin="1" end="${totalPages}">
                    <li class="page-item ${i == currentPage ? 'active' : ''}">
                        <a class="page-link" href="?page=${i}&view=cards">${i}</a>
                    </li>
                </c:forEach>
                <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
                    <a class="page-link" href="?page=${currentPage + 1}&view=cards" aria-disabled="${currentPage == totalPages}">Next</a>
                </li>
            </c:if>
        </ul>
    </nav>
</div>
</body>
</html>
