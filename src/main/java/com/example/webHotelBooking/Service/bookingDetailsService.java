package com.example.webHotelBooking.Service;

import com.example.webHotelBooking.DTO.Request.BookingRequest;
import com.example.webHotelBooking.DTO.Request.RoombookRequest;
import com.example.webHotelBooking.DTO.Response.BookingDetailsResponse;

import java.time.LocalDate;
import java.util.List;

public interface bookingDetailsService {
    public void CreateBookingDetails(RoombookRequest typeRoom, Long HotelId, LocalDate CheckinDate, LocalDate CheckoutDate, String saleCode, Long bookingid);
    public void DeleteBookingDetails(String typeRoom,Long bookingId);
    public List<BookingDetailsResponse> getBookingDetailsByBooking(Long bookingId);
}
