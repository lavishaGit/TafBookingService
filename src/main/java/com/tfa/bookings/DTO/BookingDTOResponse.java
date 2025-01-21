package com.tfa.bookings.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingDTOResponse {
    private Long id;
    private Long user_id;
    private Long flight_id;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
