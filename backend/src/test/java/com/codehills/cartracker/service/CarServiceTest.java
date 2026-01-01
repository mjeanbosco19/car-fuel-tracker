package com.codehills.cartracker.service;

import com.codehills.cartracker.dto.AddFuelRequest;
import com.codehills.cartracker.dto.CreateCarRequest;
import com.codehills.cartracker.model.Car;
import com.codehills.cartracker.model.FuelEntry;
import com.codehills.cartracker.model.FuelStats;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class CarServiceTest {

    private CarService carService;

    @BeforeEach
    void setUp() {
        carService = new CarService();
    }

    @Test
    void testCreateCar_Success() {
        CreateCarRequest request = new CreateCarRequest("Toyota", "Corolla", 2020);

        Car car = carService.createCar(request);

        assertThat(car).isNotNull();
        assertThat(car.getId()).isEqualTo(1L);
        assertThat(car.getBrand()).isEqualTo("Toyota");
        assertThat(car.getModel()).isEqualTo("Corolla");
        assertThat(car.getYear()).isEqualTo(2020);
    }

    @Test
    void testGetCarById_Found() {
        CreateCarRequest request = new CreateCarRequest("Honda", "Civic", 2019);
        Car createdCar = carService.createCar(request);

        Optional<Car> found = carService.getCarById(createdCar.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getBrand()).isEqualTo("Honda");
    }

    @Test
    void testGetCarById_NotFound() {
        Optional<Car> found = carService.getCarById(999L);

        assertThat(found).isEmpty();
    }

    @Test
    void testAddFuelEntry_Success() {
        CreateCarRequest carRequest = new CreateCarRequest("BMW", "X5", 2021);
        Car car = carService.createCar(carRequest);

        AddFuelRequest fuelRequest = new AddFuelRequest(45.0, 75.50, 10000);
        Optional<FuelEntry> entry = carService.addFuelEntry(car.getId(), fuelRequest);

        assertThat(entry).isPresent();
        assertThat(entry.get().getLiters()).isEqualTo(45.0);
        assertThat(entry.get().getPrice()).isEqualTo(75.50);
        assertThat(entry.get().getOdometer()).isEqualTo(10000);
    }

    @Test
    void testAddFuelEntry_CarNotFound() {
        AddFuelRequest fuelRequest = new AddFuelRequest(40.0, 60.00, 5000);

        Optional<FuelEntry> entry = carService.addFuelEntry(999L, fuelRequest);

        assertThat(entry).isEmpty();
    }

    @Test
    void testGetFuelStats_WithEntries() {
        CreateCarRequest carRequest = new CreateCarRequest("Audi", "A4", 2022);
        Car car = carService.createCar(carRequest);

        carService.addFuelEntry(car.getId(), new AddFuelRequest(50.0, 80.00, 10000));
        carService.addFuelEntry(car.getId(), new AddFuelRequest(45.0, 72.00, 10500));
        carService.addFuelEntry(car.getId(), new AddFuelRequest(48.0, 76.80, 11000));

        Optional<FuelStats> stats = carService.getFuelStats(car.getId());

        assertThat(stats).isPresent();
        assertThat(stats.get().getTotalFuel()).isEqualTo(143.0);
        assertThat(stats.get().getTotalCost()).isEqualTo(228.80);
    }

    @Test
    void testFuelConsumption_Calculation() {
        CreateCarRequest carRequest = new CreateCarRequest("Mercedes", "C-Class", 2023);
        Car car = carService.createCar(carRequest);

        // First entry: 50L at 10000km (this fuel won't be counted - consumed before tracking)
        carService.addFuelEntry(car.getId(), new AddFuelRequest(50.0, 80.00, 10000));
        // Second entry: 45L at 10500km (45L consumed over 500km)
        carService.addFuelEntry(car.getId(), new AddFuelRequest(45.0, 72.00, 10500));
        // Third entry: 48L at 11000km (48L consumed over 500km)
        carService.addFuelEntry(car.getId(), new AddFuelRequest(48.0, 76.80, 11000));

        // Total distance: 11000 - 10000 = 1000km
        // Fuel consumed: 45 + 48 = 93L (excludes first entry)
        // Average: (93 / 1000) * 100 = 9.3 L/100km

        Optional<FuelStats> stats = carService.getFuelStats(car.getId());

        assertThat(stats).isPresent();
        assertThat(stats.get().getAverageConsumption()).isEqualTo(9.3);
    }

    @Test
    void testFuelConsumption_LessThanTwoEntries() {
        CreateCarRequest carRequest = new CreateCarRequest("Volkswagen", "Golf", 2020);
        Car car = carService.createCar(carRequest);

        // Only one entry - cannot calculate consumption
        carService.addFuelEntry(car.getId(), new AddFuelRequest(40.0, 64.00, 5000));

        Optional<FuelStats> stats = carService.getFuelStats(car.getId());

        assertThat(stats).isPresent();
        assertThat(stats.get().getAverageConsumption()).isEqualTo(0.0);
    }
}
