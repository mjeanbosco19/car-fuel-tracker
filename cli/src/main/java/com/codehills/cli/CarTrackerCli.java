package com.codehills.cli;

import com.codehills.cli.model.Car;
import com.codehills.cli.model.FuelEntry;
import com.codehills.cli.model.FuelStats;


public class CarTrackerCli {
    
    private static final String DEFAULT_BASE_URL = "http://localhost:8080";
    
    public static void main(String[] args) {
        
        // Check if any arguments provided
        if (args.length == 0) {
            printUsage();
            System.exit(1);
        }
        
        // Get base URL from environment variable or use default
        String baseUrl = System.getenv("CAR_TRACKER_URL");
        if (baseUrl == null || baseUrl.isEmpty()) {
            baseUrl = DEFAULT_BASE_URL;
        }
        
        // Create API client
        ApiClient client = new ApiClient(baseUrl);
        
        // Get command (first argument)
        String command = args[0].toLowerCase();
        
        try {
            // Route to appropriate handler based on command
            switch (command) {
                case "create-car":
                    handleCreateCar(client, args);
                    break;
                    
                case "add-fuel":
                    handleAddFuel(client, args);
                    break;
                    
                case "fuel-stats":
                    handleFuelStats(client, args);
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
    
    private static void handleCreateCar(ApiClient client, String[] args) throws Exception {
        
        // Validate argument count
        if (args.length != 4) {
            System.err.println("Usage: create-car <brand> <model> <year>");
            System.err.println("Example: create-car Toyota Corolla 2018");
            System.exit(1);
        }
        
        String brand = args[1];
        String model = args[2];
        int year;
        
        try {
            year = Integer.parseInt(args[3]);
        } catch (NumberFormatException e) {
            System.err.println("Error: Year must be a number");
            System.exit(1);
            return;
        }
        
        // Call API
        System.out.println("Creating car...");
        Car car = client.createCar(brand, model, year);
        
        // Display result
        System.out.println("✓ Car created successfully!");
        System.out.println(car);
    }
    
    private static void handleAddFuel(ApiClient client, String[] args) throws Exception {
        
        // Validate argument count
        if (args.length != 5) {
            System.err.println("Usage: add-fuel <carId> <liters> <price> <odometer>");
            System.err.println("Example: add-fuel 1 40.0 52.50 45000");
            System.exit(1);
        }
        
        long carId;
        double liters;
        double price;
        int odometer;
        
        try {
            carId = Long.parseLong(args[1]);
            liters = Double.parseDouble(args[2]);
            price = Double.parseDouble(args[3]);
            odometer = Integer.parseInt(args[4]);
        } catch (NumberFormatException e) {
            System.err.println("Error: Invalid number format");
            System.err.println("carId and odometer must be integers");
            System.err.println("liters and price can be decimals");
            System.exit(1);
            return;
        }
        
        // Call API
        System.out.println("Adding fuel entry...");
        FuelEntry entry = client.addFuel(carId, liters, price, odometer);
        
        // Display result
        System.out.println("✓ Fuel entry added successfully!");
        System.out.println(entry);
    }
    
    private static void handleFuelStats(ApiClient client, String[] args) throws Exception {
        
        // Validate argument count
        if (args.length != 2) {
            System.err.println("Usage: fuel-stats <carId>");
            System.err.println("Example: fuel-stats 1");
            System.exit(1);
        }
        
        long carId;
        
        try {
            carId = Long.parseLong(args[1]);
        } catch (NumberFormatException e) {
            System.err.println("Error: carId must be a number");
            System.exit(1);
            return;
        }
        
        // Call API
        System.out.println("Fetching fuel statistics for car #" + carId + "...");
        FuelStats stats = client.getFuelStats(carId);
        
        // Display result
        System.out.println();
        System.out.println(stats);
    }
    
private static void printUsage() {
    System.out.println("Car Fuel Tracker - CLI Application");
    System.out.println();
    System.out.println("Usage:");
    System.out.println("  java -jar cli.jar <command> [arguments]");
    System.out.println();
    System.out.println("Commands:");
    System.out.println("  create-car <brand> <model> <year>");
    System.out.println("      Create a new car");
    System.out.println("      Example: create-car Toyota Corolla 2018");
    System.out.println();
    System.out.println("  add-fuel <carId> <liters> <price> <odometer>");
    System.out.println("      Add a fuel entry to a car");
    System.out.println("      Example: add-fuel 1 40.0 52.50 45000");
    System.out.println();
    System.out.println("  fuel-stats <carId>");
    System.out.println("      Get fuel statistics for a car");
    System.out.println("      Example: fuel-stats 1");
    System.out.println();
    System.out.println("Environment:");
    System.out.println("  CAR_TRACKER_URL - Server URL (default: http://localhost:8080)");
}
}