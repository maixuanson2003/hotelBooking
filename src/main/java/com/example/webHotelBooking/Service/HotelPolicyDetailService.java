package com.example.webHotelBooking.Service;

import com.example.webHotelBooking.DTO.Response.HotelPolicyDetailsDTO;

import java.util.List;

public interface HotelPolicyDetailService {
    public void CreateHotelPolicyDetails(HotelPolicyDetailsDTO hotelPolicyDetailsDTO,Long HotelId);
    public List<HotelPolicyDetailsDTO> GetAllHotelPolicyDetils();
    public List<HotelPolicyDetailsDTO> GetAllHotelPolicyByHotelId(Long HotelId);
    public void DeleteHotelPolicyDetails(Long id);
    public void UpdateHotelpolicyDetails(Long HotelId,Long PolicyDetailsId,HotelPolicyDetailsDTO hotelPolicyDetailsDTO);
}
