package com.codehills.cli.model;

// This is a FuelEntry for CLI which is completely separate from the backend's FuelEntry model.

public class FuelEntry {
    
    private Long id;
    private Double liters;
    private Double price;
    private Integer odometer;
    private String timestamp;
    
    public FuelEntry() {}
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Double getLiters() { return liters; }
    public void setLiters(Double liters) { this.liters = liters; }
    
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
    
    public Integer getOdometer() { return odometer; }
    public void setOdometer(Integer odometer) { this.odometer = odometer; }
    
    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
    
    @Override
    public String toString() {
        return String.format(
            "ID: %d%n" +
            "Liters: %.1f%n" +
            "Price: %.2f%n" +
            "Odometer: %d km",
            id, liters, price, odometer
        );
    }
}