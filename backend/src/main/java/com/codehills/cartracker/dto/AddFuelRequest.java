package com.codehills.cartracker.dto;

public class AddFuelRequest {
    
    private Double liters;
    
    private Double price;
    
    private Integer odometer;
    
    public AddFuelRequest() {
    }
    
    public AddFuelRequest(Double liters, Double price, Integer odometer) {
        this.liters = liters;
        this.price = price;
        this.odometer = odometer;
    }
    
    public Double getLiters() {
        return liters;
    }
    
    public void setLiters(Double liters) {
        this.liters = liters;
    }
    
    public Double getPrice() {
        return price;
    }
    
    public void setPrice(Double price) {
        this.price = price;
    }
    
    public Integer getOdometer() {
        return odometer;
    }
    
    public void setOdometer(Integer odometer) {
        this.odometer = odometer;
    }
    
    @Override
    public String toString() {
        return "AddFuelRequest{" +
                "liters=" + liters +
                ", price=" + price +
                ", odometer=" + odometer +
                '}';
    }
}