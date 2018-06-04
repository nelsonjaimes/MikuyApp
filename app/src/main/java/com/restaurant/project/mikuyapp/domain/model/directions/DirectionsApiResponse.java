package com.restaurant.project.mikuyapp.domain.model.directions;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DirectionsApiResponse {
    @SerializedName("routes")
    private final List<RouteEntity> routeEntityList;
    private final String status;

    public DirectionsApiResponse(List<RouteEntity> routeEntityList, String status) {
        this.routeEntityList = routeEntityList;
        this.status = status;
    }

    public String getPoints() {
        return routeEntityList.get(0).overriewPolyEntity.points;
    }

    public String getTime() {
        return routeEntityList.get(0).legsEntityList.get(0).duration.text;
    }

    public String getStartUbication() {
        return routeEntityList.get(0).legsEntityList.get(0).startAddress;
    }

    public String getEndUbication() {
        return routeEntityList.get(0).legsEntityList.get(0).endAddress;
    }

    public String getStatus() {
        return status;
    }

     class RouteEntity {
        @SerializedName("legs")
        List<LegsEntity> legsEntityList;
        @SerializedName("overview_polyline")
        OverriewPolyEntity overriewPolyEntity;

        class OverriewPolyEntity {
            String points;
        }

        class LegsEntity {
            Duration duration;
            @SerializedName("end_address")
            String endAddress;
            @SerializedName("start_address")
            String startAddress;

            class Duration {
                String text;
            }
        }
    }
}
