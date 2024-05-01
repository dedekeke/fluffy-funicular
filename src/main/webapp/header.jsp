<%@ page import="java.util.*, javax.servlet.http.*" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<header>
    <div class="navbar">
        <a href="#">Home</a>
        <div id="userInfo"></div>
    </div>
</header>

<script>
    function renderUserInfo() {
        const userInfo = document.getElementById('userInfo');
        <%
            String name = (String) session.getAttribute("name");
            boolean isLoggedIn = name != null;
        %>
        const isLoggedIn = <%= isLoggedIn %>;

        if (isLoggedIn) {
            userInfo.innerHTML = `
                <div class="dropdown dropstart">
                  <img
                    src="https://img.freepik.com/free-psd/3d-illustration-person-with-sunglasses_23-2149436188.jpg?size=338&ext=jpg&ga=GA1.1.553209589.1714435200&semt=sph"
                    alt="Profile Picture"
                    class="user-avatar dropdown-toggle"
                    data-bs-toggle="dropdown"
                    aria-expanded="false"
                  >
                  <ul class="dropdown-menu dropdown-menu-dark" aria-labelledby="navbarDarkDropdownMenuLink">
                    <li><a class="dropdown-item disabled" href="#">Welcome ${name}</a></li>
                    <li><a class="dropdown-item" href="/logout">Log Out</a></li>
                  </ul>
                </div>
            `;
        } else {
            userInfo.innerHTML = '<a href="/login">Login</a>';
        }
    }

    renderUserInfo();
</script>