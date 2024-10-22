package com.chaunhat.fluffyfunicular.controller.product;

import com.chaunhat.fluffyfunicular.model.Product;
import com.chaunhat.fluffyfunicular.service.ProductService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "SearchProduct", value = "/search")
public class SearchProduct extends HttpServlet {
    @Autowired
    private ProductService productService;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter("searchString");
        if (query != null && !query.trim().isEmpty()) {
            List<Product> searchResults;
            try {
                searchResults = productService.searchProducts(query.trim());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            request.setAttribute("results", searchResults);
        } else {
            request.setAttribute("results", new ArrayList<>()); // Empty list for no results
        }

        request.getRequestDispatcher("/search_product.jsp").forward(request, response);
    }
}
