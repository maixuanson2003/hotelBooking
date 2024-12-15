package com.example.webHotelBooking.Service;

import com.example.webHotelBooking.DTO.Response.HotelFacilityDTO;
import com.example.webHotelBooking.Entity.Hotel;
import com.example.webHotelBooking.Entity.HotelFacility;

import java.util.List;

public interface HotelFacilityService {
    public void createHotelFacility(String nameHotelFacility);
    public List<HotelFacilityDTO> GetAllFacility();
    public void updateHotelFacility(String nameHotelPolicy,Long hotelPolicyId);
    public String deletePolicyById(Long PolicyId);
}
