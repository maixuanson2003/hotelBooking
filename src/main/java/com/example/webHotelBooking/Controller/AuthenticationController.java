package com.example.webHotelBooking.Controller;

import com.example.webHotelBooking.DTO.Request.AuthenticationRequest;
import com.example.webHotelBooking.DTO.Request.verifytokenRequest;
import com.example.webHotelBooking.DTO.Response.AuthenticationResponse;
import com.example.webHotelBooking.DTO.Response.verifyTokenResponse;
import com.example.webHotelBooking.Service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authService; // Dịch vụ xử lý xác thực

    // Đăng nhập
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
        AuthenticationResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    // Xác thực token
    @PostMapping("/verify-token")
    public ResponseEntity<verifyTokenResponse> verifyToken(@RequestBody verifytokenRequest request) {
        try {
            verifyTokenResponse response = authService.veryfitoken(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new verifyTokenResponse(false));
        }
    }

    // Đăng xuất
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody String token) {
        try {
            authService.Logout(token);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    @PostMapping("/refreshToken")
    public ResponseEntity<AuthenticationResponse> RefreshToken(@RequestParam String token) throws ParseException, JOSEException {
        AuthenticationResponse response = authService.RefresToken(token);
        return ResponseEntity.ok(response);
    }
}
