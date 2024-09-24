package com.example.webHotelBooking.Service.impl;

import com.example.webHotelBooking.Entity.OTP;
import com.example.webHotelBooking.Entity.actor;
import com.example.webHotelBooking.Enums.AccountStatus;
import com.example.webHotelBooking.Repository.OTPRepository;
import com.example.webHotelBooking.Repository.userRepository;
import com.example.webHotelBooking.Service.OTPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
@Service
public class OTPServiceimpl implements OTPService {
    @Autowired
    private OTPRepository otpRepository;
    @Autowired
    private userRepository userRepository;
    @Override
    public String RegisterVerifyOTP(String OTP) {
        List<actor> actorList=userRepository.findAll();
        for (actor actor:actorList){
            OTP otp=findOTPByOTP(OTP);
            if (actor.getEmail().equals(otp.getUseremail())){
                actor.setStatus(AccountStatus.ĐAĐANGKY.getMessage());
                userRepository.save(actor);
                otpRepository.delete(otp);
                break;
            }
        }
        return "OK";
    }
    private OTP findOTPByOTP(String OTP){
        List<OTP> otpList=otpRepository.findAll();
        for(OTP otp:otpList){
            if (otp.getOTP().equals(OTP)){
                return otp;
            }
        }
        return null;
    }

}
