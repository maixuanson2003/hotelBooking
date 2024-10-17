package com.example.webHotelBooking.Service.impl;

import com.example.webHotelBooking.DTO.Response.MyApiResponse;
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
    @Autowired
    private EmailServiceimpl emailServiceimpl;
    @Override
    public MyApiResponse RegisterVerifyOTP(String OTP) {
        OTP otp = findOTPByOTP(OTP);
        if (otp == null) {
            return new MyApiResponse().builder()
                    .Check(false)
                    .message("OTP không hợp lệ")
                    .build();
        }else {
            otpRepository.delete(otp);
            return new MyApiResponse().builder()
                    .Check(true)
                    .message("OTP  hợp lệ")
                    .build();
        }
    }

    @Override
    public void SendOTP(String email) {
        String OTP=GenerateOTP(email);
        String userEmail = email;// card.getUser() cần được định nghĩa trong entity LibraryCard
        emailServiceimpl.sendAsyncEmail(userEmail, "Xác thực Mã OTP",OTP);
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
    private String  GenerateOTP(String useremail){
        String OTP="";
        Random rand = new Random();
        for (int i=0;i<5;i++){
            int randomInt = rand.nextInt(9);
            String code=String.valueOf(randomInt);
            OTP=OTP+code;
        }
        OTP otp=new OTP();
        otp.setOTP(OTP);
        otp.setUseremail(useremail);
        otpRepository.save(otp);
        return OTP;
    }

}
