<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
    <style>
        <%@ include file="/styles/login.css" %>
    </style>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
</head>
<body>
<% String errorMessage = (String) request.getAttribute("errorMessage"); %>
<% if (errorMessage != null) { %>
<div id="errors">${errorMessage}</div>
<% } %>
<div class="container-fluid background_1 text-light">
    <div class="d-flex flex-column">
        <div class="d-flex justify-content-center">
            <div class="tab-container">
                <ul class="nav nav-tabs" id="formTabs" role="tablist">
                    <li class="nav-item" role="presentation">
                        <button class="nav-link active" id="login-tab" data-bs-toggle="tab" data-bs-target="#login" type="button" role="tab" aria-controls="login" aria-selected="true">Login</button>
                    </li>
                    <li class="nav-item" role="presentation">
                        <button class="nav-link" id="signup-tab" data-bs-toggle="tab" data-bs-target="#signup" type="button" role="tab" aria-controls="signup" aria-selected="false">Sign Up</button>
                    </li>
                </ul>
            </div>
        </div>
        <div class="form-container">
            <div class="form-card col-lg-6 bg-gray bg-gradient">
                <div class="tab-content d-flex justify-content-center w-100" id="formTabContent">
                    <div class="tab-pane fade show active" id="login" role="tabpanel" aria-labelledby="login-tab">
                        <form action="/login" method="post" class="needs-validation" novalidate>
                            <input type="hidden" name="action" value="signin">
                            <h1 style="text-align: center">Login</h1>

                            <div id="avatar_signin">
                                <img src="https://img.freepik.com/free-psd/3d-illustration-person-with-sunglasses_23-2149436188.jpg?size=338&ext=jpg&ga=GA1.1.553209589.1714435200&semt=sph" alt="Avatar">
                            </div>
                            <label for="email_signin" class="form-label">Email</label>
                            <input type="email" name="email" id="email_signin" value="${requestScope.email}" placeholder="Email" class="form-control" required>
                            <label for="password_signin" class="form-label">Password</label>
                            <input type="password" name="password" id="password_signin" value="${requestScope.password}" placeholder="Password" class="form-control" required>

                            <div class="d-flex align-items-start">
                                <input type="checkbox" name="rememberMe" id="rememberMe" class="checkboxBtn">
                                <label for="rememberMe" class="w-100">Remember sign in?</label>
                            </div>
                            <% String invalidCredential = (String) request.getAttribute("invalidCredential"); %>
                            <% if (invalidCredential != null) { %>
                            <div id="loginError" class="w-100 border border-1 border-danger rounded-pill text-light p-1 text-center bg-transparent mt-2">
                                <i class="fa-solid fa-circle-exclamation text-light"></i>
                                ${invalidCredential}
                            </div>
                            <% } %>

                            <div class="w-100 d-flex justify-content-center"><button type="submit" id="loginBtn">Login</button></div>
                        </form>
                    </div>
                    <div class="tab-pane fade w-75" id="signup" role="tabpanel" aria-labelledby="signup-tab">
                        <form action="/login" method="post">
                            <input type="hidden" name="action" value="signup">
                            <h1 style="text-align: center">Sign Up</h1>

                            <div id="avatar_signup">
                                <img src="https://img.freepik.com/free-psd/3d-illustration-person-with-sunglasses_23-2149436188.jpg?size=338&ext=jpg&ga=GA1.1.553209589.1714435200&semt=sph" alt="Avatar">
                            </div>
                            <label for="name_signup">Name</label>
                            <input type="text" name="name" id="name_signup" placeholder="John Doe" required>
                            <label for="email_signup">Email</label>
                            <input type="email" name="email" id="email_signup" placeholder="Email" required>
                            <label for="password_signup">Password</label>
                            <input type="password" name="password" id="password_signup" placeholder="Password" required>
                            <label for="confirmPassword_signup">Confirm your password</label>
                            <input type="password" name="confirmPassword" id="confirmPassword_signup" placeholder="Confirm Password" required>

                            <div class="d-flex align-items-start">
                                <input type="checkbox" name="isAdmin" id="isAdmin" class="checkboxBtn">
                                <label for="isAdmin" class="w-100">Admin?</label>
                            </div>

                            <div class="w-100 d-flex justify-content-center"><button type="submit" id="signupBtn">Sign Up</button></div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"></script>
<script>
    (function () {
        'use strict'

        const forms = document.querySelectorAll('.needs-validation');

        // Loop over them and prevent submission
        Array.prototype.slice.call(forms)
            .forEach(function (form) {
                form.addEventListener('submit', function (event) {
                    if (!form.checkValidity()) {
                        event.preventDefault()
                        event.stopPropagation()
                    }

                    form.classList.add('was-validated')
                }, false)
            })
    })()
</script>
</body>
</html>