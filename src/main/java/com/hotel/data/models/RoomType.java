package com.hotel.data.models;

public enum                                                                                                                                                                                                                                                                                                                        RoomType {
    SINGLE(30000),
    DOUBLE(50000),
    SUITE(100000);
    private final double pricePerNight;

    RoomType(double pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }
}
