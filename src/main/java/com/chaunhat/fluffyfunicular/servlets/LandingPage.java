package com.chaunhat.fluffyfunicular.servlets;

import com.chaunhat.fluffyfunicular.model.Product;
import com.chaunhat.fluffyfunicular.service.ProductService;
import com.chaunhat.fluffyfunicular.util.DataSeeder;
import com.chaunhat.fluffyfunicular.util.DatabaseUtility;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet(name = "LandingPage", value = "/")
public class LandingPage extends HttpServlet {
    private final ProductService productService = new ProductService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Product> products = productService.getAllProducts();
            request.setAttribute("products", products);
            request.getRequestDispatcher("/landing.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Error retrieving products", e);
        }
    }
}