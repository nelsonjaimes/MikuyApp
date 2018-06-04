package com.restaurant.project.mikuyapp.menutoday.model;

public class Category {
    private final int image;
    private final String title;

    public Category(int image, String title) {
        this.image = image;
        this.title = title;
    }

    public int getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }
}
