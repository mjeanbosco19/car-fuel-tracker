package com.codehills.cartracker.model;

/** 
 * I think this useful note here:
 * This a FUEL STATISTICS MODEL which holds calculated statistics.
 * A fuel statistics has a total fuel, total cost and an average consumption.
 * It is not stored anywhere, it is calculated on-the-fly from the fuel entries.
 */
public class FuelStats {
    
    private Double totalFuel;
    
    private Double totalCost;
    
    private Double averageConsumption;
    
    public FuelStats() {
    }
    
    public FuelStats(Double totalFuel, Double totalCost, Double averageConsumption) {
        this.totalFuel = totalFuel;
        this.totalCost = totalCost;
        this.averageConsumption = averageConsumption;
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
    
    @Override
    public String toString() {
        return "FuelStats{" +
                "totalFuel=" + totalFuel +
                ", totalCost=" + totalCost +
                ", averageConsumption=" + averageConsumption +
                '}';
    }
}