package com.example.webHotelBooking.Service.impl;

import com.example.webHotelBooking.DTO.Response.HotelPolicyDetailsDTO;
import com.example.webHotelBooking.Entity.Hotel;
import com.example.webHotelBooking.Entity.HotelPolicy;
import com.example.webHotelBooking.Entity.HotelPolicyDetails;
import com.example.webHotelBooking.Exception.ResourceNotFoundException;
import com.example.webHotelBooking.Repository.HotelPolicyDetailsRepository;
import com.example.webHotelBooking.Repository.HotelPolicyRepository;
import com.example.webHotelBooking.Repository.HotelRepository;
import com.example.webHotelBooking.Service.HotelPolicyDetailService;
import com.example.webHotelBooking.Service.HotelPolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class HotelPolicyDetailsServiceimpl implements HotelPolicyDetailService {
    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private HotelPolicyDetailsRepository hotelPolicyDetailsRepository;
    @Autowired
    private HotelPolicyService hotelPolicyService;
    @Autowired
    private HotelPolicyRepository hotelPolicyRepository;

    @Override
    public void CreateHotelPolicyDetails(HotelPolicyDetailsDTO hotelPolicyDetailsDTO,Long HotelId) {
        boolean CheckExsist=false;
        List<HotelPolicy> hotelPolicyList=hotelPolicyRepository.findAll();
        Hotel hotel=hotelRepository.findById(HotelId).orElseThrow(()->new ResourceNotFoundException("not found"));
        for (HotelPolicy hotelPolicy:hotelPolicyList){
            if (hotelPolicy.getNamePolicy().equals(hotelPolicyDetailsDTO.getNamePolicy())){
                CheckExsist=true;
            }
        }
        HotelPolicy hotelPolicy=null;
        if (!CheckExsist){
            hotelPolicyService.createHotelPolicy(hotelPolicyDetailsDTO.getNamePolicy());
            hotelPolicy=hotelPolicyRepository.findHotelPolicyByNamePolicy(hotelPolicyDetailsDTO.getNamePolicy());

        }
        hotelPolicy=hotelPolicyRepository.findHotelPolicyByNamePolicy(hotelPolicyDetailsDTO.getNamePolicy());
        Long hotelFee=null;
        String coditionalInfo=null;
        if (hotelPolicyDetailsDTO.getFee()!=0){
            hotelFee=hotelPolicyDetailsDTO.getFee();

        }
        if (hotelPolicyDetailsDTO.getCoditionalInfo().equals(" ")){
            coditionalInfo=null;
        }
        HotelPolicyDetails hotelPolicyDetails=new HotelPolicyDetails().builder()
                .hotel(hotel)
                .fee(hotelFee)
                .coditionalInfo(coditionalInfo)
                .description(hotelPolicyDetailsDTO.getDescription())
                .hotelPolicy(hotelPolicy)
                .build();
        hotelPolicyDetailsRepository.save(hotelPolicyDetails);
    }

    @Override
    public List<HotelPolicyDetailsDTO> GetAllHotelPolicyDetils() {
        List<HotelPolicyDetails> hotelPolicyDetailsList=hotelPolicyDetailsRepository.findAll();
        List<HotelPolicyDetailsDTO> hotelPolicyDetailsDTOS=new ArrayList<>();
        for (HotelPolicyDetails hotelPolicyDetails:hotelPolicyDetailsList){
            hotelPolicyDetailsDTOS.add(new HotelPolicyDetailsDTO(hotelPolicyDetails));
        }
        return hotelPolicyDetailsDTOS;
    }

    @Override
    public List<HotelPolicyDetailsDTO> GetAllHotelPolicyByHotelId(Long HotelId) {
        Hotel hotel=hotelRepository.findById(HotelId).orElseThrow(()->new ResourceNotFoundException("not found"));
        List<HotelPolicyDetails> hotelPolicyDetailsList=hotel.getHotelPolicyDetailsList();
        List<HotelPolicyDetailsDTO> hotelPolicyDetailsDTOS=new ArrayList<>();
        for (HotelPolicyDetails hotelPolicyDetails:hotelPolicyDetailsList){
            hotelPolicyDetailsDTOS.add(new HotelPolicyDetailsDTO(hotelPolicyDetails));
        }
        return hotelPolicyDetailsDTOS;
    }

    @Override
    public void DeleteHotelPolicyDetails(Long id) {
        HotelPolicyDetails hotelPolicyDetails=hotelPolicyDetailsRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("not found"));
        hotelPolicyDetailsRepository.delete(hotelPolicyDetails);

    }

    @Override
    public void UpdateHotelpolicyDetails(Long HotelId, Long PolicyDetailsId,HotelPolicyDetailsDTO hotelPolicyDetailsDTO ) {
        Hotel hotel=hotelRepository.findById(HotelId).orElseThrow(()->new ResourceNotFoundException("not found"));
        List<HotelPolicyDetails> hotelPolicyDetailsList=hotel.getHotelPolicyDetailsList();
        HotelPolicyDetails hotelPolicyDetails =hotelPolicyDetailsList.stream()
              .filter(details -> details.getId().equals(PolicyDetailsId))
              .findFirst()
              .orElseThrow(()->new ResourceNotFoundException("not found"));
        hotelPolicyDetails.setDescription(hotelPolicyDetailsDTO.getDescription());
        hotelPolicyDetails.setFee(hotelPolicyDetails.getFee());
        hotelPolicyDetails.setCoditionalInfo(hotelPolicyDetails.getCoditionalInfo());
        hotelPolicyDetailsRepository.save(hotelPolicyDetails);

    }
}
