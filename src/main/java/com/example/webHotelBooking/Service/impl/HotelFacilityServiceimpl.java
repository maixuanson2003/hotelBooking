package com.example.webHotelBooking.Service.impl;

import com.example.webHotelBooking.DTO.Response.HotelFacilityDTO;
import com.example.webHotelBooking.Entity.Hotel;
import com.example.webHotelBooking.Entity.HotelFacility;
import com.example.webHotelBooking.Entity.HotelPolicy;
import com.example.webHotelBooking.Repository.HotelFacilityRepository;
import com.example.webHotelBooking.Repository.HotelPolicyRepository;
import com.example.webHotelBooking.Repository.HotelRepository;
import com.example.webHotelBooking.Service.HotelFacilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HotelFacilityServiceimpl implements HotelFacilityService {
    @Autowired
    private HotelFacilityRepository hotelFacilityRepository;
    @Autowired
    private HotelRepository hotelRepository;
    @Override
    public void createHotelFacility(HotelFacilityDTO hotelFacilityDTO, Long HotelId) {
        Hotel hotel=hotelRepository.findById(HotelId).orElseThrow(()->new RuntimeException("not found"));
        List<HotelFacility> hoteFacilities=hotelFacilityRepository.findAll();

        if (hoteFacilities.size()!=0){
            boolean Check=false;
            for (HotelFacility hotelFacility:hoteFacilities){
                if (hotelFacility.getNameHotelAmenities().equals(hotelFacilityDTO.getNameHotelAmenities())){
                    Check=true;
                    break;
                }
            }
            if (Check){
                for (HotelFacility hotelFacility:hoteFacilities){
                    if (hotelFacility.getNameHotelAmenities().equals(hotelFacilityDTO.getNameHotelAmenities())){
                        List<Hotel>  hotelList1=hotelFacility.getHotel();
                        boolean Check2=false;
                        for (Hotel hotel1:hotelList1){
                            if (hotel1.getId().equals(HotelId)){
                                Check2=true;
                            }
                        }
                        if (!Check2){
                            hotelList1.add(hotel);
                            hotelFacility.setHotel(hotelList1);
                            hotelFacilityRepository.save(hotelFacility);
                        }
                    }
                }

            }else {
                List<Hotel> hotelList=new ArrayList<>();
                hotelList.add(hotel);
                HotelFacility hotelFacility=new HotelFacility().builder()
                        .nameHotelAmenities(hotelFacilityDTO.getNameHotelAmenities())
                        .desCription(hotelFacilityDTO.getDesCription())
                        .Hotel(hotelList)
                        .build();
                hotelFacilityRepository.save(hotelFacility);

            }
        } else {
            List<Hotel> hotelList=new ArrayList<>();
            hotelList.add(hotel);
            HotelFacility hotelFacility=new HotelFacility().builder()
                    .nameHotelAmenities(hotelFacilityDTO.getNameHotelAmenities())
                    .desCription(hotelFacilityDTO.getDesCription())
                    .Hotel(hotelList)
                    .build();
            hotelFacilityRepository.save(hotelFacility);
        }
    }

    @Override
    public void updateHotelFacility(HotelFacilityDTO hotelFacilityDTO, Long HotelId) {

    }
}
