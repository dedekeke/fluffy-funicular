package com.chaunhat.fluffyfunicular.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DataSeeder {
    // TODO: set the path to the image to system env
    private static final String basePath = System.getenv("IMAGE_PATH");

    public static void seedProducts(Connection conn) throws IOException, SQLException {
        String sql = "INSERT INTO products (name, description, price, image) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            insertProduct(stmt, "Fan", "Silent Cooling for your PC", 10, "fan.jpeg");
            insertProduct(stmt, "Headphone", "Crystal Clear Audio Experience", 20, "headphone.jpeg");
            insertProduct(stmt, "Keyboard", "Ergonomic Design for Comfort", 30, "keyboard.jpeg");
            insertProduct(stmt, "Laptop", "Powerful & Portable Performance", 40, "laptop.jpeg");
            insertProduct(stmt, "Monitor", "Vivid Display for Work & Play", 50, "monitor.jpeg");
            insertProduct(stmt, "Mouse", "Wireless Freedom for Productivity", 60, "mouse.jpeg");
            insertProduct(stmt, "Speaker", "Immersive Sound for Movies & Music", 70, "speaker.jpeg");
            insertProduct(stmt, "VGA", "Display Adapter for Connecting Monitors", 80, "vga.jpeg");
        }
    }

    private static void insertProduct(PreparedStatement stmt, String name, String description, int price, String imagePath) throws IOException, SQLException {
        stmt.setString(1, name);
        stmt.setString(2, description);
        stmt.setInt(3, price);
        stmt.setBytes(4, Files.readAllBytes(Paths.get(basePath + imagePath)));
        stmt.executeUpdate();
    }
}
