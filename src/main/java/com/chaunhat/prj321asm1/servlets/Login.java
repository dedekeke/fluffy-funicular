package com.chaunhat.prj321asm1.servlets;

import java.io.*;
import java.util.regex.Pattern;

import com.chaunhat.prj321asm1.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "LoginServlet", value = "/login")
public class Login extends HttpServlet {
    private Pattern emailPattern;

    @Override
    public void init() throws ServletException {
        super.init();
        String emailRegex = getServletConfig().getInitParameter("emailRegex");
        emailPattern = Pattern.compile(emailRegex);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPwd = request.getParameter("confirmPassword");

        // Validation
        String errorMessage = "";
        if (!emailValid(email)) {
            errorMessage = "Invalid email format";
        }
        if (!password.equals(confirmPwd)) {
            errorMessage = "Passwords do not match";
        }
        if (errorMessage.isBlank() && !name.isEmpty()) {
            User user = User.createUser(name, email, password, password);

            HttpSession session = request.getSession();
            session.setAttribute("name", name);
            session.setAttribute("email", email);
            // Redirect to landing page after login
            response.sendRedirect("/");
        } else {
            request.setAttribute("errorMessage", errorMessage);
            getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
    }

    private boolean emailValid(String emailAddress) {
        return emailPattern.matcher(emailAddress).matches();
    }

}