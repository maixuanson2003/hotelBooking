package com.example.webHotelBooking.Service;

import com.example.webHotelBooking.DTO.Response.HotelRoomDTO;
import com.example.webHotelBooking.Entity.Hotel;

public interface HotelRoomService {
    public void CreateRoomHotel(HotelRoomDTO hotelRoomDTO, Long HotelID);
    public void UpdateRoomHotel(HotelRoomDTO hotelRoomDTO, Long HotelID);
}
