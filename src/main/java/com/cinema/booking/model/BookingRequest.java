package com.cinema.booking.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public class BookingRequest {
    @NotBlank(message = "Customer name is required")
    private final String customerName;
    
    @NotBlank(message = "Customer email is required")
    private final String customerEmail;
    
    @NotEmpty(message = "At least one seat must be selected")
    private final List<String> seatIds;
    
    @JsonCreator
    public BookingRequest(@JsonProperty("customerName") String customerName,
                         @JsonProperty("customerEmail") String customerEmail,
                         @JsonProperty("seatIds") List<String> seatIds) {
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.seatIds = seatIds;
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
}