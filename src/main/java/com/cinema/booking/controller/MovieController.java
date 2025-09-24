package com.cinema.booking.controller;

import com.cinema.booking.dto.ApiResponse;
import com.cinema.booking.model.*;
import com.cinema.booking.service.CinemaBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/movies")
@CrossOrigin(origins = "*")
public class MovieController {
    
    @Autowired
    private CinemaBookingService cinemaBookingService;
    
    @PostMapping
    public ResponseEntity<ApiResponse<Movie>> createMovie(@RequestBody CreateMovieRequest request) {
        try {
            SeatMap seatMap = new SeatMap(request.getTotalRows(), request.getSeatsPerRow());
            Movie movie = cinemaBookingService.createMovie(request.getTitle(), seatMap);
            
            return ResponseEntity.ok(
                ApiResponse.success("Movie created successfully", movie)
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                ApiResponse.error("Failed to create movie: " + e.getMessage())
            );
        }
    }
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<Movie>>> getAllMovies() {
        List<Movie> movies = cinemaBookingService.getAllMovies();
        return ResponseEntity.ok(
            ApiResponse.success("Movies retrieved successfully", movies)
        );
    }
    
    @GetMapping("/{movieId}")
    public ResponseEntity<ApiResponse<Movie>> getMovieById(@PathVariable String movieId) {
        return cinemaBookingService.getMovieById(movieId)
                .map(movie -> ResponseEntity.ok(
                    ApiResponse.success("Movie found", movie)
                ))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    ApiResponse.error("Movie not found")
                ));
    }
    
    @GetMapping("/{movieId}/seats")
    public ResponseEntity<ApiResponse<List<Seat>>> getMovieSeats(@PathVariable String movieId) {
        try {
            List<Seat> seats = cinemaBookingService.getAvailableSeats(movieId);
            return ResponseEntity.ok(
                ApiResponse.success("Available seats retrieved", seats)
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                ApiResponse.error(e.getMessage())
            );
        }
    }
    
    // Inner class for request handling
    public static class CreateMovieRequest {
        private String title;
        private int totalRows;
        private int seatsPerRow;
        
        // Constructors
        public CreateMovieRequest() {}
        
        public CreateMovieRequest(String title, int totalRows, int seatsPerRow) {
            this.title = title;
            this.totalRows = totalRows;
            this.seatsPerRow = seatsPerRow;
        }
        
        // Getters and Setters
        public String getTitle() {
            return title;
        }
        
        public void setTitle(String title) {
            this.title = title;
        }
        
        public int getTotalRows() {
            return totalRows;
        }
        
        public void setTotalRows(int totalRows) {
            this.totalRows = totalRows;
        }
        
        public int getSeatsPerRow() {
            return seatsPerRow;
        }
        
        public void setSeatsPerRow(int seatsPerRow) {
            this.seatsPerRow = seatsPerRow;
        }
    }
}