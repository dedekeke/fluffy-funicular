package com.chaunhat.fluffyfunicular.controller.product;

import com.chaunhat.fluffyfunicular.model.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "UpdateProduct", value = "/updateProduct")
public class UpdateProduct extends HttpServlet {

    private final ProductController controller = new ProductController(); // Assuming you have a ProductController

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve the product details from the form
        String productIdString = request.getParameter("id");
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String priceString = request.getParameter("price");
        String image = request.getParameter("image");
        String type = request.getParameter("type");
        String brand = request.getParameter("brand");

        if (productIdString != null && !productIdString.isEmpty()) {
            try {
                int productId = Integer.parseInt(productIdString);
                double price = Double.parseDouble(priceString);

                Product updatedProduct = new Product(productId, name, description, price, image, type, brand);

                boolean isUpdated = controller.updateProduct(updatedProduct);

                if (isUpdated) {
                    request.setAttribute("infoMessage", "Product successfully updated.");
                } else {
                    request.setAttribute("errorMessage", "Failed to update product.");
                }
            } catch (SQLException | NumberFormatException e) {
                System.out.println(e.getMessage());
                request.setAttribute("errorMessage", "Error occurred while updating product.");
            }
        } else {
            request.setAttribute("errorMessage", "Invalid product data.");
        }

        // Redirect back to the product management page or show the form again
        response.sendRedirect(request.getContextPath() + "/dashboard");
    }
}