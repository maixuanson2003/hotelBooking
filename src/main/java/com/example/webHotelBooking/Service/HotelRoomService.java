package com.example.webHotelBooking.Service;

import com.example.webHotelBooking.DTO.Request.HotelRoomRequest;
import com.example.webHotelBooking.DTO.Response.HotelRoomDTO;
import com.example.webHotelBooking.Entity.Hotel;

import java.util.List;

public interface HotelRoomService {
    public void CreateRoomHotel(HotelRoomDTO hotelRoomDTO, Long HotelID);
    public void CreateRoomHotelForHotel(HotelRoomRequest request, Long HotelID);

    public void UpdateRoomHotel(HotelRoomRequest request, Long HotelID, Long roomId);

    public void DeleteRoomHotelByRoomType(Long HotelId, String roomNumber);
    public void DeleteRoomHotelByHotel(Hotel hotel);

    public List<HotelRoomDTO> GetAllHotelRoomByHotelId(Long HotelId);

    public List<HotelRoomDTO> searChRoomByCodition(List<String> NameRoomFeature, Long priceStart, Long PriceEnd, Long HotelId);

    public List<HotelRoomDTO> GetRoomByHotel(String token);

    public void setAmountRoom(String TypeRoom, Long HotelId, int Amount);
}
