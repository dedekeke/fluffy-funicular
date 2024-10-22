package com.chaunhat.fluffyfunicular.controller;

import com.chaunhat.fluffyfunicular.service.ProductService;
import com.chaunhat.fluffyfunicular.model.Product;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "DashboardServlet", value = "/dashboard")
public class Dashboard extends HttpServlet {
    @Autowired
    private ProductService controller;
    protected String name;
    protected String description;
    protected double price;
    protected String img_src;
    protected String type;
    protected String brand;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Add products
        name = request.getParameter("name");
        description = request.getParameter("description");
        price = Double.parseDouble(request.getParameter("price"));
        img_src = request.getParameter("image");
        type = request.getParameter("type");
        brand = request.getParameter("brand");

        Product newProduct = new Product(name, description, price, img_src, type, brand);
        try {
            int result = controller.createProduct(newProduct);
            if (result != -1) request.setAttribute("infoMessage", "Product created");
            else request.setAttribute("errorMessage", "Something went wrong!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        response.sendRedirect(request.getContextPath() + "/dashboard");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        ServletContext context = getServletContext();
        Boolean isLoggedIn = (Boolean) context.getAttribute("isLoggedIn");
        Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");

        if (isLoggedIn != null && isLoggedIn && isAdmin) {
            try {
                List<Product> products = controller.getAllProducts();
                request.setAttribute("products", products);
                request.getRequestDispatcher("/dashboard.jsp").forward(request, response);
            } catch (Exception e) {
                throw new ServletException("Error retrieving products", e);
            }
        } else {
            response.sendRedirect("/login");
        }
    }
}
