package com.example.webHotelBooking.config;

import com.example.webHotelBooking.Entity.City;
import com.example.webHotelBooking.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

//@Component
//public class DataSeed  implements CommandLineRunner {
//    @Autowired
//    private userRepository userRepository;
//    @Autowired
//    private CityRepository cityRepository;
//    @Override
//    public void run(String... args) throws Exception {
//        LoadCity();
//
//    }
//    private void LoadUser(){
//
//    }
//    private void LoadCity(){
//        City city=new City(1L,29L,"Hà Nội nghìn năm văn hiến","Hà Nội");
//        cityRepository.save(city);
//    }
//    private void LoadHotel(){}
//}
