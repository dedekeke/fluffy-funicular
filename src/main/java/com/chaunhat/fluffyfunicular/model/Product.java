package com.chaunhat.fluffyfunicular.model;

public record Product(int id, String name, String description, int quantity, double price, byte[] image) {
}
