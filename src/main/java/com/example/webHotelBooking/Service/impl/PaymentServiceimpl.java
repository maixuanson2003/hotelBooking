package com.example.webHotelBooking.Service.impl;

import com.example.webHotelBooking.DTO.Request.PaymentRequest;
import com.example.webHotelBooking.Entity.Payment;
import com.example.webHotelBooking.Entity.booking;
import com.example.webHotelBooking.Exception.ResourceNotFoundException;
import com.example.webHotelBooking.Repository.PaymentRepository;
import com.example.webHotelBooking.Repository.bookingRepository;
import com.example.webHotelBooking.Service.PaymentService;
import com.example.webHotelBooking.config.VnPayConfig;
import com.example.webHotelBooking.ultils.VnPay;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.time.LocalDate;

@Service
public class PaymentServiceimpl implements PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private bookingRepository bookingRepository;
    @Override
    public String CreatePayment(PaymentRequest paymentRequest, HttpServletRequest req) throws IOException {
        System.out.println(paymentRequest.getBookingId());
        System.out.println(paymentRequest.getActorname());
        System.out.println(paymentRequest.getPhoneNumber());
        booking booking=bookingRepository.findById(paymentRequest.getBookingId()).orElseThrow(()->new RuntimeException("not found"));
        System.out.println(booking);
        Payment payment=new Payment().builder()
                .booking(booking)
                .TotalPrice(paymentRequest.getPrice())
                .status("Pay")
                .build();
        Payment payment1= paymentRepository.save(payment);

        return VnPay.CreatePay(paymentRequest, req);
    }


    @Override
    public String GetInforPayment(Long BookingId, HttpServletRequest req) throws IOException, ParseException {
        String returnUrl="http://localhost:3000";
        booking booking=bookingRepository.findById(BookingId).orElseThrow(()->new ResourceNotFoundException("not found"));
        Payment payment=booking.getPayment();
        if (VnPay.verifyPayment(payment,req)){
            String totalPrice = String.valueOf(payment.getBooking().getTotalPrice());
            String totalRoom = String.valueOf(payment.getBooking().getTotalRoom());
            String status="success";

            // Sử dụng URLEncoder để mã hóa các giá trị
            String paymentInfor = "totalPrice=" + URLEncoder.encode(totalPrice, "UTF-8")
                    + "&totalRoom=" + URLEncoder.encode(totalRoom, "UTF-8");
            return returnUrl + VnPayConfig.vnp_Returnurl + "?" + paymentInfor;
        }else {
            return "invalid";
        }
    }
}
