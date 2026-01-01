package com.codehills.cli.model;

// This is a CAR MODEL FOR CLI which is completely separate from the backend's Car model.

public class Car {
    
    private Long id;
    private String brand;
    private String model;
    private Integer year;
    
    // Default constructor for Gson
    public Car() {}
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }
    
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    
    public Integer getYear() { return year; }
    public void setYear(Integer year) { this.year = year; }
    
    @Override
    public String toString() {
        return String.format("Car #%d: %s %s (%d)", id, brand, model, year);
    }
}