package com.example.webHotelBooking.Service;

import com.example.webHotelBooking.DTO.Request.PaymentRequest;
import jakarta.servlet.http.HttpServletRequest;

import java.io.UnsupportedEncodingException;

public interface PaymentService {
    public String CreatePayment(PaymentRequest paymentRequest , HttpServletRequest req)throws UnsupportedEncodingException;
}
