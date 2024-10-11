package com.chaunhat.fluffyfunicular.controller;

import java.io.*;
import java.sql.SQLException;
import java.util.UUID;
import java.util.regex.Pattern;

import com.chaunhat.fluffyfunicular.model.Account;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "LoginServlet", value = "/login")
public class Login extends HttpServlet {
    protected  ServletContext context;
    private Pattern emailPattern;
    protected String name;
    protected String email;
    protected String password;
    protected boolean isAdmin;
    protected String address;
    protected String phone;



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
        isAdmin = request.getParameter("isAdmin") != null;
        address = request.getParameter("address");
        phone = request.getParameter("phone");

        if (action != null && action.equals("signup")) {
            try {
                handleSignup(request, response);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if (action != null && action.equals("signin")){
            try {
                handleLogin(request, response);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            request.setAttribute("errorMessage", "Something went wrong!");
            request.getRequestDispatcher("/login").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("userToken".equals(cookie.getName())) { // Now using a generic userToken cookie
                    String token = cookie.getValue();
                    Account user = (Account) context.getAttribute(token); // Assuming user is mapped to the token in context

                    if (user != null) {
                        HttpSession session = request.getSession();
                        session.setAttribute("name", user.name());
                        if (user.account_role() == 1) session.setAttribute("isAdmin", true);
                        else session.setAttribute("isAdmin", false);

                        request.setAttribute("email", user.email());
                        request.setAttribute("password", user.password());
                        context.setAttribute("isLoggedIn", true);

                        context.getRequestDispatcher("/login.jsp").forward(request, response);
                        return;
                    }
                }
            }
        }

        // If the user is not authenticated or the cookie doesn't exist, show the login page
        context.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    private void handleSignup(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String errorMessage = "";
        if (emailInvalid(email)) errorMessage = "Invalid email format";
        boolean isCreated = Account.createAccount(name, email, password, isAdmin, address, phone);

        if (errorMessage.isBlank() && !name.isEmpty() && isCreated) {
            HttpSession session = request.getSession();
            session.setAttribute("name", name);
            session.setAttribute("isAdmin", isAdmin);
            context.setAttribute("isLoggedIn", true);

            response.sendRedirect(request.getContextPath() + "/");
        } else {
            request.setAttribute("errorMessage", errorMessage);
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }

    private void handleLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        Account user = Account.getAccount(email, password);
        if (user == null) {
            request.setAttribute("invalidCredential", "Invalid email or password.");
            request.getRequestDispatcher("/login").forward(request, response);
            return;
        }
        String token = generateSecureToken();
        context.setAttribute(token, user);

        Cookie userTokenCookie = new Cookie("userToken", token);
        userTokenCookie.setMaxAge(60 * 60 * 24 * 30); // 30 days
        response.addCookie(userTokenCookie);
        session.setAttribute("email", user.email());

        response.sendRedirect(request.getContextPath() + "/");
    }

    private boolean emailInvalid(String emailAddress) {
        return !emailPattern.matcher(emailAddress).matches();
    }

    private boolean phoneValid(String phone) {
        return true;
    }

    private String generateSecureToken() {
        return UUID.randomUUID().toString();
    }
}