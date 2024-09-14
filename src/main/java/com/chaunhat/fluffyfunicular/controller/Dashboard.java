package com.chaunhat.fluffyfunicular.controller;

import com.chaunhat.fluffyfunicular.model.Product;
import com.chaunhat.fluffyfunicular.dao.ProductDAO;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "DashboardServlet", value = "/dashboard")
public class Dashboard extends HttpServlet {
    private ProductDAO productDAO;

    public void init() {
        productDAO = new ProductDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (ServletFileUpload.isMultipartContent((javax.servlet.http.HttpServletRequest) request)) {
            try {
                Product product = handleFileUpload(request);
                if (product != null) {
                    productDAO.insertProduct(product);
                    request.setAttribute("infoMessage", "Product added successfully!");
                }
            } catch (Exception e) {
                request.setAttribute("errorMessage", "Failed to add product: " + e.getMessage());
            }
            doGet(request, response);
        }
    }

    private Product handleFileUpload(HttpServletRequest request) throws Exception {
        List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest((javax.servlet.http.HttpServletRequest) request);
        byte[] image = null;
        String name = "", description = "";
        int quantity = 0;
        double price = 0.0;

        for (FileItem item : multiparts) {
            if (!item.isFormField()) {
                image = item.get();
            } else {
                String fieldName = item.getFieldName();
                String fieldValue = item.getString();
                switch (fieldName) {
                    case "name":
                        name = fieldValue;
                        break;
                    case "description":
                        description = fieldValue;
                        break;
                    case "quantity":
                        quantity = Integer.parseInt(fieldValue);
                        break;
                    case "price":
                        price = Double.parseDouble(fieldValue);
                        break;
                }
            }
        }
        return null; //temp
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        ServletContext context = getServletContext();
        Boolean isLoggedIn = (Boolean) context.getAttribute("isLoggedIn");
        Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");

        if (isLoggedIn != null && isLoggedIn && isAdmin) {
            try {
                List<Product> products = productDAO.getAllProducts();  // Get all products from the database
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
