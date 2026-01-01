package com.codehills.cartracker.servlet;

import com.codehills.cartracker.model.FuelStats;
import com.codehills.cartracker.service.CarService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

/**
 * FUEL STATS SERVLET - MANUAL SERVLET IMPLEMENTATION (PART 2)
 * ============================================================
 * 
 * ASSIGNMENT REQUIREMENT:
 * "Create a servlet endpoint: GET /servlet/fuel-stats?carId={id}"
 * "Must extend HttpServlet and override doGet()"
 * "Use the same Service layer instance as the REST API"
 * 
 * NOTE: Instead of using Jackson ObjectMapper, we manually build JSON
 * to avoid Spring Boot 4.x Jackson package compatibility issues.
 * This also demonstrates a more "raw" servlet approach.
 */
public class FuelStatsServlet extends HttpServlet {
    
    /**
     * THE SAME SERVICE USED BY REST CONTROLLER
     * =========================================
     * This is the key requirement: share the service instance.
     * Both REST API and this servlet use the same CarService singleton.
     */
    private final CarService carService;
    
    /**
     * CONSTRUCTOR
     * ===========
     * CarService is passed in when registering the servlet.
     */
    public FuelStatsServlet(CarService carService) {
        this.carService = carService;
    }
    
    /**
     * HANDLE GET REQUESTS
     * ===================
     * Called when GET request arrives at /servlet/fuel-stats
     * 
     * URL format: GET /servlet/fuel-stats?carId=1
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        
        // ==================== STEP 1: PARSE QUERY PARAMETER ====================
        // In @RestController: @RequestParam("carId") Long carId (automatic!)
        // In HttpServlet: request.getParameter("carId") (manual!)
        
        String carIdParam = request.getParameter("carId");
        
        // ==================== STEP 2: VALIDATE INPUT ====================
        
        if (carIdParam == null || carIdParam.trim().isEmpty()) {
            sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST,
                    "Missing required parameter: carId");
            return;
        }
        
        // Try to parse the carId as a Long
        Long carId;
        try {
            carId = Long.parseLong(carIdParam);
        } catch (NumberFormatException e) {
            sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST,
                    "Invalid carId format: must be a number");
            return;
        }
        
        // ==================== STEP 3: GET DATA FROM SERVICE ====================
        // Use the SAME service that REST controller uses
        
        Optional<FuelStats> statsOptional = carService.getFuelStats(carId);
        
        // ==================== STEP 4: RETURN RESPONSE ====================
        
        if (statsOptional.isEmpty()) {
            sendErrorResponse(response, HttpServletResponse.SC_NOT_FOUND,
                    "Car not found with id: " + carId);
            return;
        }
        
        // Success - return fuel stats as JSON
        FuelStats stats = statsOptional.get();
        sendSuccessResponse(response, stats);
    }
    
    /**
     * SEND SUCCESS RESPONSE WITH FUEL STATS
     * =====================================
     * Manually builds JSON response.
     * 
     * In a real project, you'd use a JSON library.
     * Here we demonstrate the "raw" approach to show what frameworks do for you.
     */
    private void sendSuccessResponse(HttpServletResponse response, FuelStats stats) 
            throws IOException {
        
        // Set response headers
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);
        
        // Build JSON manually
        String json = String.format(
            "{\"totalFuel\":%.1f,\"totalCost\":%.1f,\"averageConsumption\":%.1f}",
            stats.getTotalFuel(),
            stats.getTotalCost(),
            stats.getAverageConsumption()
        );
        
        // Write to response
        PrintWriter writer = response.getWriter();
        writer.write(json);
        writer.flush();
    }
    
    /**
     * SEND ERROR RESPONSE
     * ===================
     * Returns error as JSON: {"error": "message"}
     */
    private void sendErrorResponse(HttpServletResponse response, int statusCode, String message) 
            throws IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(statusCode);
        
        // Build error JSON
        String json = String.format("{\"error\":\"%s\"}", message);
        
        PrintWriter writer = response.getWriter();
        writer.write(json);
        writer.flush();
    }
}