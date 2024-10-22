package com.chaunhat.fluffyfunicular.dao;

import com.chaunhat.fluffyfunicular.model.Product;
import com.chaunhat.fluffyfunicular.util.DatabaseUtility;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductDAO {
    @Autowired
    private DatabaseUtility databaseUtility;
    public Product getProductById(int id) throws SQLException {
        String sql = "SELECT * FROM \"Products\" WHERE product_id = ?";
        try (Connection conn = databaseUtility.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            Product rs = getProduct(stmt);
            if (rs != null) return rs;
        }
        return null;
    }

    public List<Product> searchProduct(String searchString) throws SQLException {
        String sql = "SELECT * FROM \"Products\" WHERE product_name ILIKE ?";
        List<Product> products = new ArrayList<>();

        try (Connection conn = databaseUtility.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + searchString + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Product product = new Product(
                            rs.getInt("product_id"),
                            rs.getString("product_name"),
                            rs.getString("product_des"),
                            rs.getDouble("product_price"),
                            rs.getString("product_img_source"),
                            rs.getString("product_type"),
                            rs.getString("product_brand")
                    );
                    products.add(product);
                }
            }
        }
        return products;
    }

    public List<Product> getAllProducts() throws SQLException {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM \"Products\" order by product_id";
        try (Connection conn = databaseUtility.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                products.add(new Product(
                    rs.getInt("product_id"),
                    rs.getString("product_name"),
                    rs.getString("product_des"),
                    rs.getDouble("product_price"),
                    rs.getString("product_img_source"),
                    rs.getString("product_type"),
                    rs.getString("product_brand")
                ));
            }
        }
        return products;
    }

    public int insertProduct(Product product) throws SQLException {
        String sql = "INSERT INTO \"Products\" (product_name, product_des, product_price, product_img_source, product_type, product_brand) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = databaseUtility.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, product.getName());
            stmt.setString(2, product.getDescription());
            stmt.setDouble(3, product.getPrice());
            stmt.setString(4, product.getSrc());
            stmt.setString(5, product.getType());
            stmt.setString(6, product.getBrand());

            // Execute the insert and retrieve the generated keys
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1);
                    }
                }
            }
            return -1;
        }
    }

    public boolean updateProduct(Product product) throws SQLException {
        String sql = "UPDATE \"Products\" SET product_name = ?, product_des = ?, product_price = ?, product_img_source = ?, product_type = ?, product_brand = ? WHERE product_id = ?";
        try (Connection conn = databaseUtility.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Set the parameters for the update query
            stmt.setString(1, product.getName());
            stmt.setString(2, product.getDescription());
            stmt.setDouble(3, product.getPrice());
            stmt.setString(4, product.getSrc());
            stmt.setString(5, product.getType());
            stmt.setString(6, product.getBrand());
            stmt.setInt(7, product.getId());

            // Execute the update and check if a row was updated
            int rowsAffected = stmt.executeUpdate();

            // Return true if at least one row was updated, otherwise false
            return rowsAffected > 0;
        }
    }


    public boolean deleteProduct(int productId) {
        String sql = "DELETE FROM \"Products\" WHERE product_id = ?";
        try (Connection conn = databaseUtility.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, productId);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting product: " + e.getMessage());
            return false;
        }
    }

    @Nullable
    private Product getProduct(PreparedStatement stmt) throws SQLException {
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return new Product(
                        rs.getInt("product_id"),
                        rs.getString("product_name"),
                        rs.getString("product_des"),
                        rs.getDouble("product_price"),
                        rs.getString("product_img_source"),
                        rs.getString("product_type"),
                        rs.getString("product_brand")
                );
            }
        }
        return null;
    }
}
