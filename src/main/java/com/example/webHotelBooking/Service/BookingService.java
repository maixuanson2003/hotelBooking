package com.example.webHotelBooking.Service;

import com.example.webHotelBooking.DTO.Request.BookingRequest;
import com.example.webHotelBooking.DTO.Response.BookingResponse;
import java.util.List;

public interface BookingService {
    public BookingResponse CreateBooking(BookingRequest bookingRequest,String username);
    public List<BookingResponse> GetAllBookingResponse();
    public List<BookingResponse> GetBookingByUser(String username);
    public BookingResponse GetBookingById(String username, Long bookingId);
    public void ChangeScheduleBooking(Long BookingId,String CheckoutDate,String CheckInDate);
    public void CancleBooking(Long BookingId,String username);
}
