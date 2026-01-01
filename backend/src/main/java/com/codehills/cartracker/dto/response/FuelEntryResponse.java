package com.codehills.cartracker.dto.response;

import com.codehills.cartracker.model.FuelEntry;

import java.time.LocalDateTime;

public class FuelEntryResponse {

    private Long id;
    private Double liters;
    private Double price;
    private Integer odometer;
    private LocalDateTime timestamp;

    public FuelEntryResponse() {
    }

    public FuelEntryResponse(Long id, Double liters, Double price,
                             Integer odometer, LocalDateTime timestamp) {
        this.id = id;
        this.liters = liters;
        this.price = price;
        this.odometer = odometer;
        this.timestamp = timestamp;
    }

    public static FuelEntryResponse fromFuelEntry(FuelEntry entry) {
        return new FuelEntryResponse(
            entry.getId(),
            entry.getLiters(),
            entry.getPrice(),
            entry.getOdometer(),
            entry.getTimestamp()
        );
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
}
