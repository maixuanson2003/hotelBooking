package com.example.webHotelBooking.Controller;

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
    public ResponseEntity<String> verifyOTP(@RequestParam String otp) {
        String result = otpServiceimpl.RegisterVerifyOTP(otp);
        return ResponseEntity.ok(result);
    }
}
