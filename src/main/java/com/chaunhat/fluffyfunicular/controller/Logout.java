package com.chaunhat.fluffyfunicular.controller;

import java.io.*;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "LogoutServlet", value = "/logout")
public class Logout extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        getServletContext().setAttribute("isLoggedIn", "false");
        HttpSession session = request.getSession();
        session.invalidate();
        response.sendRedirect("/");
    }
}