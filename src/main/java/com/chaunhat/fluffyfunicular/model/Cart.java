package com.chaunhat.fluffyfunicular.model;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<Product> items;
    public Cart() {
        items = new ArrayList<>();
    }

    public void add(Product product) {
        for (Product p : items) {
            if (product.getId() == p.getId()) {
                product.setNumber(p.getNumber() + 1);
            }
        }
        items.add(product);
    }

    public boolean remove(int id) {
        for (Product p : items) {
            if (p.getId() == id) {
                items.remove(p);
                return true;
            }
        }
        return false;
    }

    public double getAmount() {
        double a = 0;
        for (Product p : items) {
            a += p.getPrice() * p.getNumber();
        }
        return Math.round(a * 100.0) / 100.0;
    }

    public List<Product> getItems() {
        return items;
    }
}
