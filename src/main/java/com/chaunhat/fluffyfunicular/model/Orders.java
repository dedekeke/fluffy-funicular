package com.chaunhat.fluffyfunicular.model;

import java.util.Date;
import java.util.List;

public record Orders(
        int orderId,
        float price,
        int status,
        Date orderDate,
        String address,
        String phoneNumber,
        List<ProductOrders> productOrdersList,
        String userEmail,
        Date receivedDate,
        String discount
) {}
