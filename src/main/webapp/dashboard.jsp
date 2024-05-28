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
<body class="dark-gray bg-gradient">
<%@ include file="header.jsp" %>
    <% String errorMessage = (String) request.getAttribute("errorMessage"); %>
    <% String infoMessage = (String) request.getAttribute("infoMessage"); %>
    <% if (errorMessage != null) { %>
        <div id="errorMessage">${errorMessage}</div>
    <% } %>
    <% if (infoMessage != null) { %>
        <div id="infoMessage">${infoMessage}</div>
    <% } %>
    <div class="container border border-secondary slightly-darker-gray">
        <h1 class="text-center m-4 text_lorem">Product Dashboard</h1>
        <div class="row">
            <div class="col-md-12 mx-auto">
                <div class="card dark-gray bg-gradient h-100">
                    <div class="card-header">
                        <h5 class="card-title text_lorem">Product List</h5>
                    </div>
                    <div class="card-body">
                        <table class="table table-success table-bordered table-striped table-hover">
                            <thead>
                            <tr>
                                <th>ID</th>
                                <th>Name</th>
                                <th>Description</th>
                                <th>Quantity</th>
                                <th></th>
                            </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="product" items="${products}">
                                    <tr>
                                        <td><c:out value="${product.id()}" /></td>
                                        <td><c:out value="${product.name()}" /></td>
                                        <td><c:out value="${product.description()}" /></td>
                                        <td><c:out value="${product.quantity()}" /></td>
                                        <td>
                                            <form action="/dashboard" method="post"> <input type="hidden" name="productId" value="${product.id()}" /> <button type="submit" class="btn btn-sm btn-danger">
                                                <i class="fa-solid fa-trash text-light"></i> </button>
                                            </form>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
<%@ include file="footer.jsp" %>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>