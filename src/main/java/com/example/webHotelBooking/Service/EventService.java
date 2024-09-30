package com.example.webHotelBooking.Service;

import com.example.webHotelBooking.DTO.Request.EventDTO;

import java.util.List;

public interface EventService {
    public void addEventToCity(Long cityId, EventDTO eventDTO);
    public String DeleteEventFromCity(Long cityId,Long EventID);
    public void UpdateEvent(Long EventId,EventDTO eventDTO);
    public List<EventDTO> GetAllEventByCity(Long CityId);
    public List<EventDTO> GetAllEvent();
    public List<EventDTO> SearChEvent(String DateStart,String DateEnd,Long CityId);

}
