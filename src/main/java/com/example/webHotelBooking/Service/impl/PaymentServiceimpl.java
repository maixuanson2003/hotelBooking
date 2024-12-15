package com.example.webHotelBooking.Service.impl;

import com.example.webHotelBooking.DTO.Request.PaymentRequest;
import com.example.webHotelBooking.DTO.Response.AlertPayment;
import com.example.webHotelBooking.DTO.Response.PaymentDTO;
import com.example.webHotelBooking.DTO.Response.PaymentResponse;
import com.example.webHotelBooking.Entity.Payment;
import com.example.webHotelBooking.Entity.actor;
import com.example.webHotelBooking.Entity.booking;
import com.example.webHotelBooking.Entity.bookingChangeDetails;
import com.example.webHotelBooking.Entity.bookingdetails;
import com.example.webHotelBooking.Enums.PaymentStatus;
import com.example.webHotelBooking.Enums.bookingStatus;
import com.example.webHotelBooking.Exception.ResourceNotFoundException;
import com.example.webHotelBooking.Repository.PaymentRepository;
import com.example.webHotelBooking.Repository.bookingRepository;
import com.example.webHotelBooking.Repository.bookingChangeDetailsRepository;
import com.example.webHotelBooking.Repository.userRepository;
import com.example.webHotelBooking.Service.PaymentService;
import com.example.webHotelBooking.Service.BookingChangeDetailsService;
import com.example.webHotelBooking.config.VnPayConfig;
import com.example.webHotelBooking.ultils.VnPay;
import jakarta.servlet.ServletException;
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
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class PaymentServiceimpl implements PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private bookingRepository bookingRepository;
    @Autowired
    private bookingChangeDetailsRepository bookingChangeDetailsRepository;
    @Autowired
    private BookingChangeDetailsService bookingChangeDetailsService;
    @Autowired
    private EmailServiceimpl emailServiceimpl;
    @Autowired
    private userRepository userRepository;
    private Queue<PaymentResponse> Email=new LinkedList<>();
    private Queue<PaymentResponse> EmailChange=new LinkedList<>();
    @Override
    public String CreatePayment(PaymentRequest paymentRequest, HttpServletRequest req) throws IOException, ParseException {
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        booking booking=bookingRepository.findById(paymentRequest.getBookingId()).orElseThrow(()->new RuntimeException("not found"));
        if (booking.getStatus().equals(bookingStatus.HUY.getMessage())){
            return " Don dat phong da bi huy";
        }
        PaymentDTO paymentDTO=VnPay.CreatePay(paymentRequest,"BOOKING", req);

        Payment payment=new Payment().builder()
                .booking(booking)
                .bookingChangeDetails(null)
                .actor(booking.getActor())
                .create_at(paymentDTO.getCreateAt())
                .TotalPrice(booking.getTotalPrice())
                .build();
        Payment payment1= paymentRepository.save(payment);

        return paymentDTO.getUrl();
    }

    @Override
    public String CreatePaymentChange(PaymentRequest paymentRequest, HttpServletRequest req) throws IOException, ParseException {
        bookingChangeDetails bookingChangeDetails1=bookingChangeDetailsRepository.findById(paymentRequest.getBookingId()).orElseThrow(()->new RuntimeException("not found"));
        PaymentDTO paymentDTO=VnPay.CreatePay(paymentRequest,"CHANGE",req);
        Payment payment=new Payment().builder()
                .booking(null)
                .bookingChangeDetails(bookingChangeDetails1)
                .actor(bookingChangeDetails1.getBooking().getActor())
                .create_at(paymentDTO.getCreateAt())
                .TotalPrice(bookingChangeDetails1.getPrice())
                .build();
        Payment payment1= paymentRepository.save(payment);
        return paymentDTO.getUrl();
    }


    @Override
    public AlertPayment GetInforPayment(Long BookingId, HttpServletRequest req) throws IOException, ParseException {
        booking booking=bookingRepository.findById(BookingId).orElseThrow(()->new ResourceNotFoundException("not found"));
        Payment payment=booking.getPayment();
        PaymentDTO paymentDTO=VnPay.verifyPayment(payment,"BOOKING",req);
        if (paymentDTO.isCheck()){
            payment.setStatus(PaymentStatus.SUCCESS.getMessage());
            payment.setTransactionNo(paymentDTO.getTransCode());
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
    public AlertPayment GetInforPaymentChange(Long BookingChangeId, HttpServletRequest req) throws IOException, ParseException {
        bookingChangeDetails bookingChangeDetails1=bookingChangeDetailsRepository.findById(BookingChangeId).orElseThrow(()->new RuntimeException("not found"));
        Payment payment=bookingChangeDetails1.getPayment();
        booking booking=bookingChangeDetails1.getBooking();
        PaymentDTO paymentDTO=VnPay.verifyPayment(payment,"CHANGE",req);
        if (paymentDTO.isCheck()){
            payment.setStatus(PaymentStatus.SUCCESS.getMessage());
            payment.setTransactionNo(paymentDTO.getTransCode());
            paymentRepository.save(payment);
            String username=booking.getActor().getUsername();
            boolean Check=bookingChangeDetails1.isChecks();
            booking.setStatus(bookingStatus.THAYDOI.getMessage());
            bookingChangeDetailsService.setStatusBookingDetailsService(bookingChangeDetails1.getId(),booking.getId(),username,"DATHANHTOAN",Check);
            bookingRepository.save(booking);
            AlertPayment alertPayment=new AlertPayment().builder()
                    .message("Ban da thanh toan thanh cong")
                    .status(PaymentStatus.SUCCESS.getMessage())
                    .build();
            PaymentResponse paymentResponse=new PaymentResponse().builder()
                    .Email(payment.getBookingChangeDetails().getBooking().getActor().getEmail())
                    .phoneNumber(payment.getBookingChangeDetails().getBooking().getActor().getPhone())
                    .bookingId(bookingChangeDetails1.getBooking().getId())
                    .nameuser(payment.getBookingChangeDetails().getBooking().getActor().getFullname())
                    .Status(payment.getStatus())
                    .TotalPrice(payment.getTotalPrice())
                    .timeCreate(payment.getCreate_at())
                    .build();
            EmailChange.add(paymentResponse);
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
    public AlertPayment RefundPayment(Long BookingId, HttpServletRequest req) throws IOException, ParseException, ServletException {
        booking booking=bookingRepository.findById(BookingId).orElseThrow(()->new ResourceNotFoundException("not found"));

        Payment payment=booking.getPayment();
        LocalDate date=LocalDate.now();
        if (date.isAfter(booking.getBookingdetailsList().get(0).getCheckInDate())){
            AlertPayment alertPayment=new AlertPayment().builder()
                    .status("invalid")
                    .message("bạn đã quá hạn để được hoàn tiền")
                    .build();
            return  alertPayment;
        }
        boolean RefundCheck=VnPay.refundBooking(payment,req);
        if (!RefundCheck){
            AlertPayment alertPayment=new AlertPayment().builder()
                    .status("invalid")
                    .message("Hoàn tiền thất bại")
                    .build();
            return  alertPayment;
        }
        AlertPayment alertPayment=new AlertPayment().builder()
                .status("Success")
                .message("Bạn đã được hoàn tiền thành công")
                .build();
        return  alertPayment;
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
    @Scheduled(fixedRate = 1000)
    @Transactional
    public void SendEmailChangeBooking(){
        while (!EmailChange.isEmpty()) {

            PaymentResponse email = EmailChange.poll();
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

                emailServiceimpl.sendEmail(email.getEmail(), "Thông báo chi tiết thay đổi", Booking);
            }
        }
    }

}
