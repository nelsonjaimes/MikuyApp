package com.restaurant.project.restaurantapp.menutoday.model;

public class Plate {
    private String name;
    private double price;
    private boolean isAggregate;
    private int accountant;
    private String category;

    public Plate(String name, double price, String category) {
        this.name = name;
        this.price = price;
        this.isAggregate = false;
        this.category = category;
    }

    public int getAccountant() {
        return accountant;
    }

    public void setAccountant(int accountant) {
        this.accountant = accountant;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isAggregate() {
        return isAggregate;
    }

    public void setAggregate(boolean aggregate) {
        isAggregate = aggregate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

}
