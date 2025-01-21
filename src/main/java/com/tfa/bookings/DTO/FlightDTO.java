package com.tfa.bookings.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightDTO {
    private Long id;

    private String flightNumber;
    private String departure;
    private String arrival;
   // @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")

    private LocalDateTime departureTime;  //pattern = "yyyy-MM-dd HH:mm:ss"
   // @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")

    private LocalDateTime arrivalTime;//pattern = "yyyy-MM-dd HH:mm:ss"
    private Double price;
    private Integer availableSeats;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
