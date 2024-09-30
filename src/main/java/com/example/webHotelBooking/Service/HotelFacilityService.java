package com.example.webHotelBooking.Service;

import com.example.webHotelBooking.DTO.Response.HotelFacilityDTO;
import com.example.webHotelBooking.Entity.Hotel;

import java.util.List;

public interface HotelFacilityService {
    public void createHotelFacility(HotelFacilityDTO hotelFacilityDTO,Long HotelId);
    public void updateHotelFacility(HotelFacilityDTO hotelFacilityDTO,Long HotelFacilityId);
    public String deleteFacilityHotel(Long HotelId,Long FacilityId);
    public List<HotelFacilityDTO> GetAllHotelFacilityByHotel(Long HotelId);
    public List<HotelFacilityDTO> GetAllHotelFacility();

}
