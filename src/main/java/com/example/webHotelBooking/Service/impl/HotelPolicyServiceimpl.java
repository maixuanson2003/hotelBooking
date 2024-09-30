package com.example.webHotelBooking.Service.impl;

import com.example.webHotelBooking.DTO.Response.HotelPolicyDTO;
import com.example.webHotelBooking.Entity.Hotel;
import com.example.webHotelBooking.Entity.HotelPolicy;
import com.example.webHotelBooking.Exception.ResourceNotFoundException;
import com.example.webHotelBooking.Repository.HotelPolicyRepository;
import com.example.webHotelBooking.Repository.HotelRepository;
import com.example.webHotelBooking.Repository.HotelRoomRepository;
import com.example.webHotelBooking.Service.HotelPolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HotelPolicyServiceimpl implements HotelPolicyService {
    @Autowired
    private HotelPolicyRepository hotelPolicyRepository;
    @Autowired
    private HotelRepository hotelRepository;
    @Override
    public void createHotelPolicy(HotelPolicyDTO hotelPolicyDTO, Long HotelId) {
        Hotel hotel=hotelRepository.findById(HotelId).orElseThrow(()->new RuntimeException("not found"));
        List<HotelPolicy> hotelPolicies=hotelPolicyRepository.findAll();
        if (hotelPolicies.size()!=0){
            boolean Check=false;
            for (HotelPolicy hotelPolicy:hotelPolicies){
                if (hotelPolicy.getNamePolicy().equals(hotelPolicyDTO.getNamePolicy())){
                    Check=true;
                    break;
                }
            }
            if (Check){
                for (HotelPolicy hotelPolicy:hotelPolicies){
                    if (hotelPolicy.getNamePolicy().equals(hotelPolicyDTO.getNamePolicy())){
                        List<Hotel> hotelList1=hotelPolicy.getHotelList();
                        boolean Check2=false;
                        for (Hotel hotel1:hotelList1){
                            if (hotel1.getId().equals(HotelId)){
                                Check2=true;
                            }
                        }
                        if (!Check2){
                            hotelList1.add(hotel);
                            hotelPolicyRepository.save(hotelPolicy);
                        }
                    }
                }
            }else {
                List<Hotel> hotelList=new ArrayList<>();
                hotelList.add(hotel);
                HotelPolicy hotelPolicy=new HotelPolicy().builder()
                        .namePolicy(hotelPolicyDTO.getNamePolicy())
                        .Description(hotelPolicyDTO.getDescription())
                        .changefee(hotelPolicyDTO.getChangefee())
                        .hotelList(hotelList)
                        .build();
                hotelPolicyRepository.save(hotelPolicy);
            }
        }else {
            List<Hotel> hotelList=new ArrayList<>();
            hotelList.add(hotel);
            HotelPolicy hotelPolicy=new HotelPolicy().builder()
                    .namePolicy(hotelPolicyDTO.getNamePolicy())
                    .Description(hotelPolicyDTO.getDescription())
                    .changefee(hotelPolicyDTO.getChangefee())
                    .hotelList(hotelList)
                    .build();
            hotelPolicyRepository.save(hotelPolicy);
        }

    }

    @Override
    public void updateHotelPolicy(HotelPolicyDTO hotelPolicyDTO,Long HotelPolicyId) {
      HotelPolicy hotelPolicy=hotelPolicyRepository.findById(HotelPolicyId).orElseThrow(()->new RuntimeException("not found"));
      hotelPolicy.setNamePolicy(hotelPolicyDTO.getNamePolicy());
      hotelPolicy.setDescription(hotelPolicyDTO.getDescription());
      hotelPolicy.setChangefee(hotelPolicyDTO.getChangefee());
      hotelPolicyRepository.save(hotelPolicy);
    }

    @Override
    public String deletePolicyById(Long hotelId,Long PolicyId) {
        Hotel hotel =hotelRepository.findById(hotelId).orElseThrow(()->new RuntimeException("not found"));
        HotelPolicy hotelPolicy=hotelPolicyRepository.findById(PolicyId).orElseThrow(()->new RuntimeException("not found"));
        List<Hotel> hotelList=hotelPolicy.getHotelList();
        if (!hotelList.contains(hotel)) {
            throw new ResourceNotFoundException("Hotel Đã không còn Chính sách");
        }else {
            hotelList.remove(hotel);
        }
        hotelPolicyRepository.save(hotelPolicy);
        return "success";
    }
    private HotelPolicyDTO Convert(HotelPolicy hotelPolicy){
        HotelPolicyDTO hotelPolicyDTO=new HotelPolicyDTO().builder()
                .namePolicy(hotelPolicy.getNamePolicy())
                .changefee(hotelPolicy.getChangefee())
                .Description(hotelPolicy.getDescription())
                .build();
        return hotelPolicyDTO;
    }

    @Override
    public List<HotelPolicyDTO> getHotelPolicyByHotel(Long HotelId) {
        Hotel hotel=hotelRepository.findById(HotelId).orElseThrow(()->new ResourceNotFoundException("not found"));
        List<HotelPolicy> hotelPolicyList=hotel.getHotelPolicyList();
        List<HotelPolicyDTO> hotelPolicyDTOS=new ArrayList<>();
        for (HotelPolicy hotelPolicy:hotelPolicyList){
            hotelPolicyDTOS.add(this.Convert(hotelPolicy));
        }

        return hotelPolicyDTOS;
    }

    @Override
    public List<HotelPolicyDTO> getAllHotelPolicy() {
        List<HotelPolicy> hotelPolicyList=hotelPolicyRepository.findAll();
        List<HotelPolicyDTO> hotelPolicyDTOS=new ArrayList<>();
        for (HotelPolicy hotelPolicy:hotelPolicyList){
            hotelPolicyDTOS.add(this.Convert(hotelPolicy));
        }
        return hotelPolicyDTOS;
    }
}
