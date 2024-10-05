package com.example.webHotelBooking.Controller;

import com.example.webHotelBooking.DTO.Response.BookingDetailsResponse;
import com.example.webHotelBooking.Service.impl.BookingDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/booking-details")
public class BookingDetailsController {

    @Autowired
    private BookingDetailsServiceImpl bookingDetailsService;
    @GetMapping("/get/{bookingId}")
    public ResponseEntity<List<BookingDetailsResponse>> getBookingDetailsByBooking(@PathVariable Long bookingId) {
        try {
            List<BookingDetailsResponse> bookingDetails = bookingDetailsService.getBookingDetailsByBooking(bookingId);
            return ResponseEntity.ok(bookingDetails);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }



}

