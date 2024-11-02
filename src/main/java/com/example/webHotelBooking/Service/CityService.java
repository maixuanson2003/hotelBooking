package com.example.webHotelBooking.Service;

import com.example.webHotelBooking.DTO.Request.cityDTO;
import com.example.webHotelBooking.Entity.City;
import com.example.webHotelBooking.Entity.Hotel;

import java.util.List;

public interface CityService  {
    public void createCity(cityDTO city);
    public List<cityDTO> GetAllCity();
}
