package com.cinema.booking.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Seat {
    private final String row;
    private final int number;
    private SeatStatus status;
    
    public enum SeatStatus {
        AVAILABLE, BOOKED, RESERVED
    }
    
    @JsonCreator
    public Seat(@JsonProperty("row") String row, 
                @JsonProperty("number") int number) {
        this.row = row;
        this.number = number;
        this.status = SeatStatus.AVAILABLE;
    }
    
    public String getRow() {
        return row;
    }
    
    public int getNumber() {
        return number;
    }
    
    public SeatStatus getStatus() {
        return status;
    }
    
    public void setStatus(SeatStatus status) {
        this.status = status;
    }
    
    public String getSeatId() {
        return row + number;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Seat seat = (Seat) obj;
        return number == seat.number && row.equals(seat.row);
    }
    
    @Override
    public int hashCode() {
        return row.hashCode() * 31 + number;
    }
    
    @Override
    public String toString() {
        return "Seat{" +
                "row='" + row + '\'' +
                ", number=" + number +
                ", status=" + status +
                '}';
    }
}