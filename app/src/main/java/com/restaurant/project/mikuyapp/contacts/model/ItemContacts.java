package com.restaurant.project.mikuyapp.contacts.model;

public class ItemContacts {
    private final int image;
    private final String description;

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
