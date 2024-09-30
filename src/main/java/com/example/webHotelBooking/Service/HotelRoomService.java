package com.example.webHotelBooking.Service;

import com.example.webHotelBooking.DTO.Response.HotelRoomDTO;
import com.example.webHotelBooking.Entity.Hotel;

import java.util.List;

public interface HotelRoomService {
    public void CreateRoomHotel(HotelRoomDTO hotelRoomDTO, Long HotelID);
    public void UpdateRoomHotel(HotelRoomDTO hotelRoomDTO, Long HotelID,Long roomId);
    public void DeleteRoomHotelByRoomNumber(Long HotelId,String roomNumber);
    public List<HotelRoomDTO> GetAllHotelRoomByHotelId(Long HotelId);
    public List<HotelRoomDTO> searChRoomByCodition(List<String> NameRoomFeature,Long priceStart,Long PriceEnd,String roomType,Long HotelId);
}
