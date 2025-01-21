package com.tfa.bookings.Service;

import com.tfa.bookings.DTO.BookingDTO;
import com.tfa.bookings.DTO.BookingDTOResponse;
import com.tfa.bookings.DTO.FlightDTO;
import com.tfa.bookings.DTO.UserDTO;
import com.tfa.bookings.Service.Interfaces.BookingService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Service
public class DataStoreClient implements BookingService {

    private static final Logger logger = LogManager.getLogger(DataStoreClient.class);

    @Autowired
    private RestTemplate restTemplate;
    @Value("${datastore.service.url}")
    private String dataStoreURL;
    //    @Value("${user.service.url}")
//    private String userURL;
//    @Value("${flight.service.url}")
    private String flightURL;

    public BookingDTOResponse createBooking(BookingDTO bookingDTO) {
//        Long userId = bookingDTO.getUser_id();
//        String userUrl = dataStoreURL + "/users/" + userId;
//        UserDTO userDetails = restTemplate.getForObject(userUrl, UserDTO.class);
//        if (userDetails == null || !userDetails.getId().equals(userId)) {
//            logger.error("User validation failed for userId: {}", userId);
//            throw new RuntimeException(" Response is null or UserId is invalid");
//        }

        // Check available seats before making a booking
        Long flightId = bookingDTO.getFlight_id();
        String flightUrl = dataStoreURL + "/flights/" + flightId;
        // Get flight details and validate
        FlightDTO flightDetails = restTemplate.getForObject(flightUrl, FlightDTO.class);
        if (flightDetails == null || !flightDetails.getId().equals(flightId)) {
            logger.error("User validation failed for flightId: {}", flightId);
            throw new RuntimeException(" Response is null or flightId is invalid");
        }
        if (flightDetails.getAvailableSeats() <= 0) {
            throw new RuntimeException("Flight is fully booked!");
        }
// Reduce available seats and update flight
        flightDetails.setAvailableSeats(flightDetails.getAvailableSeats() - 1);
        restTemplate.put(flightUrl, flightDetails);

// Map DTO to entity

//BookingDTOResponse bookingDTO1=new BookingDTOResponse();
//bookingDTO1.setUser_id(bookingDTO.getUser_id());
//        bookingDTO1.setFlight_id(bookingDTO.getFlight_id());
        // Create booking

        // Create booking
        BookingDTOResponse bookingdto = restTemplate.postForObject(dataStoreURL + "/bookings", bookingDTO, BookingDTOResponse.class);
        System.out.println(bookingdto);
        return bookingdto;
    }


    public BookingDTOResponse getBooking(Long id) {
        BookingDTOResponse response = restTemplate.getForObject(dataStoreURL + "/bookings/" + id, BookingDTOResponse.class);
        if (response == null || !response.getId().equals(id)) {
            throw new RuntimeException("Failed to retrieve booking details , received null response");

        }
        logger.info("Received User by Id {} ", response.getId());

        return restTemplate.getForObject(dataStoreURL + "/bookings/" + id, BookingDTOResponse.class);


    }

    public List<BookingDTOResponse> getBookingsByUserId(Long UserId) {
        BookingDTOResponse[] bookingDTOResponseList;
        try {
            bookingDTOResponseList = restTemplate.getForObject(dataStoreURL + "/bookings/users/" + UserId, BookingDTOResponse[].class);

            if (bookingDTOResponseList == null || bookingDTOResponseList.length == 0) {

                logger.warn("No bookings found");
                return Collections.emptyList();
            }
        } catch (RestClientException e) {
            // Handle HTTP/communication errors
            logger.error("Error retrieving all Booking", e);
            throw new RuntimeException("Failed to retrieve bookings due to network issues", e);
        } catch (Exception e) {
            // Handle other exceptions
            logger.error("Unexpected error during retrieving all bookings ", e);
            throw new RuntimeException("Unexpected error occurred while retrieving bookings ", e);
        }

        return List.of(bookingDTOResponseList);
    }

    public BookingDTOResponse cancelBooking(Long id) {

        BookingDTOResponse bookingDTOResponse;
        try {
            // Use RestTemplate to send a PUT request to update the status
            bookingDTOResponse = restTemplate.exchange(
                    dataStoreURL + "/bookings/" + id,
                    HttpMethod.DELETE,
                    null,
                    BookingDTOResponse.class
            ).getBody();

            // Return the response received from Datastore service
            if (bookingDTOResponse == null) {
                throw new RuntimeException("Failed to cancel booking. Empty response from Datastore service.");
            }

            return bookingDTOResponse;
        } catch (RestClientException e) {
            // Handle HTTP/communication errors
            logger.error("Error canceling booking with ID: {}", id, e);
            throw new RuntimeException("Failed to cancel booking due to network or server issues.", e);
        }


    }

}