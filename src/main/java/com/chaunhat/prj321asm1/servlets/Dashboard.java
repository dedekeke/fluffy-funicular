package com.chaunhat.prj321asm1.servlets;

import java.io.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "DashboardServlet", value = "/dashboard")
public class Dashboard extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("products", getServletContext().getAttribute("products"));
        getServletContext().getRequestDispatcher("/dashboard.jsp").forward(request, response);
    }
}