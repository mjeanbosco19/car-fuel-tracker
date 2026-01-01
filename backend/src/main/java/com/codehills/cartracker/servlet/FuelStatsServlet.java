package com.codehills.cartracker.servlet;

import com.codehills.cartracker.dto.response.ErrorResponse;
import com.codehills.cartracker.model.FuelStats;
import com.codehills.cartracker.service.CarService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

public class FuelStatsServlet extends HttpServlet {

    private final CarService carService;
    private final ObjectMapper objectMapper;

    public FuelStatsServlet(CarService carService) {
        this.carService = carService;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {

        String carIdParam = request.getParameter("carId");
        
        
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

        Optional<FuelStats> statsOptional = carService.getFuelStats(carId);
        
        
        if (statsOptional.isEmpty()) {
            sendErrorResponse(response, HttpServletResponse.SC_NOT_FOUND,
                    "Car not found with id: " + carId);
            return;
        }
        
        // Success - return fuel stats as JSON
        FuelStats stats = statsOptional.get();
        sendSuccessResponse(response, stats);
    }

    private void sendSuccessResponse(HttpServletResponse response, FuelStats stats)
            throws IOException {

        // Set response headers
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);

        // Build JSON safely using ObjectMapper to prevent injection
        String json = objectMapper.writeValueAsString(stats);

        // Write to response
        PrintWriter writer = response.getWriter();
        writer.write(json);
        writer.flush();
    }

    private void sendErrorResponse(HttpServletResponse response, int statusCode, String message)
            throws IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(statusCode);

        // Build error JSON safely using ObjectMapper to prevent injection
        ErrorResponse errorResponse = new ErrorResponse(statusCode, getErrorType(statusCode), message);
        String json = objectMapper.writeValueAsString(errorResponse);

        PrintWriter writer = response.getWriter();
        writer.write(json);
        writer.flush();
    }

    private String getErrorType(int statusCode) {
        return switch (statusCode) {
            case 400 -> "Bad Request";
            case 404 -> "Not Found";
            case 500 -> "Internal Server Error";
            default -> "Error";
        };
    }
}