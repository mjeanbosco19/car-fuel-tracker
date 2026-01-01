package com.codehills.cartracker.controller;

import com.codehills.cartracker.dto.AddFuelRequest;
import com.codehills.cartracker.dto.CreateCarRequest;
import com.codehills.cartracker.dto.response.CarResponse;
import com.codehills.cartracker.dto.response.FuelEntryResponse;
import com.codehills.cartracker.dto.response.FuelStatsResponse;
import com.codehills.cartracker.model.Car;
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
    public ResponseEntity<CarResponse> createCar(@Valid @RequestBody CreateCarRequest request) {
        Car createdCar = carService.createCar(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CarResponse.fromCar(createdCar));
    }

    @GetMapping
    public List<CarResponse> getAllCars() {
        return carService.getAllCars().stream()
                .map(CarResponse::fromCar)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarResponse> getCarById(@PathVariable Long id) {
        return carService.getCarById(id)
                .map(car -> ResponseEntity.ok(CarResponse.fromCar(car)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/fuel")
    public ResponseEntity<FuelEntryResponse> addFuel(
            @PathVariable Long id,
            @Valid @RequestBody AddFuelRequest request) {

        return carService.addFuelEntry(id, request)
                .map(entry -> ResponseEntity.status(HttpStatus.CREATED)
                        .body(FuelEntryResponse.fromFuelEntry(entry)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/fuel/stats")
    public ResponseEntity<FuelStatsResponse> getFuelStats(@PathVariable("id") Long id) {
        return carService.getCarById(id)
                .flatMap(car -> carService.getFuelStats(id)
                        .map(stats -> FuelStatsResponse.fromFuelStats(stats, car.getFuelEntries().size())))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}