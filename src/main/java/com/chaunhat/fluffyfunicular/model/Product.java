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
}
