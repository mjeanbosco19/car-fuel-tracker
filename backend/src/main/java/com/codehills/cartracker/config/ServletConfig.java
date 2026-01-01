package com.codehills.cartracker.config;

import com.codehills.cartracker.service.CarService;
import com.codehills.cartracker.servlet.FuelStatsServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * SERVLET CONFIGURATION
 * =====================
 * Registers our manual servlet with Spring Boot.
 * 
 * WHY THIS APPROACH?
 * ==================
 * Instead of using @WebServlet + @ServletComponentScan (which has
 * compatibility issues in Spring Boot 4.x), we register the servlet
 * programmatically using ServletRegistrationBean.
 * 
 * This approach:
 * - Is more explicit and easier to understand
 * - Allows dependency injection (we pass CarService to servlet)
 * - Works reliably across Spring Boot versions
 * - Is the recommended Spring Boot way
 */
@Configuration
public class ServletConfig {
    
    /**
     * REGISTER FUEL STATS SERVLET
     * ===========================
     * Creates and registers our FuelStatsServlet.
     * 
     * @param carService Injected by Spring (same instance used by REST controller)
     * @return ServletRegistrationBean that registers our servlet
     */
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