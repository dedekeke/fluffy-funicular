package com.chaunhat.fluffyfunicular.dao;

import com.chaunhat.fluffyfunicular.model.Product;
import com.chaunhat.fluffyfunicular.util.DatabaseUtility;
import org.jetbrains.annotations.Nullable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    public Product getProductById(int id) throws SQLException {
        String sql = "SELECT * FROM products WHERE id = ?";
        try (Connection conn = DatabaseUtility.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            Product rs = getProduct(stmt);
            if (rs != null) return rs;
        }
        return null;
    }

    public List<Product> searchProduct(String searchString) throws SQLException {
        String sql = "SELECT * FROM products WHERE products.product_name ILIKE ?";
        List<Product> products = new ArrayList<>();

        try (Connection conn = DatabaseUtility.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Concatenate wildcards with the search string
            stmt.setString(1, "%" + searchString + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Product product = new Product(
                            rs.getInt("product_id"),
                            rs.getString("product_name"),
                            rs.getString("product_des"),
                            rs.getDouble("product_price"),
                            rs.getString("product_img_src"),
                            rs.getString("product_type"),
                            rs.getString("product_brand"),
                            rs.getInt("number")
                    );
                    products.add(product);
                }
            }
        }
        return products;
    }

    public List<Product> getAllProducts() throws SQLException {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products";
        try (Connection conn = DatabaseUtility.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                products.add(new Product(
                    rs.getInt("product_id"),
                    rs.getString("product_name"),
                    rs.getString("product_des"),
                    rs.getDouble("product_price"),
                    rs.getString("product_img_src"),
                    rs.getString("product_type"),
                    rs.getString("product_brand"),
                    rs.getInt("number")
                ));
            }
        }
        return products;
    }

    public void insertProduct(Product product) throws SQLException {
        String sql = "INSERT INTO products (product_name, product_des, product_price, product_img_source, product_type, product_brand, number) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseUtility.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, product.getName());
            stmt.setString(2, product.getDescription());
            stmt.setDouble(4, product.getPrice());
            stmt.setString(5, product.getSrc());
            stmt.setString(6, product.getType());
            stmt.setString(7, product.getBrand());
            stmt.setInt(8, product.getNumber());
            stmt.executeUpdate();
        }
    }

    public boolean deleteProduct(int productId) {
        String sql = "DELETE FROM products WHERE product_id = ?";
        try (Connection conn = DatabaseUtility.getConnection();
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
                        rs.getString("product_img_src"),
                        rs.getString("product_type"),
                        rs.getString("product_brand"),
                        rs.getInt("number")
                );
            }
        }
        return null;
    }
}
