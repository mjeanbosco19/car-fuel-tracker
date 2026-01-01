package com.codehills.cartracker.model;

import java.time.LocalDateTime;

/**
 * This a FUEL ENTRY MODEL which represents a single fuel fill-up record.
 * A fuel entry has a liters, price, odometer and a timestamp.
 * 
 * The odometer is crucial for calculating fuel consumption.
 */
public class FuelEntry {

    private Long id;
  
    private Double liters;
   
    private Double price;
    
    private Integer odometer;
    
    private LocalDateTime timestamp;
    
    public FuelEntry() {
        this.timestamp = LocalDateTime.now();
    }
    
    public FuelEntry(Double liters, Double price, Integer odometer) {
        this.liters = liters;
        this.price = price;
        this.odometer = odometer;
        this.timestamp = LocalDateTime.now();
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
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
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
    
    @Override
    public String toString() {
        return "FuelEntry{" +
                "id=" + id +
                ", liters=" + liters +
                ", price=" + price +
                ", odometer=" + odometer +
                ", timestamp=" + timestamp +
                '}';
    }
}