<%@ page import="java.util.*, javax.servlet.http.*" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<%@ page import="java.util.List, com.chaunhat.fluffyfunicular.model.Product" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<header>
    <div class="navbar bg-dark bg-gradient bg-opacity-75">
        <div id="home_div">Home</div>

<%--        Search bar--%>
        <% List<Product> searchResults = (List<Product>) request.getAttribute("searchResults"); %>
        <div class="d-flex ms-auto position-relative">
            <form action="search" method="post" class="d-flex ms-auto" id="searchForm">
                <input class="form-control me-2" type="search"` id="searchInput" placeholder="Search products" aria-label="Search" autocomplete="off" name="searchString">
                <div class="dropdown">
                    <ul id="searchResultsDropdown" class="dropdown-menu" style="position: absolute; top: 100%; left: 0; right: 0;">
                        <%
                            if (searchResults != null && !searchResults.isEmpty()) {
                                for (Product product : searchResults) {
                        %>
                        <li class="dropdown-item"><%= product.getName() %></li>
                        <%
                            }
                        } else if (searchResults != null) {
                        %>
                        <li class="dropdown-item disabled">No results found</li>
                        <%
                            }
                        %>
                    </ul>
                </div>
            </form>
        </div>

<%--        Cart--%>
        <a class="nav-link" href="<%= request.getContextPath() %>/cart">
            <i class="fa fa-shopping-cart"></i>
            <span class="badge bg-danger">
                <%
                    List<Product> cartItems = (List<Product>) session.getAttribute("cart");
                    int cartSize = cartItems != null ? cartItems.size() : 0;
                    out.print(cartSize);
                %>
            </span>
        </a>
<%--        Avatar/Login btn--%>
        <div id="userInfo"></div>
    </div>
</header>

<script>
    function renderUserInfo() {
        const userInfo = document.getElementById('userInfo');
        const homeDiv = document.getElementById('home_div');
        <%
            String name = (String) session.getAttribute("name");
            boolean isLoggedIn = name != null;
        %>
        const isLoggedIn = <%= isLoggedIn %>;

        if (isLoggedIn) {
            userInfo.innerHTML = `
                <div class="dropdown dropstart me-3">
                  <img
                    src="https://img.freepik.com/free-psd/3d-illustration-person-with-sunglasses_23-2149436188.jpg?size=338&ext=jpg&ga=GA1.1.553209589.1714435200&semt=sph"
                    alt="Profile Picture"
                    class="user-avatar dropdown-toggle"
                    data-bs-toggle="dropdown"
                    aria-expanded="false"
                    style="height: 50px; width: 50"
                  >
                  <ul class="dropdown-menu dropdown-menu-dark" aria-labelledby="navbarDarkDropdownMenuLink">
                    <li><a class="dropdown-item disabled" href="#">Welcome ${name}</a></li>
                    <li><a class="dropdown-item" href="/logout">Log Out</a></li>
                  </ul>
                </div>
            `;
            homeDiv.innerHTML = `
                <div class="dropdown home_btn">
                  <i class="fa fa-bars text-light" aria-hidden="true"></i>
                  <ul class="dropdown-menu dropdown-menu-dark" aria-labelledby="navbarDarkDropdownMenuLink">
                    <li><a class="dropdown-item" href="/">Home</a></li>
                    <li><a class="dropdown-item" href="/dashboard" ${!isAdmin ? 'hidden' : ''}>Dashboard</a></li>
                    <li><a class="dropdown-item" href="/user-manager" ${!isAdmin ? 'hidden' : ''}>User Manager</a></li>
                    <li><a class="dropdown-item" href="/about" ${isAdmin ? 'hidden' : ''}>About Us  </a></li>
                  </ul>
                </div>
            `;
        } else {
            userInfo.innerHTML = '<a href="/login">Login</a>';
            homeDiv.innerHTML = '<a href="/">Home</a>';
        }
    }

    renderUserInfo();
</script>