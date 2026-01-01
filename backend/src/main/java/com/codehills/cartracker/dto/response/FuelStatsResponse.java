package com.codehills.cartracker.dto.response;

import com.codehills.cartracker.model.FuelStats;

public class FuelStatsResponse {

    private Double totalFuel;
    private Double totalCost;
    private Double averageConsumption;
    private int entryCount;

    public FuelStatsResponse() {
    }

    public FuelStatsResponse(Double totalFuel, Double totalCost,
                             Double averageConsumption, int entryCount) {
        this.totalFuel = totalFuel;
        this.totalCost = totalCost;
        this.averageConsumption = averageConsumption;
        this.entryCount = entryCount;
    }

    public static FuelStatsResponse fromFuelStats(FuelStats stats, int entryCount) {
        return new FuelStatsResponse(
            stats.getTotalFuel(),
            stats.getTotalCost(),
            stats.getAverageConsumption(),
            entryCount
        );
    }

    public Double getTotalFuel() {
        return totalFuel;
    }

    public void setTotalFuel(Double totalFuel) {
        this.totalFuel = totalFuel;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public Double getAverageConsumption() {
        return averageConsumption;
    }

    public void setAverageConsumption(Double averageConsumption) {
        this.averageConsumption = averageConsumption;
    }

    public int getEntryCount() {
        return entryCount;
    }

    public void setEntryCount(int entryCount) {
        this.entryCount = entryCount;
    }
}
