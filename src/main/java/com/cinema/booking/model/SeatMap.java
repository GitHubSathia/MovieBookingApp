package com.cinema.booking.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class SeatMap {
    private final int totalRows;
    private final int seatsPerRow;
    private final Map<String, Seat> seats;
    
    @JsonCreator
    public SeatMap(@JsonProperty("totalRows") int totalRows, 
                   @JsonProperty("seatsPerRow") int seatsPerRow) {
        this.totalRows = totalRows;
        this.seatsPerRow = seatsPerRow;
        this.seats = new HashMap<>();
        initializeSeats();
    }
    
    private void initializeSeats() {
        char rowChar = 'A';
        for (int i = 0; i < totalRows; i++) {
            String row = String.valueOf((char)(rowChar + i));
            for (int j = 1; j <= seatsPerRow; j++) {
                Seat seat = new Seat(row, j);
                seats.put(seat.getSeatId(), seat);
            }
        }
    }
    
    public int getTotalRows() {
        return totalRows;
    }
    
    public int getSeatsPerRow() {
        return seatsPerRow;
    }
    
    public List<Seat> getAllSeats() {
        return new ArrayList<>(seats.values());
    }
    
    public Seat getSeat(String seatId) {
        return seats.get(seatId);
    }
    
    public boolean isSeatAvailable(String seatId) {
        Seat seat = seats.get(seatId);
        return seat != null && seat.getStatus() == Seat.SeatStatus.AVAILABLE;
    }
    
    public boolean bookSeat(String seatId) {
        Seat seat = seats.get(seatId);
        if (seat != null && seat.getStatus() == Seat.SeatStatus.AVAILABLE) {
            seat.setStatus(Seat.SeatStatus.BOOKED);
            return true;
        }
        return false;
    }
    
    public List<Seat> getAvailableSeats() {
        return seats.values().stream()
                .filter(seat -> seat.getStatus() == Seat.SeatStatus.AVAILABLE)
                .toList();
    }
    
    public int getAvailableSeatsCount() {
        return (int) seats.values().stream()
                .filter(seat -> seat.getStatus() == Seat.SeatStatus.AVAILABLE)
                .count();
    }
}