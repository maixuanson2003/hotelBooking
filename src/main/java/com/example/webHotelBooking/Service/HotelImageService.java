package com.example.webHotelBooking.Service;

import com.example.webHotelBooking.DTO.Response.HotelFacilityDTO;
import com.example.webHotelBooking.Entity.Hotel;

public interface HotelImageService {
    public void createHotelImage(String LinkImage, Long HotelId);
    public void updateHotelImage(String LinkImage,  Long HotelId);
}
