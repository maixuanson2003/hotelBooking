package com.example.webHotelBooking.Service.impl;

import com.example.webHotelBooking.Entity.Hotel;
import com.example.webHotelBooking.Entity.HotelImage;
import com.example.webHotelBooking.Repository.HotelImagRepository;
import com.example.webHotelBooking.Repository.HotelRepository;
import com.example.webHotelBooking.Repository.HotelRoomRepository;
import com.example.webHotelBooking.Service.HotelImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HotelImageServiceimpl implements HotelImageService {
    @Autowired
    private HotelImagRepository hotelImagRepository;
    @Autowired
    private HotelRepository hotelRepository;
    public void createHotelImage(String LinkImage,  Long HotelId) {
        Hotel hotel=hotelRepository.findById(HotelId).orElseThrow(()->new RuntimeException("not found"));
        HotelImage hotelImage=new HotelImage();
        hotelImage.setHotel(hotel);
        hotelImage.setLinkImage(LinkImage);
        hotelImagRepository.save(hotelImage);
    }

    @Override
    public void updateHotelImage(String LinkImage,  Long HotelId) {

    }
}
