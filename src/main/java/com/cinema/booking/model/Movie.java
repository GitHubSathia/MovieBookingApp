package com.cinema.booking.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;

public class Movie {
    private final String id;
    private final String title;
    private final SeatMap seatMap;
    
    @JsonCreator
    public Movie(@JsonProperty("title") String title, 
                 @JsonProperty("seatMap") SeatMap seatMap) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.seatMap = seatMap;
    }
    
    public String getId() {
        return id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public SeatMap getSeatMap() {
        return seatMap;
    }
    
    @Override
    public String toString() {
        return "Movie{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", seatMap=" + seatMap +
                '}';
    }
}