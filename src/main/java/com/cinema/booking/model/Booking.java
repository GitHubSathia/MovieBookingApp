package com.cinema.booking.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class Booking {
    private final String id;
    private final String movieId;
    private final String customerName;
    private final String customerEmail;
    private final List<String> seatIds;
    private final LocalDateTime bookingTime;
    
    public Booking(String movieId, String customerName, String customerEmail, List<String> seatIds) {
        this.id = UUID.randomUUID().toString();
        this.movieId = movieId;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.seatIds = seatIds;
        this.bookingTime = LocalDateTime.now();
    }
    
    public String getId() {
        return id;
    }
    
    public String getMovieId() {
        return movieId;
    }
    
    public String getCustomerName() {
        return customerName;
    }
    
    public String getCustomerEmail() {
        return customerEmail;
    }
    
    public List<String> getSeatIds() {
        return seatIds;
    }
    
    public LocalDateTime getBookingTime() {
        return bookingTime;
    }
    
    @Override
    public String toString() {
        return "Booking{" +
                "id='" + id + '\'' +
                ", movieId='" + movieId + '\'' +
                ", customerName='" + customerName + '\'' +
                ", customerEmail='" + customerEmail + '\'' +
                ", seatIds=" + seatIds +
                ", bookingTime=" + bookingTime +
                '}';
    }
}