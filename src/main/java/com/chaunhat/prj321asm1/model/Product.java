package com.chaunhat.prj321asm1.model;

import java.util.UUID;

public record Product(UUID id, String name, String description, int quantity, byte[] image) {
    public static Product createProduct(String name, String description, int quantity, byte[] image) {
        UUID id = UUID.randomUUID();
        return new Product(id, name, description, quantity, image);
    }
}
