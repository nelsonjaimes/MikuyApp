package com.restaurant.project.mikuyapp.home.sidebar.adapter;

 class Item {
    private int image;
    private String option;
    private boolean isSelect;
    private final int type;
    public static final int HEADER_TYPE = 1;
    public static final int NORMAL_TYPE = 2;

    Item() {
        this.type = HEADER_TYPE;
        this.isSelect = false;
    }

    Item(int image, String option) {
        this.image = image;
        this.option = option;
        this.isSelect = false;
        this.type = NORMAL_TYPE;
    }

    public int getType() {
        return type;
    }

    public int getImage() {
        return image;
    }

    public String getOption() {
        return option;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public boolean isSelect() {
        return isSelect;
    }
}

