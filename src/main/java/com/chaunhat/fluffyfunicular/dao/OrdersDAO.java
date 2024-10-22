package com.chaunhat.fluffyfunicular.dao;

import com.chaunhat.fluffyfunicular.model.Cart;
import com.chaunhat.fluffyfunicular.model.Orders;
import com.chaunhat.fluffyfunicular.model.Product;
import com.chaunhat.fluffyfunicular.util.DatabaseUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class OrdersDAO {
    @Autowired
    private DatabaseUtility databaseUtility;
    public void insertOrder(Orders order, Cart cart) throws Exception {
        try {
            Connection conn = databaseUtility.getConnection();

            String sqlO = "INSERT INTO \"Orders\" (user_mail, order_status, order_date, order_discount_code, order_address) VALUES(?,?,?,?,?)";
            PreparedStatement stmt_Order = conn.prepareStatement(sqlO,  PreparedStatement.RETURN_GENERATED_KEYS);
            stmt_Order.setString(1,order.getUserEmail());
            stmt_Order.setInt(2,order.getStatus());
            stmt_Order.setDate(3, (Date) order.getOrderDate());
            stmt_Order.setString(4,order.getDiscount());
            stmt_Order.setString(5,order.getAddress());
            stmt_Order.executeUpdate();
            ResultSet rs = stmt_Order.getGeneratedKeys();
            int orderID = 0;
            if (rs.next()) {
                orderID = rs.getInt(1);
            }

            String sqlC = "INSERT INTO \"Orders_detail\" (order_id, product_id,amount_product,price_product) VALUES(?,?,?,?)";
            PreparedStatement stmt_Cart = conn.prepareStatement(sqlC);
            for(Product product : cart.getItems()) {
                stmt_Cart.setInt(1,orderID);
                stmt_Cart.setInt(2,product.getId());
                stmt_Cart.setDouble(3,product.getNumber());
                stmt_Cart.setFloat(4, (float) product.getPrice());
                stmt_Cart.executeUpdate();
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
