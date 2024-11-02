package com.example.webHotelBooking.Service;

import com.example.webHotelBooking.DTO.Response.AccountHotelDTO;
import com.example.webHotelBooking.Entity.AccountHotel;

import java.util.List;

public interface AccountHotelService {
    public void CreatAccountByHotel(Long HotelId);
    public void DeleteAccountById(Long Id);
    public void CreateAllAccountHotel();
    public List<AccountHotelDTO> GetAllAccountHotel();
    public String ChangePassWord(String password,String oldPassword,Long accountId);
}
