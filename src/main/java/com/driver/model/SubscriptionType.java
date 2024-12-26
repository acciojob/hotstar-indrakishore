package com.driver.model;

public enum SubscriptionType {
    BASIC(500),
    STANDARD(200),
    ELITE(300);

    private final int price;

    SubscriptionType(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }
}
