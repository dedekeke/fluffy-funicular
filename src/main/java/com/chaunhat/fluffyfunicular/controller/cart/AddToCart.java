package com.chaunhat.fluffyfunicular.controller.cart;

import com.chaunhat.fluffyfunicular.controller.product.ProductController;
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

@WebServlet(name = "AddToCart", value = "/addToCart")
public class AddToCart extends HttpServlet {
    private ProductController controller = new ProductController();

    // TODO: reuse this method to update cart
    // TODO: add delete cart
    // TODO: responsive + font
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        List<Product> cartItems = (List<Product>) session.getAttribute("cart");

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

        response.sendRedirect(request.getContextPath() + "/productDetail?id=" + productId);
    }
}