package com.example.webHotelBooking.Service;

import com.example.webHotelBooking.DTO.Response.MyApiResponse;
import com.example.webHotelBooking.DTO.Response.bookingChangeDetailsDto;
import com.example.webHotelBooking.Entity.bookingChangeDetails;

import java.util.List;

public interface BookingChangeDetailsService {
    public MyApiResponse setStatusBookingDetailsService(Long booKingChangeId,Long bookingid,String username,String status);
    public List<bookingChangeDetailsDto> getListBookingChange();
    public List<bookingChangeDetailsDto>getListBookingChangeByUser(String userName);
    public List<bookingChangeDetailsDto> getListBookingChangeByHotel(Long HotelId);
}
