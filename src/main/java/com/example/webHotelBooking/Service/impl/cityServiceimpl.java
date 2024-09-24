package com.example.webHotelBooking.Service.impl;

import com.example.webHotelBooking.DTO.Request.cityDTO;
import com.example.webHotelBooking.Entity.City;
import com.example.webHotelBooking.Entity.Hotel;
import com.example.webHotelBooking.Repository.CityRepository;
import com.example.webHotelBooking.Repository.HotelRepository;
import com.example.webHotelBooking.Service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class cityServiceimpl implements CityService {
    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private CityRepository cityRepository;
    @Override
    public City createCity(cityDTO city) {
        City city1=new City();
        city1.setCodeCity(city.getCodeCity());
        city1.setNameCity(city.getNameCity());
        city1.setDescrription(city.getDescrription());
        cityRepository.save(city1);
        return  city1;
    }

}
