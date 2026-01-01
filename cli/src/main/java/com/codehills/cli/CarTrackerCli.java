package com.codehills.cli;

import com.codehills.cli.model.Car;
import com.codehills.cli.model.FuelEntry;
import com.codehills.cli.model.FuelStats;

import java.util.HashMap;
import java.util.Map;


public class CarTrackerCli {
    
    private static final String DEFAULT_BASE_URL = "http://localhost:8080";
    
    public static void main(String[] args) {
        if (args.length == 0) {
            printUsage();
            System.exit(1);
        }
        
        String baseUrl = System.getenv("CAR_TRACKER_URL");
        if (baseUrl == null || baseUrl.isEmpty()) {
            baseUrl = DEFAULT_BASE_URL;
        }
        
        ApiClient client = new ApiClient(baseUrl);
        String command = args[0].toLowerCase();
        
        // Parse named arguments into a map
        Map<String, String> params = parseArguments(args);
        
        try {
            switch (command) {
                case "create-car":
                    handleCreateCar(client, params);
                    break;
                case "add-fuel":
                    handleAddFuel(client, params);
                    break;
                case "fuel-stats":
                    handleFuelStats(client, params);
                    break;
                case "help":
                case "--help":
                case "-h":
                    printUsage();
                    break;
                default:
                    System.err.println("Unknown command: " + command);
                    printUsage();
                    System.exit(1);
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(1);
        }
    }

    private static Map<String, String> parseArguments(String[] args) {
        Map<String, String> params = new HashMap<>();
        
        for (int i = 1; i < args.length; i++) {
            if (args[i].startsWith("--") && i + 1 < args.length) {
                String key = args[i].substring(2); // Remove "--"
                String value = args[i + 1];
                params.put(key, value);
                i++; // Skip the value in next iteration
            }
        }
        
        return params;
    }
    

    private static void handleCreateCar(ApiClient client, Map<String, String> params) 
            throws Exception {
        
        String brand = params.get("brand");
        String model = params.get("model");
        String yearStr = params.get("year");
        
        // Validate required parameters
        if (brand == null || model == null || yearStr == null) {
            System.err.println("Usage: create-car --brand <brand> --model <model> --year <year>");
            System.err.println("Example: create-car --brand Toyota --model Corolla --year 2018");
            System.exit(1);
        }
        
        int year;
        try {
            year = Integer.parseInt(yearStr);
        } catch (NumberFormatException e) {
            System.err.println("Error: Year must be a number");
            System.exit(1);
            return;
        }
        
        Car car = client.createCar(brand, model, year);
        System.out.println("Car created successfully!");
        System.out.println(car);
    }
    
    private static void handleAddFuel(ApiClient client, Map<String, String> params) 
            throws Exception {
        
        String carIdStr = params.get("carId");
        String litersStr = params.get("liters");
        String priceStr = params.get("price");
        String odometerStr = params.get("odometer");
        
        // Validate required parameters
        if (carIdStr == null || litersStr == null || priceStr == null || odometerStr == null) {
            System.err.println("Usage: add-fuel --carId <id> --liters <liters> --price <price> --odometer <km>");
            System.err.println("Example: add-fuel --carId 1 --liters 40 --price 52.5 --odometer 45000");
            System.exit(1);
        }
        
        long carId;
        double liters;
        double price;
        int odometer;
        
        try {
            carId = Long.parseLong(carIdStr);
            liters = Double.parseDouble(litersStr);
            price = Double.parseDouble(priceStr);
            odometer = Integer.parseInt(odometerStr);
        } catch (NumberFormatException e) {
            System.err.println("Error: Invalid number format");
            System.exit(1);
            return;
        }
        
        FuelEntry entry = client.addFuel(carId, liters, price, odometer);
        System.out.println("Fuel entry added successfully!");
        System.out.println(entry);
    }

    private static void handleFuelStats(ApiClient client, Map<String, String> params) 
            throws Exception {
        
        String carIdStr = params.get("carId");
        
        // Validate required parameter
        if (carIdStr == null) {
            System.err.println("Usage: fuel-stats --carId <id>");
            System.err.println("Example: fuel-stats --carId 1");
            System.exit(1);
        }
        
        long carId;
        try {
            carId = Long.parseLong(carIdStr);
        } catch (NumberFormatException e) {
            System.err.println("Error: carId must be a number");
            System.exit(1);
            return;
        }
        
        FuelStats stats = client.getFuelStats(carId);
        
        // Output uses toString() which matches assignment format
        System.out.println(stats);
    }
    
    private static void printUsage() {
        System.out.println("Car Fuel Tracker - CLI Application");
        System.out.println();
        System.out.println("Usage:");
        System.out.println("  java -jar cli.jar <command> [options]");
        System.out.println();
        System.out.println("Commands:");
        System.out.println("  create-car --brand <brand> --model <model> --year <year>");
        System.out.println("      Create a new car");
        System.out.println("      Example: create-car --brand Toyota --model Corolla --year 2018");
        System.out.println();
        System.out.println("  add-fuel --carId <id> --liters <liters> --price <price> --odometer <km>");
        System.out.println("      Add a fuel entry to a car");
        System.out.println("      Example: add-fuel --carId 1 --liters 40 --price 52.5 --odometer 45000");
        System.out.println();
        System.out.println("  fuel-stats --carId <id>");
        System.out.println("      Get fuel statistics for a car");
        System.out.println("      Example: fuel-stats --carId 1");
    }
}