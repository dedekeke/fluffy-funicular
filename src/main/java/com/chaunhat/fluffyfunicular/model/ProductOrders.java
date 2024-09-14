package com.chaunhat.fluffyfunicular.model;

public record ProductOrders(
    int orderId,
    int productId,
    int amountProduct,
    String nameProduct
) {}
