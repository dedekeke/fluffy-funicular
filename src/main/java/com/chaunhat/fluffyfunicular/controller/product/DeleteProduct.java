package com.chaunhat.fluffyfunicular.controller.product;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "DeleteProduct", value = "/deleteProduct")
public class DeleteProduct extends HttpServlet {
    private final ProductController controller = new ProductController();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productIdString = request.getParameter("productId");

        if (productIdString != null && !productIdString.isEmpty()) {
            int productId = Integer.parseInt(productIdString); // Convert product ID to int

            try {
                boolean isDeleted = controller.deleteProduct(productId);
                if (isDeleted) {
                    request.setAttribute("infoMessage", "Product successfully deleted.");
                } else {
                    request.setAttribute("errorMessage", "Failed to delete product.");
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                request.setAttribute("errorMessage", "Error occurred while deleting product.");
            }
        } else {
            request.setAttribute("errorMessage", "Invalid product ID.");
        }
        response.sendRedirect(request.getContextPath() + "/dashboard");
    }
}