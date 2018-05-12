package com.restaurant.project.mikuyapp.domain.model.directions;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DirectionsApiResponse {
    @SerializedName("routes")
    private List<RouteEntity> routeEntityList;
    private String status;


    public List<RouteEntity> getRouteEntityList() {
        return routeEntityList;
    }

    public String getPoints() {
        return routeEntityList.get(0).getOverriewPolyEntity().getPoints();
    }

    public void setRouteEntityList(List<RouteEntity> routeEntityList) {
        this.routeEntityList = routeEntityList;
    }

    public String getTime() {
        return routeEntityList.get(0).getLegsEntityList().get(0).getDuration().getText();
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

    public void setStatus(String status) {
        this.status = status;
    }

    public class RouteEntity {
        @SerializedName("legs")
        private List<LegsEntity> legsEntityList;
        @SerializedName("overview_polyline")
        private OverriewPolyEntity overriewPolyEntity;

        public List<LegsEntity> getLegsEntityList() {
            return legsEntityList;
        }

        public void setLegsEntityList(List<LegsEntity> legsEntityList) {
            this.legsEntityList = legsEntityList;
        }

        OverriewPolyEntity getOverriewPolyEntity() {
            return overriewPolyEntity;
        }

        public void setOverriewPolyEntity(OverriewPolyEntity overriewPolyEntity) {
            this.overriewPolyEntity = overriewPolyEntity;
        }

        class OverriewPolyEntity {
            private String points;

            String getPoints() {
                return points;
            }

            public void setPoints(String points) {
                this.points = points;
            }
        }

        class LegsEntity {
            private Distance distance;
            private Duration duration;
            @SerializedName("end_address")
            private String endAddress;
            @SerializedName("start_address")
            private String startAddress;


            public Distance getDistance() {
                return distance;
            }

            public void setDistance(Distance distance) {
                this.distance = distance;
            }

            public Duration getDuration() {
                return duration;
            }

            public void setDuration(Duration duration) {
                this.duration = duration;
            }

            public String getEndAddress() {
                return endAddress;
            }

            public void setEndAddress(String endAddress) {
                this.endAddress = endAddress;
            }

            public String getStartAddress() {
                return startAddress;
            }

            public void setStartAddress(String startAddress) {
                this.startAddress = startAddress;
            }

            class Distance {
                private String text;
                private int value;

                public String getText() {
                    return text;
                }

                public void setText(String text) {
                    this.text = text;
                }

                public int getValue() {
                    return value;
                }

                public void setValue(int value) {
                    this.value = value;
                }
            }

            class Duration {
                private String text;
                private int value;

                public String getText() {
                    return text;
                }

                public void setText(String text) {
                    this.text = text;
                }

                public int getValue() {
                    return value;
                }

                public void setValue(int value) {
                    this.value = value;
                }
            }
        }
    }
}
