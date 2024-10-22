package com.chaunhat.fluffyfunicular.controller.cart;

import com.chaunhat.fluffyfunicular.service.ProductService;
import com.chaunhat.fluffyfunicular.model.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@WebServlet(name = "AddToCart", value = "/addToCart")
public class AddToCart extends HttpServlet {
    private ProductService controller = new ProductService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        List<Product> cartItems = (List<Product>) session.getAttribute("cart");
        String action = request.getParameter("action");

        if (cartItems == null) {
            cartItems = new ArrayList<>();
        }

        int productId = Integer.parseInt(request.getParameter("productId"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        Product product;
        try {
            product = controller.getProductById(productId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (Objects.equals(action, "add")) {
            addProductToCart(product, productId, quantity, cartItems, session);
            response.sendRedirect(request.getContextPath() + "/productDetail?id=" + productId);
        }
        else if (Objects.equals(action, "update")) {
            updateProductAmount(product, productId, quantity, cartItems, session);
            response.sendRedirect(request.getContextPath() + "/cart");
        }

    }

    private void addProductToCart(Product product, int productId, int quantity, List<Product> cartItems, HttpSession session) {
        if (product != null) {
            boolean productExists = false;
            for (Product item : cartItems) {
                if (item.getId() == productId) {
                    item.setNumber(item.getNumber() + quantity);
                    productExists = true;
                    break;
                }
            }

            if (!productExists) {
                Product cartProduct = new Product(product.getId(), product.getName(), product.getDescription(), product.getPrice(), product.getSrc(), product.getType(), product.getBrand(), quantity);
                cartItems.add(cartProduct);
            }

            session.setAttribute("cart", cartItems);
        }
    }

    private void updateProductAmount(Product product, int productId, int quantity, List<Product> cartItems, HttpSession session) {
        if (product != null) {
            for (Product item : cartItems) {
                if (item.getId() == productId) {
                    item.setNumber(quantity);
                    break;
                }
            }

            session.setAttribute("cart", cartItems);
        }
    }
}