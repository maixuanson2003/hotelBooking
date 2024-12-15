package com.example.webHotelBooking.Service.impl;

import com.example.webHotelBooking.DTO.Response.CheckPolicy;
import com.example.webHotelBooking.DTO.Response.HotelPolicyDetailsDTO;
import com.example.webHotelBooking.Entity.Hotel;
import com.example.webHotelBooking.Entity.HotelPolicy;
import com.example.webHotelBooking.Entity.HotelPolicyDetails;
import com.example.webHotelBooking.Entity.booking;

import com.example.webHotelBooking.Enums.NamePolicy;
import com.example.webHotelBooking.Exception.ResourceNotFoundException;
import com.example.webHotelBooking.Repository.HotelPolicyDetailsRepository;
import com.example.webHotelBooking.Repository.HotelPolicyRepository;
import com.example.webHotelBooking.Repository.HotelRepository;
import com.example.webHotelBooking.Repository.bookingRepository;
import com.example.webHotelBooking.Service.HotelPolicyDetailService;
import com.example.webHotelBooking.Service.HotelPolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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
    @Autowired
    private bookingRepository bookingRepository;

    @Override
    @Transactional
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
        }else {
            coditionalInfo= hotelPolicyDetailsDTO.getCoditionalInfo();
        }
        HotelPolicyDetails hotelPolicyDetails=new HotelPolicyDetails().builder()
                .hotel(hotel)
                .fee(hotelFee)
                .coditionalInfo(coditionalInfo)
                .description(hotelPolicyDetailsDTO.getDescription())
                .beforeDayAmount(hotelPolicyDetailsDTO.getBeforeDayAmount())
                .note(hotelPolicyDetailsDTO.getNote())
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
    public CheckPolicy CheckPolicyChangeQualifiled(Long bookingid) {
        booking booking=bookingRepository.findById(bookingid).orElseThrow(()->new RuntimeException("notfound"));
        Hotel hotel=booking.getBookingdetailsList().get(0).getHotelRoom().getHotel();
        List<HotelPolicyDetails> hotelPolicyDetails=hotel.getHotelPolicyDetailsList();
        HotelPolicyDetails hotelPolicyDetailsCheck=null;
        for (HotelPolicyDetails hotelPolicyDetails1:hotelPolicyDetails){
            if(hotelPolicyDetails1.getHotelPolicy().getNamePolicy().equals(NamePolicy.CHINHSACHDOILICH.getMessage())){
                hotelPolicyDetailsCheck=hotelPolicyDetails1;
            }
        }
        if(booking.getBookingdetailsList()==null || booking.getBookingdetailsList().isEmpty()){
            throw new RuntimeException("not have booking detail");
        }
        LocalDate today=LocalDate.now();
        LocalDate checkInDate = booking.getBookingdetailsList().get(0).getCheckInDate();
        boolean isBeforeAmontDay = today.isBefore(checkInDate.minusDays(hotelPolicyDetailsCheck.getBeforeDayAmount()));
         if(!isBeforeAmontDay){
             return new CheckPolicy().builder()
                     .Check(false)
                     .message("Bạn sẽ phải thanh toán phí đổi lịch sau khi khách sạn xác nhận đơn đặt hàng của bạn")
                     .build();
         }
        return new CheckPolicy().builder()
                .Check(true)
                .message("Bạn được miễn phí đổi lịch nếu khách sạn xác nhận đơn đặt phòng của bạn ,hãy đợi thông báo của khách sạn nhé")
                .build();

    }

    @Override
    public CheckPolicy CheckPolicyCanceledQualifiled(Long bookingid) {
        booking booking=bookingRepository.findById(bookingid).orElseThrow(()->new RuntimeException("notfound"));
        Hotel hotel=booking.getBookingdetailsList().get(0).getHotelRoom().getHotel();
        List<HotelPolicyDetails> hotelPolicyDetails=hotel.getHotelPolicyDetailsList();
        HotelPolicyDetails hotelPolicyDetailsCheck=null;
        for (HotelPolicyDetails hotelPolicyDetails1:hotelPolicyDetails){
            if(hotelPolicyDetails1.getHotelPolicy().getNamePolicy().equals(NamePolicy.CHINHSACHHUYLICH.getMessage())){
                hotelPolicyDetailsCheck=hotelPolicyDetails1;
            }
        }
        if(booking.getBookingdetailsList()==null || booking.getBookingdetailsList().isEmpty()){
            throw new RuntimeException("not have booking detail");
        }
        LocalDate today=LocalDate.now();
        LocalDate checkInDate = booking.getBookingdetailsList().get(0).getCheckInDate();
        boolean isBeforeAmontDay = today.isBefore(checkInDate.minusDays(hotelPolicyDetailsCheck.getBeforeDayAmount()));
        if(!isBeforeAmontDay){
            return new CheckPolicy().builder()
                    .Check(false)
                    .message("Bạn sẽ không được hoàn tiền nếu Hủy lịch")
                    .build();
        }
        return new CheckPolicy().builder()
                .Check(true)
                .message("Hủy lịch Hệ thống sẽ tự động hoàn tiền cho bạn")
                .build();

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
        hotelPolicyDetails.setFee(hotelPolicyDetailsDTO.getFee());
        hotelPolicyDetails.setCoditionalInfo(hotelPolicyDetailsDTO.getCoditionalInfo());
        hotelPolicyDetails.setBeforeDayAmount(hotelPolicyDetailsDTO.getBeforeDayAmount());
        hotelPolicyDetails.setNote(hotelPolicyDetailsDTO.getNote());
        hotelPolicyDetailsRepository.save(hotelPolicyDetails);

    }
}
