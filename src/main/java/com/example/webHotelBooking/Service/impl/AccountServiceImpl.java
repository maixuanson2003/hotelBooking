package com.example.webHotelBooking.Service.impl;

import com.example.webHotelBooking.DTO.Response.AccountHotelDTO;
import com.example.webHotelBooking.Entity.AccountHotel;
import com.example.webHotelBooking.Entity.Hotel;
import com.example.webHotelBooking.Enums.AccountStatus;
import com.example.webHotelBooking.Enums.UpdateAccount;
import com.example.webHotelBooking.Exception.ResourceNotFoundException;
import com.example.webHotelBooking.Repository.AccountHotelRepository;
import com.example.webHotelBooking.Repository.HotelRepository;
import com.example.webHotelBooking.Service.AccountHotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountHotelService {
    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private AccountHotelRepository accountHotelRepository;
    @Override
    @Transactional
    public void CreatAccountByHotel(Long HotelId) {
        BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
        Hotel hotel=hotelRepository.findById(HotelId).orElseThrow(()->new ResourceNotFoundException("not found"));
        String userName=hotel.getNameHotel()+"ADMIN";
        String password=hotel.getNameHotel()+"ADMIN";
        AccountHotel accountHotel=new AccountHotel().builder()
                .phone(hotel.getHotline())
                .hotel(hotel)
                .username(userName)
                .password(passwordEncoder.encode(password))
                .build();
        accountHotelRepository.save(accountHotel);
    }

    @Override
    @Transactional
    public void DeleteAccountById(Long Id) {
        accountHotelRepository.deleteById(Id);
    }

    @Override
    public List<AccountHotelDTO> GetAllAccountHotel() {
        List<AccountHotel> accountHotelList=accountHotelRepository.findAll();
        List<AccountHotelDTO> accountHotelDTOS=new ArrayList<>();
        for (AccountHotel accountHotel:accountHotelList){
            AccountHotelDTO accountHotelDTO=new AccountHotelDTO().builder()
                    .HotelName(accountHotel.getHotel().getNameHotel())
                    .id(accountHotel.getId())
                    .username(accountHotel.getUsername())
                    .build();
            accountHotelDTOS.add(accountHotelDTO);
        }
        return accountHotelDTOS;
    }

    @Override
    public String ChangePassWord(String password, String oldPassword,Long accountId) {
        BCryptPasswordEncoder passwordCheck = new BCryptPasswordEncoder();
        AccountHotel accountHotel=accountHotelRepository.findById(accountId).orElseThrow(()->new ResourceNotFoundException("not found"));

        if (passwordCheck.matches(oldPassword,accountHotel.getPassword())){
            accountHotel.setPassword( passwordCheck.encode(password));
            accountHotelRepository.save(accountHotel);
            return UpdateAccount.SUCCESS.getMessage();
        }else {
            return UpdateAccount.FAILED.getMessage();
        }
    }
}
