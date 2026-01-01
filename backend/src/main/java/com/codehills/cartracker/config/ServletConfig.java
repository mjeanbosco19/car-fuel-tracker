package com.codehills.cartracker.config;

import com.codehills.cartracker.service.CarService;
import com.codehills.cartracker.servlet.FuelStatsServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServletConfig {

    @Bean
    public ServletRegistrationBean<FuelStatsServlet> fuelStatsServlet(CarService carService) {
        
        // Create the servlet, passing in the shared CarService
        FuelStatsServlet servlet = new FuelStatsServlet(carService);
        
        // Register it at the URL path /servlet/fuel-stats
        ServletRegistrationBean<FuelStatsServlet> registration = 
                new ServletRegistrationBean<>(servlet, "/servlet/fuel-stats");
        
        // Give it a name (for logging/debugging)
        registration.setName("fuelStatsServlet");
        
        // Load on startup (1 = high priority)
        registration.setLoadOnStartup(1);
        
        return registration;
    }
}