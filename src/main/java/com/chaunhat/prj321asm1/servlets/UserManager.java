package com.chaunhat.prj321asm1.servlets;

import java.io.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "UserManagerServlet", value = "/user-manager")
public class UserManager extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getRequestDispatcher("/user_manager.jsp").forward(request, response);
    }
}