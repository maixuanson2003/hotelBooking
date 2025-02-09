package com.example.webHotelBooking.Service;

import com.example.webHotelBooking.DTO.Request.PaymentRequest;
import com.example.webHotelBooking.DTO.Response.AlertPayment;
import com.example.webHotelBooking.DTO.Response.PaymentResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.List;

public interface PaymentService {
    public String CreatePayment(PaymentRequest paymentRequest , HttpServletRequest req) throws IOException, ParseException;
    public String CreatePaymentChange(PaymentRequest paymentRequest , HttpServletRequest req) throws IOException, ParseException;
    public AlertPayment GetInforPayment(Long BookingId, HttpServletRequest req) throws IOException, ParseException;
    public AlertPayment GetInforPaymentChange(Long BookingChangeId, HttpServletRequest req) throws IOException, ParseException;
    public AlertPayment RefundPayment(Long BookingId, HttpServletRequest req) throws IOException, ParseException, ServletException;
    public List<PaymentResponse> GetAllPayMentResponse();
    public List<PaymentResponse> GetAllPayMentByUser(String nameUser);
}
