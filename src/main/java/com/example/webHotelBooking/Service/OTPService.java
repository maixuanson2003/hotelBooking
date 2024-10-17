package com.example.webHotelBooking.Service;

import com.example.webHotelBooking.DTO.Response.MyApiResponse;

public interface OTPService {
    public MyApiResponse RegisterVerifyOTP(String OTP);
    public void SendOTP(String email);
}
