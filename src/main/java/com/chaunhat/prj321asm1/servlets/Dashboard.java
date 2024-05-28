package com.chaunhat.prj321asm1.servlets;

import java.io.*;
import java.util.List;
import java.util.UUID;

import com.chaunhat.prj321asm1.model.Product;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "DashboardServlet", value = "/dashboard")
public class Dashboard extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the ServletContext object
        ServletContext context = getServletContext();

        // Get the value of the "isLoggedIn" context parameter
        Boolean isLoggedIn = (Boolean) context.getAttribute("isLoggedIn");
        // Check if the user is logged in
        if (isLoggedIn != null && isLoggedIn) {
            request.setAttribute("products", getServletContext().getAttribute("products"));
            getServletContext().getRequestDispatcher("/dashboard.jsp").forward(request, response);
        } else {
            response.sendRedirect("/login");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Check if the request is for product deletion
        if (request.getParameter("productId") != null) {
            String productIdStr = request.getParameter("productId");

            if (!isValidUUID(productIdStr)) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid product ID format");
                return;
            }

            UUID productId = UUID.fromString(productIdStr);
            List<Product> products = (List<Product>) getServletContext().getAttribute("products");

            // Call your logic to delete the product by ID
            boolean deleted = Product.deleteProduct(products,productId);

            if (deleted) {
                request.setAttribute("infoMessage", "Product deleted!");
                request.getRequestDispatcher("/dashboard.jsp").forward(request, response);
            } else {
                request.setAttribute("errorMessage", "Failed to delete product");
                request.getRequestDispatcher("/dashboard.jsp").forward(request, response);
            }
        }
    }

    // Utility method to check if a string is a valid UUID format
    private static boolean isValidUUID(String uuid) {
        if(uuid == null) return false;
        try {
            UUID fromStringUUID = UUID.fromString(uuid);
            String toStringUUID = fromStringUUID.toString();
            return toStringUUID.equals(uuid);
        } catch(IllegalArgumentException e) {
            return false;
        }
    }


}