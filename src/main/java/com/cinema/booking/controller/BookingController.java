package com.cinema.booking.controller;

import com.cinema.booking.dto.ApiResponse;
import com.cinema.booking.model.Booking;
import com.cinema.booking.model.BookingRequest;
import com.cinema.booking.service.CinemaBookingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class BookingController {
    
    @Autowired
    private CinemaBookingService cinemaBookingService;
    
    @PostMapping("/movies/{movieId}/bookings")
    public ResponseEntity<ApiResponse<Booking>> createBooking(
            @PathVariable String movieId,
            @Valid @RequestBody BookingRequest request,
            BindingResult bindingResult) {
        
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
            return ResponseEntity.badRequest().body(
                ApiResponse.error("Validation error: " + errorMessage)
            );
        }
        
        try {
            Booking booking = cinemaBookingService.createBooking(movieId, request);
            return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.success("Booking created successfully", booking)
            );
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ApiResponse.error(e.getMessage())
            );
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                ApiResponse.error(e.getMessage())
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                ApiResponse.error("Failed to create booking: " + e.getMessage())
            );
        }
    }
    
    @GetMapping("/bookings")
    public ResponseEntity<ApiResponse<List<Booking>>> getAllBookings() {
        List<Booking> bookings = cinemaBookingService.getAllBookings();
        return ResponseEntity.ok(
            ApiResponse.success("Bookings retrieved successfully", bookings)
        );
    }
    
    @GetMapping("/bookings/{bookingId}")
    public ResponseEntity<ApiResponse<Booking>> getBookingById(@PathVariable String bookingId) {
        return cinemaBookingService.getBookingById(bookingId)
                .map(booking -> ResponseEntity.ok(
                    ApiResponse.success("Booking found", booking)
                ))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    ApiResponse.error("Booking not found")
                ));
    }
    
    @GetMapping("/movies/{movieId}/bookings")
    public ResponseEntity<ApiResponse<List<Booking>>> getBookingsByMovie(@PathVariable String movieId) {
        List<Booking> bookings = cinemaBookingService.getBookingsByMovieId(movieId);
        return ResponseEntity.ok(
            ApiResponse.success("Movie bookings retrieved", bookings)
        );
    }
}