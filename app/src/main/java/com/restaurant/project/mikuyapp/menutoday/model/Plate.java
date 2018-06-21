package com.restaurant.project.mikuyapp.menutoday.model;

@SuppressWarnings("all")
public class Plate {
    private final String code;
    private final String name;
    private final float price;
    private final String category;
    private int acount;

    private boolean isAggregate;
    private boolean isFirstAggregate;

    //*Este es....
    public Plate(String code, String name, float price, String category) {
        this.name = name;
        this.code = code;
        this.price = price;
        this.category = category;
        this.isAggregate = false;
        this.isFirstAggregate=false;
        this.acount = 1;
    }

    public float getAmount() {
        return price * acount;
    }

    public int getAcount() {
        return acount;
    }

    public void setAcount(int acount) {
        this.acount = acount;
    }

    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
    }

    public boolean isAggregate() {
        return isAggregate;
    }

    public void setAggregate(boolean aggregate) {
        isAggregate = aggregate;
    }

    public boolean isFirstAggregate() {
        return isFirstAggregate;
    }

    public void setFirstAggregate(boolean firstAggregate) {
        isFirstAggregate = firstAggregate;
    }

    public String getCategory() {
        return category;
    }

}
