<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard</title>
    <style>
        <%@ include file="/styles/dashboard.css" %>
    </style>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css" rel="stylesheet">
</head>
<%@ include file="header.jsp" %>
<body data-bs-theme="dark">
    <% String errorMessage = (String) request.getAttribute("errorMessage"); %>
    <% String infoMessage = (String) request.getAttribute("infoMessage"); %>
    <% if (errorMessage != null) { %>
        <div id="errorMessage">${errorMessage}</div>
    <% } %>
    <% if (infoMessage != null) { %>
        <div id="infoMessage">${infoMessage}</div>
    <% } %>
    <div class="container border border-secondary">
    <h1 class="text-center m-4 text-light">Product Dashboard</h1>
    <div class="row">
        <div class="col-md-12 mx-auto">
            <div class="card h-100">
                <div class="card-header">
                    <h5 class="card-title text-light">Product List</h5>
                    <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#addProductModal">Add New Product</button>
                </div>
                <div class="card-body">
                    <table class="table table-dark table-bordered table-striped table-hover">
                        <thead>
                        <tr>
                            <th>ID</th>
                            <th>Name</th>
                            <th>Type</th>
                            <th>Brand</th>
                            <th>Action</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="product" items="${products}">
                            <tr>
                                <td>${product.getId()}</td>
                                <td>${product.getName()}</td>
                                <td>${product.getType()}</td>
                                <td>${product.getBrand()}</td>
                                <td>
                                    <div class="d-flex">
                                        <form action="#" method="post">
                                            <input type="hidden" name="productId" value="${product.getId()}" />
                                            <button type="button" class="btn"
                                                    data-bs-toggle="modal"
                                                    data-bs-target="#updateProductModal-${product.getId()}"
                                            >
                                                <i class="fa fa-info-circle"></i>
                                            </button>
                                        </form>
                                        <form action="${pageContext.request.contextPath}/deleteProduct" method="post">
                                            <input type="hidden" name="productId" value="${product.getId()}" />
                                            <button type="submit" class="btn btn-danger">
                                                <i class="fa fa-trash"></i>
                                            </button>
                                        </form>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
                <div class="modal fade" id="addProductModal" tabindex="-1" aria-labelledby="addProductModalLabel" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="addProductModalLabel">Add New Product</h5>
                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                            </div>
                            <div class="modal-body">
                                <form action="/dashboard" method="post">
                                    <input type="hidden" name="action" value="add"/>
                                    <div class="mb-3">
                                        <label>Name:</label>
                                        <input type="text" name="name" required class="form-control"/>
                                    </div>
                                    <div class="mb-3">
                                        <label>Description:</label>
                                        <input type="text" name="description" required class="form-control"/>
                                    </div>
                                    <div class="mb-3">
                                        <label>Price:</label>
                                        <input type="text" name="price" required class="form-control"/>
                                    </div>
                                    <div class="mb-3">
                                        <label>Image:</label>
                                        <input type="text" name="image" required class="form-control"/>
                                    </div>
                                    <div class="mb-3">
                                        <label>Type:</label>
                                        <input type="text" name="type" required class="form-control"/>
                                    </div>
                                    <div class="mb-3">
                                        <label>Brand:</label>
                                        <input type="text" name="brand" required class="form-control"/>
                                    </div>

                                    <button type="submit" class="btn btn-success">Add Product</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- Update Modal for Each Product -->
                <c:forEach var="product" items="${products}">
                    <div class="modal fade" id="updateProductModal-${product.getId()}" tabindex="-1" aria-labelledby="updateProductModalLabel" aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="updateProductModalLabel">Update Product - ${product.getName()}</h5>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                </div>
                                <div class="modal-body">
                                    <form action="/updateProduct" method="post">
                                        <input type="hidden" name="id" value="${product.getId()}"/> <!-- Hidden input for the product ID -->
                                        <div class="mb-3">
                                            <label for="updateProductName-${product.getId()}">Name:</label>
                                            <input type="text" name="name" id="updateProductName-${product.getId()}" value="${product.getName()}" required class="form-control"/>
                                        </div>
                                        <div class="mb-3">
                                            <label for="updateProductDescription-${product.getId()}">Description:</label>
                                            <input type="text" name="description" id="updateProductDescription-${product.getId()}" value="${product.getDescription()}" required class="form-control"/>
                                        </div>
                                        <div class="mb-3">
                                            <label for="updateProductPrice-${product.getId()}">Price:</label>
                                            <input type="text" name="price" id="updateProductPrice-${product.getId()}" value="${product.getPrice()}" required class="form-control"/>
                                        </div>
                                        <div class="mb-3">
                                            <label for="updateProductImage-${product.getId()}">Image:</label>
                                            <input type="text" name="image" id="updateProductImage-${product.getId()}" value="${product.getSrc()}" class="form-control"/>
                                        </div>
                                        <div class="mb-3">
                                            <label for="updateProductType-${product.getId()}">Type:</label>
                                            <input type="text" name="type" id="updateProductType-${product.getId()}" value="${product.getType()}" required class="form-control"/>
                                        </div>
                                        <div class="mb-3">
                                            <label for="updateProductBrand-${product.getId()}">Brand:</label>
                                            <input type="text" name="brand" id="updateProductBrand-${product.getId()}" value="${product.getBrand()}" class="form-control"/>
                                        </div>
                                        <button type="submit" class="btn btn-success">Update Product</button>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>

            </div>
        </div>
    </div>
</div>
<%@ include file="footer.jsp" %>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>