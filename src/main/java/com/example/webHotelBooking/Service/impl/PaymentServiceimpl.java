package com.example.webHotelBooking.Service.impl;

import com.example.webHotelBooking.DTO.Request.PaymentRequest;
import com.example.webHotelBooking.DTO.Response.AlertPayment;
import com.example.webHotelBooking.DTO.Response.PaymentResponse;
import com.example.webHotelBooking.Entity.Payment;
import com.example.webHotelBooking.Entity.actor;
import com.example.webHotelBooking.Entity.booking;
import com.example.webHotelBooking.Entity.bookingdetails;
import com.example.webHotelBooking.Enums.PaymentStatus;
import com.example.webHotelBooking.Enums.bookingStatus;
import com.example.webHotelBooking.Exception.ResourceNotFoundException;
import com.example.webHotelBooking.Repository.PaymentRepository;
import com.example.webHotelBooking.Repository.bookingRepository;
import com.example.webHotelBooking.Repository.userRepository;
import com.example.webHotelBooking.Service.PaymentService;
import com.example.webHotelBooking.config.VnPayConfig;
import com.example.webHotelBooking.ultils.VnPay;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@Service
public class PaymentServiceimpl implements PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private bookingRepository bookingRepository;
    @Autowired
    private EmailServiceimpl emailServiceimpl;
    @Autowired
    private userRepository userRepository;
    private Queue<PaymentResponse> Email=new LinkedList<>();
    @Override
    public String CreatePayment(PaymentRequest paymentRequest, HttpServletRequest req) throws IOException {
        booking booking=bookingRepository.findById(paymentRequest.getBookingId()).orElseThrow(()->new RuntimeException("not found"));
        if (booking.getStatus().equals(bookingStatus.HUY.getMessage())){
            return " Don dat phong da bi huy";
        }
        Payment payment=new Payment().builder()
                .booking(booking)
                .actor(booking.getActor())
                .TotalPrice(booking.getTotalPrice())
                .build();
        Payment payment1= paymentRepository.save(payment);
        return VnPay.CreatePay(paymentRequest, req);
    }


    @Override
    public AlertPayment GetInforPayment(Long BookingId, HttpServletRequest req) throws IOException, ParseException {
        booking booking=bookingRepository.findById(BookingId).orElseThrow(()->new ResourceNotFoundException("not found"));
        Payment payment=booking.getPayment();
        if (VnPay.verifyPayment(payment,req)){
            payment.setStatus(PaymentStatus.SUCCESS.getMessage());
            paymentRepository.save(payment);
            booking.setStatus(bookingStatus.ĐA_THANH_TOAN.getMessage());
            bookingRepository.save(booking);
            AlertPayment alertPayment=new AlertPayment().builder()
                    .message("Ban da thanh toan thanh cong")
                    .status(PaymentStatus.SUCCESS.getMessage())
                    .build();
            PaymentResponse paymentResponse=new PaymentResponse().builder()
                    .Email(payment.getBooking().getActor().getEmail())
                    .phoneNumber(payment.getBooking().getActor().getPhone())
                    .bookingId(payment.getBooking().getId())
                    .nameuser(payment.getBooking().getActor().getFullname())
                    .Status(payment.getStatus())
                    .TotalPrice(payment.getTotalPrice())
                    .timeCreate(payment.getCreate_at())
                    .build();
            Email.add(paymentResponse);
            return alertPayment;
        }else {
            AlertPayment alertPayment=new AlertPayment().builder()
                    .message("Thanh toan that bai")
                    .status("Faild")
                    .build();
            return alertPayment;
        }
    }

    @Override
    public List<PaymentResponse> GetAllPayMentResponse() {
        List<Payment> paymentList=paymentRepository.findAll();
        List<PaymentResponse> paymentResponseList=new ArrayList<>();
        for (Payment payment:paymentList){
            PaymentResponse paymentResponse=new PaymentResponse().builder()
                    .Email(payment.getBooking().getActor().getEmail())
                    .phoneNumber(payment.getBooking().getActor().getPhone())
                    .bookingId(payment.getBooking().getId())
                    .nameuser(payment.getBooking().getActor().getFullname())
                    .Status(payment.getStatus())
                    .TotalPrice(payment.getTotalPrice())
                    .timeCreate(payment.getCreate_at())
                    .build();
            paymentResponseList.add(paymentResponse);
        }
        return paymentResponseList;
    }

    @Override
    public List<PaymentResponse> GetAllPayMentByUser(String nameUser) {
        actor actor=userRepository.findByUsername(nameUser);
        List<Payment> paymentList=actor.getPayments();
        List<PaymentResponse> paymentResponseList=new ArrayList<>();
        for (Payment payment:paymentList){
            PaymentResponse paymentResponse=new PaymentResponse().builder()
                    .Email(payment.getBooking().getActor().getEmail())
                    .phoneNumber(payment.getBooking().getActor().getPhone())
                    .bookingId(payment.getBooking().getId())
                    .nameuser(payment.getBooking().getActor().getFullname())
                    .Status(payment.getStatus())
                    .TotalPrice(payment.getTotalPrice())
                    .timeCreate(payment.getCreate_at())
                    .build();
            paymentResponseList.add(paymentResponse);
        }
        return paymentResponseList;
    }
    @Scheduled(fixedRate = 1000)
    @Transactional
    public void SendEmail(){
        while (!Email.isEmpty()) {

            PaymentResponse email = Email.poll();
            booking booking=bookingRepository.findById(email.getBookingId()).orElseThrow(()->new ResourceNotFoundException("not found"));// Lấy email từ hàng đợi
            if (email != null) {
                String s=" ";
                String nameuser="";
                List<bookingdetails> bookingdetailsList=booking.getBookingdetailsList();
                for (bookingdetails bookingdetails:bookingdetailsList){
                    nameuser=bookingdetails.getHotelRoom().getHotel().getNameHotel();
                    s+="Loai phong: "+bookingdetails.getHotelRoom().getTypeRoom()+"\n"+
                      "So luong phong: "+bookingdetails.getAmountRoom()+"\n";
                }
                String Booking="Khach hang: "+ email.getNameuser()+"\n"+
                               "Khach san : "+ nameuser+"\n"+
                               "tong phong: "+ booking.getTotalRoom()+"\n"+
                               "so nguoi: "+booking.getNumberPeople()+"\n"+
                               "Chi tiet Don hang"+"\n"+
                                s;

                emailServiceimpl.sendEmail(email.getEmail(), "Don dat hang", Booking);
            }
        }
    }

}
