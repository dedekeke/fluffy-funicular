package com.chaunhat.prj321asm1.servlets;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.chaunhat.prj321asm1.model.User;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "LoginServlet", value = "/login")
public class Login extends HttpServlet {
    // TODO: add cookie!
    protected  ServletContext context;
    private Pattern emailPattern;
    protected List<User> userList;
    protected String name;
    protected String email;
    protected String password;
    protected String confirmPwd;
    protected boolean isAdmin;
    protected boolean rememberMe;



    @Override
    public void init() throws ServletException {
        super.init();
        String emailRegex = getServletConfig().getInitParameter("emailRegex");
        emailPattern = Pattern.compile(emailRegex);
        context = getServletContext();
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        name = request.getParameter("name");
        email = request.getParameter("email");
        password = request.getParameter("password");
        confirmPwd = request.getParameter("confirmPassword");
        isAdmin = request.getParameter("isAdmin") != null;
        rememberMe = request.getParameter("rememberMe") != null;

        if (context.getAttribute("userList") == null){
            userList = new ArrayList<>();
        } else {
            userList = (List<User>) context.getAttribute("userList");
        }

        if (action != null && action.equals("signup")) {
            // Sign-up request
            handleSignup(request, response);
        } else if (action != null && action.equals("signin")){
            // Login request
            handleLogin(request, response);
        } else {
            request.setAttribute("errorMessage", "Something went wrong!");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        context.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    private void handleSignup(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Validation
        String errorMessage = "";
        if (emailInvalid(email)) {
            errorMessage = "Invalid email format";
        }
        if (!password.equals(confirmPwd)) {
            errorMessage = "Passwords do not match";
        }
        if (errorMessage.isBlank() && !name.isEmpty()) {
            User user = User.createUser(name, email, password, password, isAdmin);
            userList.add(user);

            HttpSession session = request.getSession();
            session.setAttribute("name", name);
            session.setAttribute("isAdmin", isAdmin);
            context.setAttribute("isLoggedIn", true);
            context.setAttribute("userList", userList);

            // Redirect to landing page after login
            response.sendRedirect("/");
        } else {
            request.setAttribute("errorMessage", errorMessage);
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }

    private void handleLogin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = User.findUser(email, password, userList);

        // Validation
        String invalidCredential = "";
        if (user == null) {
            invalidCredential = "Invalid credential!";
        }

        if (invalidCredential.isBlank() && user != null) {
            // Redirect to login page after successful sign-up
            HttpSession session = request.getSession();
            session.setAttribute("name", user.name());
            session.setAttribute("isAdmin", user.isAdmin());
            context.setAttribute("isLoggedIn", true);
            response.sendRedirect("/");
        } else {
            request.setAttribute("invalidCredential", invalidCredential);
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }

    private boolean emailInvalid(String emailAddress) {
        return !emailPattern.matcher(emailAddress).matches();
    }

}