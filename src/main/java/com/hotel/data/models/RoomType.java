package com.hotel.data.models;

public enum                                                                                                                                                                                                                                                                                                                        RoomType {
    SINGLE(30000,20000,40000),
    DOUBLE(50000,40000,60000),
    SUITE(100000,90000,110000);
    private final double pricePerNight;
    private final double reducedPrice;
    private final double priceForFestivePeriod;

    RoomType(double pricePerNight,double reducedPrice,double priceForFestivePeriod) {
        this.pricePerNight = pricePerNight;
        this.reducedPrice = reducedPrice;
        this.priceForFestivePeriod =priceForFestivePeriod;
    }

    public double getPricePerNight() {

        return pricePerNight;
    }
    public double getReducedPrice() {
        return reducedPrice;
    }

    public double getPriceForFestivePeriod() {
        return priceForFestivePeriod;
    }
}
