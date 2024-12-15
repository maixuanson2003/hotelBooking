package com.example.webHotelBooking.Service;

import com.example.webHotelBooking.DTO.Response.CheckPolicy;
import com.example.webHotelBooking.DTO.Response.HotelFacilityDetailsDTO;
import com.example.webHotelBooking.DTO.Response.HotelPolicyDetailsDTO;

import java.util.List;

public interface HotelFacilityDetailService {
    public void CreateHotelFacilityDetails(HotelFacilityDetailsDTO hotelFacilityDetailsDTO, Long HotelId);
    public List<HotelFacilityDetailsDTO> GetAllHotelFacilityDetils();
    public List<HotelFacilityDetailsDTO> GetAllHotelFacilityDetilsByHotelId(Long HotelId);
    public void DeleteHotelFacilityDetails(Long id);
    public void UpdateHotelFacilityDetails(Long HotelId,Long FacilityDetailsId,HotelFacilityDetailsDTO hotelFacilityDetailsDTO);
}
