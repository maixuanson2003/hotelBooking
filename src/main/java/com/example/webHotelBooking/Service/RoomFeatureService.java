package com.example.webHotelBooking.Service;

import com.example.webHotelBooking.DTO.Request.RoomFeatureDTO;

import java.util.List;

public interface RoomFeatureService {
    public void addHotelRoomFeature(Long roomId, Long HotelId, RoomFeatureDTO roomFeatureDTO);
    public void addHotelRoomFeatureAll(RoomFeatureDTO roomFeatureDTO);
    public void deleteHotelRoomFeatur(Long roomId, Long HotelId,String roomFeatureName);
    public List<RoomFeatureDTO> getAllRoomFeatureByHotel(Long HotelId,String roomNumber);
    public List<RoomFeatureDTO> getAllRoomFeature();
}
