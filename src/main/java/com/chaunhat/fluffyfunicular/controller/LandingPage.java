package com.chaunhat.fluffyfunicular.controller;

import com.chaunhat.fluffyfunicular.model.Product;
import com.chaunhat.fluffyfunicular.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class LandingPage {

    @Autowired
    private ProductService productService;

    @GetMapping
    public String showLandingPage(Model model) {
        try {
            List<Product> products = productService.getAllProducts();
            model.addAttribute("products", products);  // Add products to the model for the view
            return "landing";  // Return the logical view name (assumes a landing.jsp page)
        } catch (Exception e) {
            // Handle exceptions and potentially add an error message to the model
            model.addAttribute("errorMessage", "Error retrieving products");
            System.out.println(e.getMessage());
            return null;
        }
    }
}
