package com.example.webHotelBooking.Service.impl;

import com.example.webHotelBooking.DTO.Response.HotelFacilityDTO;

import com.example.webHotelBooking.Entity.Hotel;
import com.example.webHotelBooking.Entity.HotelFacility;
import com.example.webHotelBooking.Entity.HotelPolicy;
import com.example.webHotelBooking.Exception.DuplicateRecordException;
import com.example.webHotelBooking.Exception.ResourceNotFoundException;
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
    public void createHotelFacility(String nameHotelFacility) {
        boolean CheckExsits=false;
        List<HotelFacility> hotelFacilityList=hotelFacilityRepository.findAll();
        for (HotelFacility hotelFacility:hotelFacilityList){
            if (hotelFacility.getNameHotelFacility().equals(nameHotelFacility)){
                CheckExsits=true;
            }
        }
        if (CheckExsits) throw new DuplicateRecordException("exsits");
       HotelFacility hotelFacility=new HotelFacility().builder()
               .nameHotelFacility(nameHotelFacility)
               .build();
        hotelFacilityRepository.save(hotelFacility);
    }

    @Override
    public List<HotelFacilityDTO> GetAllFacility() {
        List<HotelFacility> hotelFacilityList=hotelFacilityRepository.findAll();
        List<HotelFacilityDTO> hotelFacilityDTOList=new ArrayList<>();
        for (HotelFacility hotelFacility :hotelFacilityList){
            HotelFacilityDTO hotelFacilityDTO=new HotelFacilityDTO().builder()
                    .id(hotelFacility.getId())
                    .nameHotelFacility(hotelFacility.getNameHotelFacility())
                    .build();
            hotelFacilityDTOList.add(hotelFacilityDTO);

        }
        return  hotelFacilityDTOList;
    }

    @Override
    public void updateHotelFacility(String nameHotelPolicy,Long hotelPolicyId) {
        HotelFacility hotelFacility=hotelFacilityRepository.findById(hotelPolicyId).orElseThrow(()->new ResourceNotFoundException("not found"));
        hotelFacility.setNameHotelFacility(nameHotelPolicy);
        hotelFacilityRepository.save(hotelFacility);

    }

    @Override
    public String deletePolicyById(Long PolicyId) {
        hotelFacilityRepository.deleteById(PolicyId);
        if (hotelFacilityRepository.existsById(PolicyId)){
            return  "faild";
        }else {
            return "Success";
        }
    }
}
