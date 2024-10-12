package com.chaunhat.fluffyfunicular.controller.order;

import com.chaunhat.fluffyfunicular.model.Account;
import com.chaunhat.fluffyfunicular.model.Product;
import com.chaunhat.fluffyfunicular.util.DatabaseUtility;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.*;
import java.util.Date;
import java.util.List;

@WebServlet(name = "CreateOrder",value = "/createOrder")
public class CreateOrderServlet extends HttpServlet {
    private static final int status = 1;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        List<Product> cartItems = (List<Product>) session.getAttribute("cart");
        String userEmail = (String) session.getAttribute("email");
        Account user;

        if (cartItems == null || cartItems.isEmpty()) {
            request.setAttribute("errorMessage", "Your cart is empty.");
            request.getRequestDispatcher("/cart.jsp").forward(request, response);
            return;
        }


        try {
            user = Account.getAccountByEmail(userEmail);
            if (user == null || userEmail == null) {
                response.sendRedirect("/login");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        float totalPrice = 0;
        for (Product item : cartItems) {
            totalPrice += (float) (item.getPrice() * item.getNumber());
        }

        Date orderDate = new Date();
        int orderId = 0;

        try {
            Connection conn = DatabaseUtility.getConnection();
            PreparedStatement orderStmt = conn.prepareStatement(
                    "INSERT INTO \"Orders\" (user_mail, order_status, order_date, order_address) VALUES (?, ?, ?, ?) RETURNING order_id"
            );
            orderStmt.setString(1, userEmail);
            orderStmt.setInt(2, status);
            orderStmt.setDate(3, new java.sql.Date(orderDate.getTime()));
            orderStmt.setString(4, user.address());
            orderStmt.execute();

            // Get the generated order ID
            ResultSet rs = orderStmt.getResultSet();
            if (rs.next()) {
                orderId = rs.getInt(1);
            } else {
                throw new SQLException("Creating order failed, no ID obtained.");
            }

            PreparedStatement productOrderStmt = conn.prepareStatement(
                    "INSERT INTO \"Orders_detail\" (order_id, product_id, amount_product, price_product) VALUES (?, ?, ?, ?)"
            );
            for (Product item : cartItems) {
                productOrderStmt.setInt(1, orderId);
                productOrderStmt.setInt(2, item.getId());
                productOrderStmt.setInt(3, item.getNumber());
                productOrderStmt.setDouble(4, item.getPrice());
                productOrderStmt.executeUpdate();
            }

            orderStmt.close();
            productOrderStmt.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        session.setAttribute("cart", null);

        request.setAttribute("cartItems", cartItems);
        request.setAttribute("total", totalPrice);
        request.getRequestDispatcher("/orderConfirmation.jsp").forward(request, response);
    }
}
