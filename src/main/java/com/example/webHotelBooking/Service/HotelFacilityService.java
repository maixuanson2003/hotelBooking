package com.example.webHotelBooking.Service;

import com.example.webHotelBooking.DTO.Response.HotelFacilityDTO;
import com.example.webHotelBooking.Entity.Hotel;

public interface HotelFacilityService {
    public void createHotelFacility(HotelFacilityDTO hotelFacilityDTO,Long HotelId);
    public void updateHotelFacility(HotelFacilityDTO hotelFacilityDTO,Long HotelId);

}
