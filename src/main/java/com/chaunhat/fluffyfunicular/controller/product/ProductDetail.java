package com.chaunhat.fluffyfunicular.controller.product;

import com.chaunhat.fluffyfunicular.model.Product;
import com.chaunhat.fluffyfunicular.service.ProductService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/productDetail")
public class ProductDetail extends HttpServlet {

    private ProductService controller = new ProductService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productIdStr = request.getParameter("id");
        int productId = 0;

        try {
            productId = Integer.parseInt(productIdStr);
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid product ID.");
            request.getRequestDispatcher("/productDetail.jsp").forward(request, response);
            return;
        }

        Product product;
        try {
            product = controller.getProductById(productId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (product == null) {
            request.setAttribute("errorMessage", "Something went wrong");
            return;
        }
        request.setAttribute("product", product);
        request.getRequestDispatcher("/productDetail.jsp").forward(request, response);
    }
}

