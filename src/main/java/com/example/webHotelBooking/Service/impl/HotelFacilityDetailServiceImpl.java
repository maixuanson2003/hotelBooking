package com.example.webHotelBooking.Service.impl;

import com.example.webHotelBooking.DTO.Response.HotelFacilityDetailsDTO;
import com.example.webHotelBooking.DTO.Response.HotelPolicyDetailsDTO;
import com.example.webHotelBooking.Repository.*;
import com.example.webHotelBooking.Entity.*;

import com.example.webHotelBooking.Exception.ResourceNotFoundException;
import com.example.webHotelBooking.Service.HotelFacilityDetailService;
import com.example.webHotelBooking.Service.HotelFacilityService;
import com.example.webHotelBooking.Service.HotelPolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class HotelFacilityDetailServiceImpl implements HotelFacilityDetailService {
    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private HotelFacilityDetaiRepository hotelFacilityDetaiRepository;
    @Autowired
    private HotelFacilityService hotelFacilityService;
    @Autowired
    private HotelFacilityRepository hotelFacilityRepository;
    @Override
    public void CreateHotelFacilityDetails(HotelFacilityDetailsDTO hotelFacilityDetailsDTO, Long HotelId) {
        boolean CheckExsist=false;
        List<HotelFacility> hotelFacilityList=hotelFacilityRepository.findAll();
        Hotel hotel=hotelRepository.findById(HotelId).orElseThrow(()->new ResourceNotFoundException("not found"));
        for (HotelFacility hotelFacility:hotelFacilityList){
            if (hotelFacility.getNameHotelFacility().equals(hotelFacilityDetailsDTO.getNameHotelFacility())){
                CheckExsist=true;
            }
        }
        HotelFacility hotelFacility=null;
        if (!CheckExsist){
            hotelFacilityService.createHotelFacility(hotelFacilityDetailsDTO.getNameHotelFacility());
            hotelFacility=hotelFacilityRepository.findHotelPolicyByNameHotelFacility(hotelFacilityDetailsDTO.getNameHotelFacility());

        }
        hotelFacility=hotelFacilityRepository.findHotelPolicyByNameHotelFacility(hotelFacilityDetailsDTO.getNameHotelFacility());
        HotelFacilityDetails hotelFacilityDetails=new HotelFacilityDetails().builder()
                .desCription(hotelFacilityDetailsDTO.getDesCription())
                .hotelFacility(hotelFacility)
                .hotel(hotel)
                .build();
        hotelFacilityDetaiRepository.save(hotelFacilityDetails);
    }

    @Override
    public List<HotelFacilityDetailsDTO> GetAllHotelFacilityDetils() {
        List<HotelFacilityDetails> hotelFacilityDetailsList=hotelFacilityDetaiRepository.findAll();
        List<HotelFacilityDetailsDTO> hotelFacilityDetailsDTOS=new ArrayList<>();
        for (HotelFacilityDetails hotelFacilityDetails:hotelFacilityDetailsList){
            hotelFacilityDetailsDTOS.add(new HotelFacilityDetailsDTO(hotelFacilityDetails));
        }
        return hotelFacilityDetailsDTOS;
    }

    @Override
    public List<HotelFacilityDetailsDTO> GetAllHotelFacilityDetilsByHotelId(Long HotelId) {
        Hotel hotel=hotelRepository.findById(HotelId).orElseThrow(()->new ResourceNotFoundException("not found"));
        List<HotelFacilityDetails>  hotelFacilityDetailsList=hotel.getHotelFacilityDetailsList();
        List<HotelFacilityDetailsDTO> hotelFacilityDetailsDTOS=new ArrayList<>();
        for (HotelFacilityDetails hotelFacilityDetails:hotelFacilityDetailsList){
            hotelFacilityDetailsDTOS.add(new HotelFacilityDetailsDTO(hotelFacilityDetails));
        }
        return hotelFacilityDetailsDTOS;
    }

    @Override
    public void DeleteHotelFacilityDetails(Long id) {
        HotelFacilityDetails hotelFacilityDetails=hotelFacilityDetaiRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("not found"));
        hotelFacilityDetaiRepository.delete(hotelFacilityDetails);
    }

    @Override
    public void UpdateHotelFacilityDetails(Long HotelId, Long FacilityDetailsId, HotelFacilityDetailsDTO hotelFacilityDetailsDTO) {
        Hotel hotel=hotelRepository.findById(HotelId).orElseThrow(()->new ResourceNotFoundException("not found"));
        List<HotelFacilityDetails> hotelFacilityDetailsList=hotel.getHotelFacilityDetailsList();
        HotelFacilityDetails hotelFacilityDetails =hotelFacilityDetailsList.stream()
                .filter(details -> details.getId().equals(FacilityDetailsId))
                .findFirst()
                .orElseThrow(()->new ResourceNotFoundException("not found"));
        hotelFacilityDetails.setDesCription(hotelFacilityDetailsDTO.getDesCription());
        hotelFacilityDetaiRepository.save(hotelFacilityDetails);

    }
}
