package com.example.webHotelBooking.Controller;

import com.example.webHotelBooking.DTO.Response.MyApiResponse;
import com.example.webHotelBooking.DTO.Response.bookingChangeDetailsDto;
import com.example.webHotelBooking.Service.BookingChangeDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class bookingChangeController {
    @Autowired
    private BookingChangeDetailsService bookingChangeService;

    // API để thay đổi trạng thái booking change details
    @PutMapping("/change-status/{bookingChangeId}/{bookingId}")
    public ResponseEntity<MyApiResponse> setStatusBookingDetailsService(
            @PathVariable Long bookingChangeId,
            @PathVariable Long bookingId,
            @RequestParam String username,
            @RequestParam String status,
            @RequestParam boolean Check
    ) {

        MyApiResponse response = bookingChangeService.setStatusBookingDetailsService(bookingChangeId, bookingId, username, status,Check);
        return ResponseEntity.ok(response);
    }

    // API để lấy danh sách booking change details
    @GetMapping("/change-list")
    public ResponseEntity<List<bookingChangeDetailsDto>> getListBookingChange() {
        List<bookingChangeDetailsDto> changeDetailsList = bookingChangeService.getListBookingChange();
        return ResponseEntity.ok(changeDetailsList);
    }

    // API để lấy danh sách booking change details theo username
    @GetMapping("/change-list/user")
    public ResponseEntity<List<bookingChangeDetailsDto>> getListBookingChangeByUser(@RequestParam String username) {
        List<bookingChangeDetailsDto> changeDetailsList = bookingChangeService.getListBookingChangeByUser(username);
        return ResponseEntity.ok(changeDetailsList);
    }

    // API để lấy danh sách booking change details theo hotelId
    @GetMapping("/change-list/hotel/{hotelId}")
    public ResponseEntity<List<bookingChangeDetailsDto>> getListBookingChangeByHotel(@PathVariable Long hotelId) {
        List<bookingChangeDetailsDto> changeDetailsList = bookingChangeService.getListBookingChangeByHotel(hotelId);
        return ResponseEntity.ok(changeDetailsList);
    }
}
