package com.cinema.booking.service;

import com.cinema.booking.model.*;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CinemaBookingService {
    private final Map<String, Movie> movies = new ConcurrentHashMap<>();
    private final Map<String, Booking> bookings = new ConcurrentHashMap<>();
    
    public Movie createMovie(String title, SeatMap seatMap) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Movie title cannot be null or empty");
        }
        if (seatMap == null) {
            throw new IllegalArgumentException("Seat map cannot be null");
        }
        
        Movie movie = new Movie(title, seatMap);
        movies.put(movie.getId(), movie);
        return movie;
    }
    
    public List<Movie> getAllMovies() {
        return new ArrayList<>(movies.values());
    }
    
    public Optional<Movie> getMovieById(String movieId) {
        return Optional.ofNullable(movies.get(movieId));
    }
    
    public Optional<Movie> getMovieByTitle(String title) {
        return movies.values().stream()
                .filter(movie -> movie.getTitle().equalsIgnoreCase(title))
                .findFirst();
    }
    
    public List<Seat> getAvailableSeats(String movieId) {
        return getMovieById(movieId)
                .map(movie -> movie.getSeatMap().getAvailableSeats())
                .orElse(Collections.emptyList());
    }
    
    public Booking createBooking(String movieId, BookingRequest request) {
        Movie movie = getMovieById(movieId)
                .orElseThrow(() -> new IllegalArgumentException("Movie not found"));
        
        // Validate all seats are available
        for (String seatId : request.getSeatIds()) {
            if (!movie.getSeatMap().isSeatAvailable(seatId)) {
                throw new IllegalStateException("Seat " + seatId + " is not available");
            }
        }
        
        // Book all seats
        for (String seatId : request.getSeatIds()) {
            if (!movie.getSeatMap().bookSeat(seatId)) {
                throw new IllegalStateException("Failed to book seat " + seatId);
            }
        }
        
        Booking booking = new Booking(movieId, request.getCustomerName(), 
                                    request.getCustomerEmail(), request.getSeatIds());
        bookings.put(booking.getId(), booking);
        
        return booking;
    }
    
    public List<Booking> getAllBookings() {
        return new ArrayList<>(bookings.values());
    }
    
    public Optional<Booking> getBookingById(String bookingId) {
        return Optional.ofNullable(bookings.get(bookingId));
    }
    
    public List<Booking> getBookingsByMovieId(String movieId) {
        return bookings.values().stream()
                .filter(booking -> booking.getMovieId().equals(movieId))
                .toList();
    }
    
    public void clearAllData() {
        movies.clear();
        bookings.clear();
    }
}