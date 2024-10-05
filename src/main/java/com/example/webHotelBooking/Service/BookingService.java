package com.example.webHotelBooking.Service;

import com.example.webHotelBooking.DTO.Request.BookingRequest;
import com.example.webHotelBooking.DTO.Response.BookingResponse;
import com.example.webHotelBooking.DTO.Response.MyApiResponse;

import java.time.LocalDate;
import java.util.List;

public interface BookingService {
    public BookingResponse CreateBooking(BookingRequest bookingRequest,String username);
    public List<BookingResponse> GetAllBookingResponse();
    public List<BookingResponse> GetBookingByUser(String username);
    public BookingResponse GetBookingById(String username, Long bookingId);
    public MyApiResponse ChangeScheduleBooking(Long BookingId, LocalDate CheckoutDate, LocalDate CheckInDate, String username);

}
