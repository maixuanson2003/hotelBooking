package com.example.webHotelBooking.Service.impl;

import com.example.webHotelBooking.DTO.Request.PaymentRequest;
import com.example.webHotelBooking.Entity.Payment;
import com.example.webHotelBooking.Entity.booking;
import com.example.webHotelBooking.Repository.PaymentRepository;
import com.example.webHotelBooking.Repository.bookingRepository;
import com.example.webHotelBooking.Service.PaymentService;
import com.example.webHotelBooking.ultils.VnPay;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;

@Service
public class PaymentServiceimpl implements PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private bookingRepository bookingRepository;
    @Override
    public String CreatePayment(PaymentRequest paymentRequest, HttpServletRequest req) throws UnsupportedEncodingException {
        booking booking=bookingRepository.findById(paymentRequest.getBookingId()).orElseThrow(()->new RuntimeException("not found"));
        Payment payment=new Payment().builder()
                .createAt(LocalDate.now().toString())
                .booking(booking)
                .TotalPrice(paymentRequest.getPrice())
                .status("Pay")
                .build();
        paymentRepository.save(payment);
        return VnPay.CreatePay(paymentRequest, req);
    }
}
