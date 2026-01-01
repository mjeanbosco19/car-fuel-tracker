package com.codehills.cartracker.dto.response;

import com.codehills.cartracker.model.Car;

import java.time.LocalDateTime;

public class CarResponse {

    private Long id;
    private String brand;
    private String model;
    private Integer year;
    private LocalDateTime createdAt;
    private int fuelEntriesCount;

    public CarResponse() {
    }

    public CarResponse(Long id, String brand, String model, Integer year,
                       LocalDateTime createdAt, int fuelEntriesCount) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.createdAt = createdAt;
        this.fuelEntriesCount = fuelEntriesCount;
    }

    public static CarResponse fromCar(Car car) {
        return new CarResponse(
            car.getId(),
            car.getBrand(),
            car.getModel(),
            car.getYear(),
            LocalDateTime.now(),
            car.getFuelEntries() != null ? car.getFuelEntries().size() : 0
        );
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public int getFuelEntriesCount() {
        return fuelEntriesCount;
    }

    public void setFuelEntriesCount(int fuelEntriesCount) {
        this.fuelEntriesCount = fuelEntriesCount;
    }
}
