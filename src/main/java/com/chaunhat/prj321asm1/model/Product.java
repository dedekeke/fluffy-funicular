package com.chaunhat.prj321asm1.model;

import java.util.List;
import java.util.UUID;

public record Product(UUID id, String name, String description, int quantity, byte[] image) {
    public static Product createProduct(String name, String description, int quantity, byte[] image) {
        UUID id = UUID.randomUUID();
        return new Product(id, name, description, quantity, image);
    }

    private static int findProductIndex(List<Product> products, UUID id) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).id().equals(id)) {
                return i;
            }
        }
        return -1;
    }

    public static boolean deleteProduct(List<Product> products, UUID id) {
        int productIndex = findProductIndex(products, id);
        if (productIndex != -1) {
            products.remove(productIndex);
            return true;
        } else {
            return false;
        }
    }
}
