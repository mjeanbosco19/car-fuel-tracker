package com.codehills.cartracker.service;

import com.codehills.cartracker.dto.AddFuelRequest;
import com.codehills.cartracker.dto.CreateCarRequest;
import com.codehills.cartracker.model.Car;
import com.codehills.cartracker.model.FuelEntry;
import com.codehills.cartracker.model.FuelStats;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * This class is a CAR SERVICE which contains ALL business logic and data storage.
 * - REST Controller uses this service.
 * - Manual Servlet uses this SAME service instance.
 * - Both access the same in-memory data.
 */
@Service
public class CarService {
    
    private final Map<Long, Car> cars = new ConcurrentHashMap<>();

    private final AtomicLong carIdGenerator = new AtomicLong(0);
    private final AtomicLong fuelEntryIdGenerator = new AtomicLong(0);
    
    public Car createCar(CreateCarRequest request) {
        // Create car from request data
        Car car = new Car(
            request.getBrand(),
            request.getModel(),
            request.getYear()
        );
        
        // Generate unique ID (1, 2, 3, ...)
        Long newId = carIdGenerator.incrementAndGet();
        car.setId(newId);
        
        // Store in our "database"
        cars.put(newId, car);
        
        return car;
    }
 
    public List<Car> getAllCars() {
        return new ArrayList<>(cars.values());
    }
  
    public Optional<Car> getCarById(Long id) {
        return Optional.ofNullable(cars.get(id));
    }
 
    public Optional<FuelEntry> addFuelEntry(Long carId, AddFuelRequest request) {
        // Try to find the car
        Car car = cars.get(carId);
        
        // Car not found - return empty Optional
        if (car == null) {
            return Optional.empty();
        }
        
        // Create fuel entry from request
        FuelEntry entry = new FuelEntry(
            request.getLiters(),
            request.getPrice(),
            request.getOdometer()
        );
        
        // Assign unique ID
        entry.setId(fuelEntryIdGenerator.incrementAndGet());
        
        // Add to car's fuel entries
        car.addFuelEntry(entry);
        
        return Optional.of(entry);
    }
 
    public Optional<FuelStats> getFuelStats(Long carId) {
        // Try to find the car
        Car car = cars.get(carId);
        
        if (car == null) {
            return Optional.empty();
        }
        
        List<FuelEntry> entries = car.getFuelEntries();
        
        // No entries yet - return zeros
        if (entries.isEmpty()) {
            return Optional.of(new FuelStats(0.0, 0.0, 0.0));
        }
        
        // Calculate total fuel using Stream API
        double totalFuel = entries.stream()
                .mapToDouble(FuelEntry::getLiters)
                .sum();
        
        // Calculate total cost
        double totalCost = entries.stream()
                .mapToDouble(FuelEntry::getPrice)
                .sum();
        
        // Calculate average consumption
        double averageConsumption = calculateAverageConsumption(entries);
        
        return Optional.of(new FuelStats(totalFuel, totalCost, averageConsumption));
    }

    private double calculateAverageConsumption(List<FuelEntry> entries) {
        // Need at least 2 entries to calculate consumption
        if (entries.size() < 2) {
            return 0.0;
        }

        // Sort entries by odometer to ensure correct order
        List<FuelEntry> sorted = entries.stream()
                .sorted(Comparator.comparingInt(FuelEntry::getOdometer))
                .toList();

        // Calculate distance from first to last fill-up
        int firstOdometer = sorted.get(0).getOdometer();
        int lastOdometer = sorted.get(sorted.size() - 1).getOdometer();
        int distance = lastOdometer - firstOdometer;

        // Avoid division by zero
        if (distance <= 0) {
            return 0.0;
        }

        // Sum fuel from all entries EXCEPT the first one
        // (first fill-up's fuel was consumed before our tracking started)
        double fuelConsumed = sorted.stream()
                .skip(1)
                .mapToDouble(FuelEntry::getLiters)
                .sum();

        // Calculate L/100km, round to 1 decimal place
        double average = (fuelConsumed / distance) * 100;
        return Math.round(average * 10.0) / 10.0;
    }
}