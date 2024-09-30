package com.example.webHotelBooking.Service.impl;

import com.example.webHotelBooking.DTO.Request.cityDTO;
import com.example.webHotelBooking.Entity.City;
import com.example.webHotelBooking.Entity.Hotel;
import com.example.webHotelBooking.Exception.DuplicateRecordException;
import com.example.webHotelBooking.Repository.CityRepository;
import com.example.webHotelBooking.Repository.HotelRepository;
import com.example.webHotelBooking.Service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class cityServiceimpl implements CityService {
    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private CityRepository cityRepository;
    @Override
    public void createCity(cityDTO city) {
        Optional<City> city1= Optional.ofNullable(cityRepository.findCityByNameCity(city.getNameCity()));
        if (!city1.isPresent()){
            City city2=new City();
            city2.setCodeCity(city.getCodeCity());
            city2.setNameCity(city.getNameCity());
            city2.setDescrription(city.getDescrription());
            cityRepository.save(city2);

        }else {
            throw new DuplicateRecordException("cityItsPrensnt");
        }
    }
}
