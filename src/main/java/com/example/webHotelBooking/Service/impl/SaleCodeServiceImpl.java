package com.example.webHotelBooking.Service.impl;

import com.example.webHotelBooking.DTO.Response.SaleCodeDTO;
import com.example.webHotelBooking.Entity.booking;
import com.example.webHotelBooking.Entity.bookingdetails;
import com.example.webHotelBooking.Entity.saleCode;
import com.example.webHotelBooking.Enums.SaleCodeStatus;
import com.example.webHotelBooking.Exception.ResourceNotFoundException;
import com.example.webHotelBooking.Repository.bookingRepository;
import com.example.webHotelBooking.Repository.saleCodeRepository;
import com.example.webHotelBooking.Service.SaleCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
@Service
public class  SaleCodeServiceImpl implements SaleCodeService {
    @Autowired
    private saleCodeRepository saleCodeRepository;
    @Override
    public void CreateSaleCode(SaleCodeDTO saleCodeDTO) {
        saleCode saleCode=new saleCode().builder()
                .Code(saleCodeDTO.getCode())
                .DateStart(saleCodeDTO.getDateStart())
                .DateEnd(saleCodeDTO.getDateEnd())
                .Description(saleCodeDTO.getDescription())
                .image(saleCodeDTO.getImage())
                .Title(saleCodeDTO.getTitle())
                .status(SaleCodeStatus.CONHAN.getMessage())
                .discountPercentage(saleCodeDTO.getDiscountPercentage())
                .build();
        saleCodeRepository.save(saleCode);
    }

    @Override
    public void UpdateSaleCode(SaleCodeDTO saleCodeDTO,Long saleCodeId) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        saleCode saleCode=saleCodeRepository.findById(saleCodeId).orElseThrow(()->new ResourceNotFoundException("not found"));
        LocalDate DateStart= LocalDate.parse(saleCode.getDateStart(),formatter);
        LocalDate DateEnd= LocalDate.parse(saleCode.getDateStart(),formatter);
        LocalDate DateStartCheck= LocalDate.parse(saleCodeDTO.getDateStart(),formatter);
        LocalDate DateEndCheck= LocalDate.parse(saleCodeDTO.getDateStart(),formatter);
        saleCode.setCode(saleCodeDTO.getCode());
        saleCode.setDateEnd(saleCodeDTO.getDateEnd());
        saleCode.setDateStart(saleCodeDTO.getDateStart());
        saleCode.setTitle(saleCode.getTitle());
        saleCode.setDescription(saleCodeDTO.getDescription());
        saleCode.setDiscountPercentage(saleCodeDTO.getDiscountPercentage());
        if ( DateEndCheck.isAfter(DateEnd)){
            if (saleCode.getStatus().equals(SaleCodeStatus.HETHAN.getMessage())){
                saleCode.setStatus(SaleCodeStatus.CONHAN.getMessage());
            }
        }
        saleCodeRepository.save(saleCode);

    }
    @Override
    public void DeleteSaleCode(Long SaleCodeId) {
        saleCode saleCode=saleCodeRepository.findById( SaleCodeId).orElseThrow(()->new ResourceNotFoundException("not found"));
        List<bookingdetails> bookingList=saleCode.getBookingdetailsList();
        if (bookingList.isEmpty()){
            saleCodeRepository.deleteById(SaleCodeId);
        }else {
            bookingList.clear();
            saleCodeRepository.deleteById(SaleCodeId);
        }
    }

    @Override
    public void AutoDeleteSaleCode() {
        List<saleCode> saleCodeList=saleCodeRepository.findAll();
        for (saleCode saleCode:saleCodeList){
            if (saleCode.getStatus().equals(SaleCodeStatus.HETHAN.getMessage())){
                List<bookingdetails> bookingList=saleCode.getBookingdetailsList();
                if (bookingList.isEmpty()){
                    saleCodeRepository.deleteById(saleCode.getId());
                }else {
                    bookingList.clear();
                    saleCodeRepository.deleteById(saleCode.getId());
                }
            }
        }
    }
    @Override
    public List<SaleCodeDTO> GetAllSaleCode() {
        List<saleCode> saleCodeList=saleCodeRepository.findAll();
        List<SaleCodeDTO> SaleCodeDTOs=new ArrayList<>();
        for (saleCode saleCode:saleCodeList){
            if(saleCode.getStatus().equals(SaleCodeStatus.CONHAN.getMessage())){
                SaleCodeDTO saleCodeDTO=new SaleCodeDTO().builder()
                        .id(saleCode.getId())
                        .Code(saleCode.getCode())
                        .DateEnd(saleCode.getDateEnd())
                        .DateStart(saleCode.getDateStart())
                        .Description(saleCode.getDescription())
                        .title(saleCode.getTitle())
                        .image(saleCode.getImage())
                        .discountPercentage(saleCode.getDiscountPercentage())
                        .build();
                SaleCodeDTOs.add(saleCodeDTO);
            }
        }
        return SaleCodeDTOs;
    }

    @Override
    public SaleCodeDTO GetSaleCodeById(Long id) {
        saleCode saleCodeDetail=saleCodeRepository.findById(id).orElseThrow(()->new RuntimeException("not found"));
        SaleCodeDTO saleCodeDTO=new SaleCodeDTO().builder()
                .id( saleCodeDetail.getId())
                .Code(saleCodeDetail.getCode())
                .DateEnd(saleCodeDetail.getDateEnd())
                .DateStart(saleCodeDetail.getDateStart())
                .Description(saleCodeDetail.getDescription())
                .title(saleCodeDetail.getTitle())
                .image(saleCodeDetail.getImage())
                .discountPercentage(saleCodeDetail.getDiscountPercentage())
                .build();
        return saleCodeDTO;
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void AutoCancelSaleCode(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        List<saleCode> saleCodeList=saleCodeRepository.findAll();
        for (saleCode saleCode:saleCodeList){
            LocalDate DateEnd= LocalDate.parse(saleCode.getDateStart(),formatter);
            if (LocalDate.now().isAfter(DateEnd)){
                saleCode.setStatus(SaleCodeStatus.HETHAN.getMessage());
                saleCodeRepository.save(saleCode);
            }
        }
    }
}
