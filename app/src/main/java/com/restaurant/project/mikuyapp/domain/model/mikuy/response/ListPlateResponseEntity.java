package com.restaurant.project.mikuyapp.domain.model.mikuy.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListPlateResponseEntity {

    @SerializedName("platelist")
    private List<PlateResponseEntity> plateList;

    public List<PlateResponseEntity> getPlateList() {
        return plateList;
    }

    public class PlateResponseEntity {
        private String code;
        private String name;
        private float price;
        private String category;

        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }

        public float getPrice() {
            return price;
        }

        public String getCategory() {
            return category;
        }
        /*
    "code": "A001",
        "name": "Lomo Saltado",
        "price": "10.5",
        "category": "carta"
     */
    }
}
