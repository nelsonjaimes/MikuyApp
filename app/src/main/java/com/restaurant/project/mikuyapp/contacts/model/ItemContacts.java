package com.restaurant.project.mikuyapp.contacts.model;

public class ItemContacts {
    private int image;
    private String description;

    public ItemContacts(int image, String description) {
        this.image = image;
        this.description = description;
    }

    public int getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }
}
