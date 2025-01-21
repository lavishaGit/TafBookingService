package com.tfa.bookings.Service.Interfaces;

import com.tfa.bookings.DTO.BookingDTO;
import com.tfa.bookings.DTO.BookingDTOResponse;

import java.util.List;

public interface BookingService {
    public BookingDTOResponse createBooking(BookingDTO bookingDTO);
    public BookingDTOResponse getBooking(Long id);
    public List<BookingDTOResponse> getBookingsByUserId(Long UserId);
    public BookingDTOResponse cancelBooking(Long id);
}
