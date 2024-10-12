package com.chaunhat.fluffyfunicular.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private int id;
    private String name;
    private String description;
    private double price;
    private String src;
    private String type;
    private String brand;
    private int number;

    public Product(String name, String description, double price, String src, String type, String brand) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.src = src;
        this.type = type;
        this.brand = brand;
    }

    public Product(int id, String name, String description, double price, String src, String type, String brand) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.src = src;
        this.type = type;
        this.brand = brand;
    }
}
