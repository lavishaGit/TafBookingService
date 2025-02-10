package com.tfa.bookings.Controller;

import com.tfa.bookings.DTO.BookingDTO;
import com.tfa.bookings.DTO.BookingDTOResponse;
import com.tfa.bookings.Service.DataStoreClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
     @CrossOrigin(origins = "*")
@RequestMapping("/bookings")
public class BookingController {
    @Autowired
    private DataStoreClient dataStoreClient;

    @PostMapping
    public ResponseEntity<BookingDTOResponse> createBooking(@RequestBody BookingDTO booking) {
        return ResponseEntity.status(HttpStatus.CREATED).body(dataStoreClient.createBooking(booking));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingDTOResponse> getBooking(@PathVariable Long id) {
        return ResponseEntity.ok(dataStoreClient.getBooking(id));
    }

    @GetMapping("/users/{UserId}")
    public ResponseEntity<List<BookingDTOResponse>> getBookingsByUserId(@PathVariable Long UserId) {
        return ResponseEntity.ok(dataStoreClient.getBookingsByUserId(UserId));
    }

    @DeleteMapping("/{bookingId}")
    public ResponseEntity<BookingDTOResponse> cancelBooking(@PathVariable Long bookingId) {
        return ResponseEntity.ok(dataStoreClient.cancelBooking(bookingId));
    }
}
