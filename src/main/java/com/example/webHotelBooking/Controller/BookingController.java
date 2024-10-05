package com.example.webHotelBooking.Controller;

import com.example.webHotelBooking.DTO.Request.BookingRequest;
import com.example.webHotelBooking.DTO.Response.BookingResponse;
import com.example.webHotelBooking.DTO.Response.MyApiResponse;
import com.example.webHotelBooking.Service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    // Tạo booking mới
    @PostMapping("/create")
    public ResponseEntity<BookingResponse> createBooking(@RequestBody BookingRequest bookingRequest, @RequestParam String username) {
        BookingResponse bookingResponse = bookingService.CreateBooking(bookingRequest, username);
        return new ResponseEntity<>(bookingResponse, HttpStatus.CREATED);
    }

    // Lấy tất cả các booking
    @GetMapping("/all")
    public ResponseEntity<List<BookingResponse>> getAllBookings() {
        List<BookingResponse> bookingResponses = bookingService.GetAllBookingResponse();
        return new ResponseEntity<>(bookingResponses, HttpStatus.OK);
    }

    // Lấy booking theo người dùng
    @GetMapping("/user/{username}")
    public ResponseEntity<List<BookingResponse>> getBookingsByUser(@PathVariable String username) {
        List<BookingResponse> bookingResponses = bookingService.GetBookingByUser(username);
        return new ResponseEntity<>(bookingResponses, HttpStatus.OK);
    }

    // Lấy booking theo ID
    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingResponse> getBookingById(@RequestParam String username, @PathVariable Long bookingId) {
        BookingResponse bookingResponse = bookingService.GetBookingById(username, bookingId);
        return new ResponseEntity<>(bookingResponse, HttpStatus.OK);
    }
    @PostMapping("/change-schedule/{bookingId}")
    public ResponseEntity<MyApiResponse> changeScheduleBooking(
            @PathVariable Long bookingId,
            @RequestParam LocalDate checkInDate,
            @RequestParam LocalDate checkOutDate,
            @RequestParam String username) {
        MyApiResponse response = bookingService.ChangeScheduleBooking(bookingId, checkOutDate, checkInDate, username);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    // Thay đổi lịch trình booking
//    @PutMapping("/change-schedule/{bookingId}")
//    public ResponseEntity<Void> changeScheduleBooking(@PathVariable Long bookingId, @RequestParam String checkoutDate, @RequestParam String checkinDate) {
//        bookingService.ChangeScheduleBooking(bookingId, checkoutDate, checkinDate);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
//
//    // Hủy booking
//    @DeleteMapping("/cancel/{bookingId}")
//    public ResponseEntity<Void> cancelBooking(@PathVariable Long bookingId, @RequestParam String username) {
//        bookingService.CancleBooking(bookingId, username);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
}
