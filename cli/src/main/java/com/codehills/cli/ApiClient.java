package com.codehills.cli;

import com.codehills.cli.model.Car;
import com.codehills.cli.model.FuelEntry;
import com.codehills.cli.model.FuelStats;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.net.URI;
import java.util.Map;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;


public class ApiClient {

    private final String baseUrl;
    
    private final HttpClient httpClient;
    
    private final Gson gson;

    public ApiClient(String baseUrl) {
        this.baseUrl = baseUrl;
        
        // Build HTTP client with timeout
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        
        // Build Gson with pretty printing
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
    }
    
    

    public Car createCar(String brand, String model, int year) throws Exception {

        // Build JSON request body using Gson to prevent injection
        String jsonBody = gson.toJson(Map.of(
            "brand", brand,
            "model", model,
            "year", year
        ));
        
        // Build HTTP request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/api/cars"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();
        
        // Send request and get response
        HttpResponse<String> response = httpClient.send(
                request, 
                HttpResponse.BodyHandlers.ofString()
        );
        
        // Check for success
        if (response.statusCode() != 201) {
            throw new RuntimeException("Failed to create car: " + response.body());
        }
        
        // Parse JSON response to Car object
        return gson.fromJson(response.body(), Car.class);
    }
    
    
    // ADD FUEL ENTRY TO A CAR

    public FuelEntry addFuel(long carId, double liters, double price, int odometer)
            throws Exception {

        // Build JSON request body using Gson to prevent injection
        String jsonBody = gson.toJson(Map.of(
            "liters", liters,
            "price", price,
            "odometer", odometer
        ));
        
        // Build HTTP request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/api/cars/" + carId + "/fuel"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();
        
        // Send request
        HttpResponse<String> response = httpClient.send(
                request, 
                HttpResponse.BodyHandlers.ofString()
        );
        
        // Check for success
        if (response.statusCode() == 404) {
            throw new RuntimeException("Car not found with ID: " + carId);
        }
        if (response.statusCode() != 201) {
            throw new RuntimeException("Failed to add fuel: " + response.body());
        }
        
        return gson.fromJson(response.body(), FuelEntry.class);
    }
    

    public FuelStats getFuelStats(long carId) throws Exception {
        
        // Build HTTP GET request (no body needed)
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/api/cars/" + carId + "/fuel/stats"))
                .GET()
                .build();
        
        // Send request
        HttpResponse<String> response = httpClient.send(
                request, 
                HttpResponse.BodyHandlers.ofString()
        );
        
        // Check for errors
        if (response.statusCode() == 404) {
            throw new RuntimeException("Car not found with ID: " + carId);
        }
        if (response.statusCode() != 200) {
            throw new RuntimeException("Failed to get stats: " + response.body());
        }
        
        return gson.fromJson(response.body(), FuelStats.class);
    }
}