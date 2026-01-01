package com.codehills.cartracker.model;

import java.util.ArrayList;
import java.util.List;

/**
 * This a CAR MODEL which represents a car in our fuel tracking system.
 * A car has a brand, model, year and a list of fuel entries.
 */
public class Car {

    private Long id;

    private String brand;

    private String model;

    private Integer year;

    private List<FuelEntry> fuelEntries = new ArrayList<>();
    
   //Jackson (JSON library) will be used for deserialization.
    public Car() {
    }
    
    public Car(String brand, String model, Integer year) {
        this.brand = brand;
        this.model = model;
        this.year = year;
    }
        
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getBrand() {
        return brand;
    }
    
    public void setBrand(String brand) {
        this.brand = brand;
    }
    
    public String getModel() {
        return model;
    }
    
    public void setModel(String model) {
        this.model = model;
    }
    
    public Integer getYear() {
        return year;
    }
    
    public void setYear(Integer year) {
        this.year = year;
    }
    
    public List<FuelEntry> getFuelEntries() {
        return fuelEntries;
    }
    
    public void setFuelEntries(List<FuelEntry> fuelEntries) {
        this.fuelEntries = fuelEntries;
    }

    public void addFuelEntry(FuelEntry entry) {
        this.fuelEntries.add(entry);
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", year=" + year +
                ", fuelEntriesCount=" + fuelEntries.size() +
                '}';
    }
}