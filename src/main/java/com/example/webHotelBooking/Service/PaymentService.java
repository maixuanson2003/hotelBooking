package com.example.webHotelBooking.Service;

import com.example.webHotelBooking.DTO.Request.PaymentRequest;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;

public interface PaymentService {
    public String CreatePayment(PaymentRequest paymentRequest , HttpServletRequest req) throws IOException;
    public String GetInforPayment(Long BookingId,HttpServletRequest req) throws IOException, ParseException;
}
