package com.example.webHotelBooking.Controller;

import com.example.webHotelBooking.DTO.Response.MyApiResponse;
import com.example.webHotelBooking.Service.impl.OTPServiceimpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OTPController {
    @Autowired
    private OTPServiceimpl otpServiceimpl;

    @PostMapping("/verify-otp")
    public ResponseEntity<MyApiResponse> verifyOTP(@RequestParam(required = true) String OTP) {
        MyApiResponse response = otpServiceimpl.RegisterVerifyOTP(OTP);

        if (response != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(new MyApiResponse().builder()
                    .Check(false)
                    .message("Xác thực thất bại.")
                    .build());
        }
    }
    @PostMapping("/send")
    public void sendOTP(@RequestParam String email) {
        otpServiceimpl.SendOTP(email);

    }
}
