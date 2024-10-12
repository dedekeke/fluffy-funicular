package com.chaunhat.fluffyfunicular.controller;

import com.chaunhat.fluffyfunicular.controller.product.ProductController;
import com.chaunhat.fluffyfunicular.model.Product;
import com.chaunhat.fluffyfunicular.dao.ProductDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "LandingPage", value = "/")
public class LandingPage extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Product> products = ProductController.getAllProducts();
            request.setAttribute("products", products);
            request.getRequestDispatcher("/landing.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Error retrieving products", e);
        }
    }
}