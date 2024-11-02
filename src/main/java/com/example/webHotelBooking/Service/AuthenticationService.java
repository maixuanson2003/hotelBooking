package com.example.webHotelBooking.Service;

import com.example.webHotelBooking.DTO.Request.AuthenticationRequest;
import com.example.webHotelBooking.DTO.Request.verifytokenRequest;
import com.example.webHotelBooking.DTO.Response.AuthenticationResponse;
import com.example.webHotelBooking.DTO.Response.verifyTokenResponse;
import com.nimbusds.jose.JOSEException;

import java.text.ParseException;

public interface AuthenticationService {
    public verifyTokenResponse veryfitoken(verifytokenRequest request) throws JOSEException, ParseException;
    public void Logout(String token) throws ParseException, JOSEException;

    public AuthenticationResponse login(AuthenticationRequest requests);
    public AuthenticationResponse RefresToken(String token)throws JOSEException, ParseException;

}
