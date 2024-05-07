<%@ page import="java.util.*, javax.servlet.http.*" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<header>
    <div class="navbar bg-dark bg-gradient"Ò>
        <div id="home_div">Home</div>
        <div id="userInfo"></div>
    </div>
</header>

<script>
    function renderUserInfo() {
        // get current User + isAdmin
        // handle cases for admin + guest
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
                  >
                  <ul class="dropdown-menu dropdown-menu-dark" aria-labelledby="navbarDarkDropdownMenuLink">
                    <li><a class="dropdown-item disabled" href="#">Welcome ${name}</a></li>
                    <li><a class="dropdown-item" href="/logout">Log Out</a></li>
                  </ul>
                </div>
            `;
            homeDiv.innerHTML = `
                <div class="dropdown home_btn">
                  <svg xmlns="http://www.w3.org/2000/svg"
                    width="16" height="16"
                    fill="currentColor"
                    class="bi bi-list"
                    viewBox="0 0 16 16"
                    data-bs-toggle="dropdown"
                    aria-expanded="false"
                    >
                    <path fill-rule="evenodd" d="M2.5 12a.5.5 0 0 1 .5-.5h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5m0-4a.5.5 0 0 1 .5-.5h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5m0-4a.5.5 0 0 1 .5-.5h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5"/>
                  </svg>
                  <ul class="dropdown-menu dropdown-menu-dark" aria-labelledby="navbarDarkDropdownMenuLink">
                    <li><a class="dropdown-item disabled" href="/">Home</a></li>
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