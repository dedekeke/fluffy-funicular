package com.chaunhat.prj321asm1.servlets;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.chaunhat.prj321asm1.model.Product;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "LandingPage", value = "/")
public class LandingPage extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve the list of products from the repository
        List<Product> products = new ArrayList<>();
        // Change basePath
        // For e.g: C/Project/src/images
        String basePath = "/Users/bkinm1/Desktop/your_map/temp/Funix/PRJ321/fluffy-funicular/src/main/webapp/images/";

        products.add(Product.createProduct("Fan", "Silent Cooling for your PC", 10, Files.readAllBytes(Paths.get(basePath + "fan.jpeg"))));
        products.add(Product.createProduct("Headphone", "Crystal Clear Audio Experience", 20, Files.readAllBytes(Paths.get(basePath + "headphone.jpeg"))));
        products.add(Product.createProduct("Keyboard", "Ergonomic Design for Comfort", 30, Files.readAllBytes(Paths.get(basePath + "keyboard.jpeg"))));
        products.add(Product.createProduct("Laptop", "Powerful & Portable Performance", 40, Files.readAllBytes(Paths.get(basePath + "laptop.jpeg"))));
        products.add(Product.createProduct("Monitor", "Vivid Display for Work & Play", 50, Files.readAllBytes(Paths.get(basePath + "monitor.jpeg"))));
        products.add(Product.createProduct("Mouse", "Wireless Freedom for Productivity", 60, Files.readAllBytes(Paths.get(basePath + "mouse.jpeg"))));
        products.add(Product.createProduct("Speaker", "Immersive Sound for Movies & Music", 70, Files.readAllBytes(Paths.get(basePath + "speaker.jpeg"))));
        products.add(Product.createProduct("VGA", "Display Adapter for Connecting Monitors", 80, Files.readAllBytes(Paths.get(basePath + "vga.jpeg"))));


        ServletContext context = getServletContext();
        // Add the list of products to the request attribute
        context.setAttribute("products", products);
        request.setAttribute("products", products);

        // Forward the request to the landing.jsp
        context.getRequestDispatcher("/landing.jsp").forward(request, response);
    }
}