package com.chaunhat.fluffyfunicular.model;

import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Data
public class Orders {
    private int orderId;
    private float price;
    private int status;
    private Date orderDate;
    private String address;
    private String phoneNumber;
    private List<ProductOrders> productOrdersList;
    private String userEmail;
    private Date receivedDate;
    private String discount;

    public Orders(
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
    ) {
        this.orderId = orderId;
        this.price = price;
        this.status = status;
        this.orderDate = orderDate;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.productOrdersList = productOrdersList;
        this.userEmail = userEmail;
        this.receivedDate = receivedDate;
        this.discount = discount;
    }

    public Orders(String userEmail, int status, String discount, String address, String phoneNumber, Date receivedDate) {
        this(0, 0, status, null, address, phoneNumber, List.of(), userEmail, receivedDate, discount);
    }

    public Orders(int orderId, float price, int status, Date orderDate, String address, String phoneNumber, List<ProductOrders> productOrdersList, String userEmail, Date receivedDate) {
        this(orderId, price, status, orderDate, address, phoneNumber, productOrdersList, userEmail, receivedDate, "");
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Orders) obj;
        return this.orderId == that.orderId &&
                Float.floatToIntBits(this.price) == Float.floatToIntBits(that.price) &&
                this.status == that.status &&
                Objects.equals(this.orderDate, that.orderDate) &&
                Objects.equals(this.address, that.address) &&
                Objects.equals(this.phoneNumber, that.phoneNumber) &&
                Objects.equals(this.productOrdersList, that.productOrdersList) &&
                Objects.equals(this.userEmail, that.userEmail) &&
                Objects.equals(this.receivedDate, that.receivedDate) &&
                Objects.equals(this.discount, that.discount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, price, status, orderDate, address, phoneNumber, productOrdersList, userEmail, receivedDate, discount);
    }

    @Override
    public String toString() {
        return "Orders[" +
                "orderId=" + orderId + ", " +
                "price=" + price + ", " +
                "status=" + status + ", " +
                "orderDate=" + orderDate + ", " +
                "address=" + address + ", " +
                "phoneNumber=" + phoneNumber + ", " +
                "productOrdersList=" + productOrdersList + ", " +
                "userEmail=" + userEmail + ", " +
                "receivedDate=" + receivedDate + ", " +
                "discount=" + discount + ']';
    }

}
