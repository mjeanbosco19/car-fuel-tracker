package com.codehills.cartracker.controller;

import com.codehills.cartracker.dto.AddFuelRequest;
import com.codehills.cartracker.dto.CreateCarRequest;
import com.codehills.cartracker.model.Car;
import com.codehills.cartracker.model.FuelEntry;
import com.codehills.cartracker.model.FuelStats;
import com.codehills.cartracker.service.CarService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This controller handles all HTTP requests for car management.
 * All methods here will have URLs starting with /api/cars
 */
@RestController
@RequestMapping("/api/cars")
public class CarController {

    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @PostMapping
    public ResponseEntity<Car> createCar(@Valid @RequestBody CreateCarRequest request) {
        Car createdCar = carService.createCar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCar);
    }

    @GetMapping
    public List<Car> getAllCars() {
        return carService.getAllCars();
    }
    

    @GetMapping("/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable Long id) {
        return carService.getCarById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/fuel")
    public ResponseEntity<FuelEntry> addFuel(
            @PathVariable Long id,
            @Valid @RequestBody AddFuelRequest request) {

        return carService.addFuelEntry(id, request)
                .map(entry -> ResponseEntity.status(HttpStatus.CREATED).body(entry))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/fuel/stats")
    public ResponseEntity<FuelStats> getFuelStats(@PathVariable("id") Long id) {
        return carService.getFuelStats(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}