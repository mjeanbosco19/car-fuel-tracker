package com.codehills.cartracker.dto.response;

import java.time.LocalDateTime;

public class ErrorResponse {

    private int status;
    private String error;
    private String message;
    private LocalDateTime timestamp;

    public ErrorResponse() {
    }

    public ErrorResponse(int status, String error, String message) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    public static ErrorResponse badRequest(String message) {
        return new ErrorResponse(400, "Bad Request", message);
    }

    public static ErrorResponse notFound(String message) {
        return new ErrorResponse(404, "Not Found", message);
    }

    public static ErrorResponse internalError(String message) {
        return new ErrorResponse(500, "Internal Server Error", message);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
