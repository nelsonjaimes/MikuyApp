package com.restaurant.project.mikuyapp.menutoday.model;

public class Plate {
    private String code;
    private String name;
    private float price;
    private String category;
    private int acount;

    private boolean isAggregate;
    private boolean isFirstAggregate;

    //*Este es....
    public Plate(String code,String name, float price, String category) {
        this.name = name;
        this.price = price;
        this.code =  code;
        this.category = category;
        this.isAggregate = false;
        this.isFirstAggregate=false;
        this.acount = 1;
    }

    public float getAmount() {
        return price * acount;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
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

    public void setCategory(String category) {
        this.category = category;
    }

}
