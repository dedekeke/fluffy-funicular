package com.chaunhat.fluffyfunicular.controller.product;

import com.chaunhat.fluffyfunicular.dao.ProductDAO;
import com.chaunhat.fluffyfunicular.model.Product;

import java.sql.SQLException;
import java.util.List;

public class ProductController {
    private static final ProductDAO productDAO = new ProductDAO();

    public static List<Product> getAllProducts() throws SQLException {
        return productDAO.getAllProducts();
    }

    public static List<Product> searchProducts(String name) throws SQLException {
        return productDAO.searchProduct(name);
    }

    public static int getPageCount(List<Product> products, int pageSize) {
        return (products.size() / pageSize) + (products.size() % pageSize == 0 ? 0 : 1);
    }

    public static List<Product> getProductsForPage(List<Product> products, int currentPage, int pageSize) {
        int start = (currentPage - 1) * pageSize;
        int end = Math.min(start + pageSize, products.size());
        return products.subList(start, end);
    }
}
