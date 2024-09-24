package com.example.webHotelBooking.Service;

import com.example.webHotelBooking.DTO.Response.HotelPolicyDTO;
import com.example.webHotelBooking.Entity.Hotel;

import java.util.List;

public interface HotelPolicyService {
    public void createHotelPolicy(HotelPolicyDTO hotelPolicyDTO, Long HotelId);
    public void updateHotelPolicy(HotelPolicyDTO hotelPolicyDTO,Long HotelPolicyId);
    public String deletePolicyById(Long hotelPolicyId,Long PolicyId);
    public  List<HotelPolicyDTO>  getHotelPolicyByHotel(Long HotelId);
    public List<HotelPolicyDTO> getAllHotelPolicy();
}
