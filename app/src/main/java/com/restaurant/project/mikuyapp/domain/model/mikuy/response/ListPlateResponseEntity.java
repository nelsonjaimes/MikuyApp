package com.restaurant.project.mikuyapp.domain.model.mikuy.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListPlateResponseEntity {

    private int status;
    @SerializedName("platelist")
    private List<PlateResponseEntity> plateList;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<PlateResponseEntity> getPlateList() {
        return plateList;
    }

    public void setPlateList(List<PlateResponseEntity> plateList) {
        this.plateList = plateList;
    }

    public class PlateResponseEntity {
        private String code;
        private String name;
        private float price;
        private String category;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
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

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }
    /*
    "code": "A001",
        "name": "Lomo Saltado",
        "price": "10.5",
        "category": "carta"
     */
    }
}
