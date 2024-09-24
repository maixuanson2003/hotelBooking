package com.example.webHotelBooking.Service;

import com.example.webHotelBooking.DTO.Request.cityDTO;
import com.example.webHotelBooking.Entity.City;
import com.example.webHotelBooking.Entity.Hotel;

public interface CityService  {
    public City createCity(cityDTO city);
}
