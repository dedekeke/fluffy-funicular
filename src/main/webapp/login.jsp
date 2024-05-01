<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
    <style>
        <%@ include file="/styles/login.css" %>
    </style>
</head>
<body>
    <% String errorMessage = (String) request.getAttribute("errorMessage"); %>
    <% if (errorMessage != null) { %>
        <div id="errors">${errorMessage}</div>
    <% } %>
    <div class="container-fluid form-container">
        <div class="form-card">
            <form action="/login" method="post">
                <h1 style="text-align: center">Login</h1>

                <div id="avatar">
                    <img src="https://img.freepik.com/free-psd/3d-illustration-person-with-sunglasses_23-2149436188.jpg?size=338&ext=jpg&ga=GA1.1.553209589.1714435200&semt=sph" alt="Avatar">
                </div>
                <input type="text" name="name" id="name" placeholder="Name" required>
                <input type="email" name="email" id="email" placeholder="Email" required>
                <input type="password" name="password" id="password" placeholder="Password" required>
                <input type="password" name="confirmPassword" id="confirmPassword" placeholder="Confirm Password" required>

                <button type="submit" id="loginBtn">Login</button>
                
            </form>
        </div>
    </div>
</body>
</html>