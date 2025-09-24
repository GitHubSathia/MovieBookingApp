package com.cinema.booking.controller;

import com.cinema.booking.dto.ApiResponse;
import com.cinema.booking.service.CinemaBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/system")
@CrossOrigin(origins = "*")
public class SystemController {
    
    @Autowired
    private CinemaBookingService cinemaBookingService;
    
    @GetMapping("/health")
    public ResponseEntity<ApiResponse<String>> healthCheck() {
        return ResponseEntity.ok(
            ApiResponse.success("Cinema Booking System is running", "OK")
        );
    }
    
    @PostMapping("/reset")
    public ResponseEntity<ApiResponse<String>> resetSystem() {
        cinemaBookingService.clearAllData();
        return ResponseEntity.ok(
            ApiResponse.success("System reset successfully", "All data cleared")
        );
    }
}