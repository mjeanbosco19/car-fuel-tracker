package com.codehills.cli.model;

// This is a FUEL STATS MODEL FOR CLI which is completely separate from the backend's FuelStats model.

public class FuelStats {
    
    private Double totalFuel;
    private Double totalCost;
    private Double averageConsumption;
    
    public FuelStats() {}
    
    // Getters and Setters
    public Double getTotalFuel() { return totalFuel; }
    public void setTotalFuel(Double totalFuel) { this.totalFuel = totalFuel; }
    
    public Double getTotalCost() { return totalCost; }
    public void setTotalCost(Double totalCost) { this.totalCost = totalCost; }
    
    public Double getAverageConsumption() { return averageConsumption; }
    public void setAverageConsumption(Double averageConsumption) { 
        this.averageConsumption = averageConsumption; 
    }
    
    @Override
    public String toString() {
        return String.format(
            "=== Fuel Statistics ===\n" +
            "Total Fuel:    %.1f L\n" +
            "Total Cost:    %.2f\n" +
            "Average:       %.1f L/100km",
            totalFuel, totalCost, averageConsumption
        );
    }
}