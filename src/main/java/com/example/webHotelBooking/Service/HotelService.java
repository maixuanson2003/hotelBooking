package com.example.webHotelBooking.Service;

import com.example.webHotelBooking.DTO.Request.HotelRequest;
import com.example.webHotelBooking.DTO.Request.HotelSearch;
import com.example.webHotelBooking.DTO.Request.RoomRequest;
import com.example.webHotelBooking.DTO.Response.HotelResonse;
import com.example.webHotelBooking.Entity.HotelPolicy;

import java.util.List;

public interface HotelService {
    public List<HotelResonse> GetAllHotel();
    public HotelResonse GetHotelById(Long id);
    public List<HotelResonse> GetHotelsuit(String address, RoomRequest roomRequest);
    public List<HotelResonse> GetHotelByCodition(List<String> hotelPolicyList, String address, RoomRequest roomRequest,Integer starpoint);
    public List<HotelResonse> SortByPrice();
    public void createHotel(HotelRequest hotelRequest);
    public HotelResonse Update(Long HotelId,HotelRequest hotelRequest);
    public void DeleteHotel(Long id);
    public void deleteHotelByName(String name);
}
