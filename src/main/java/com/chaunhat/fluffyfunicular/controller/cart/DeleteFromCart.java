package com.chaunhat.fluffyfunicular.controller.cart;

import com.chaunhat.fluffyfunicular.model.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/deleteFromCart")
public class DeleteFromCart extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        List<Product> cartItems = (List<Product>) session.getAttribute("cart");
        int productId = Integer.parseInt(request.getParameter("productId"));

        if (cartItems != null) {
            // Remove the product from the cart
            cartItems.removeIf(item -> item.getId() == productId);
        }
        session.setAttribute("cart", cartItems);

        response.sendRedirect(request.getContextPath() + "/cart");
    }
}
