package com.example.webHotelBooking.Service.impl;

import com.example.webHotelBooking.DTO.Response.BookingDetailsResponse;
import com.example.webHotelBooking.DTO.Response.MyApiResponse;
import com.example.webHotelBooking.DTO.Response.bookingChangeDetailsDto;
import com.example.webHotelBooking.Entity.*;
import com.example.webHotelBooking.Enums.bookingChangeStatus;
import com.example.webHotelBooking.Enums.bookingStatus;
import com.example.webHotelBooking.Exception.ResourceNotFoundException;
import com.example.webHotelBooking.Repository.*;
import com.example.webHotelBooking.Service.BookingChangeDetailsService;
import com.example.webHotelBooking.Service.bookingDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class bookingChangeDetailsServiceimpl implements BookingChangeDetailsService {
    @Autowired
    private bookingdetailsRepository bookingdetailsRepository;
    @Autowired
    private HotelRoomRepository hotelRoomRepository;
    @Autowired
    private userRepository userRepository;
    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private bookingDetailsService bookingDetailsService;
    @Autowired
    private bookingChangeDetailsRepository bookingChangeDetailsRepository;
    @Autowired
    private EmailServiceimpl emailServiceimpl;
    @Autowired
    private bookingRepository bookingRepository;

    @Override
    public MyApiResponse setStatusBookingDetailsService(Long booKingChangeId, Long bookingid, String username, String status,boolean Check) {
        actor actor = userRepository.findByUsername(username);
        if (actor == null) {
            throw new ResourceNotFoundException("not found");
        }
        List<booking> bookingList = actor.getBookings();
        for (booking booking : bookingList) {
            if (booking.getId() == bookingid) {
                System.out.println("go");
                List<bookingChangeDetails> bookingChangeDetails = booking.getBookingChangeDetailsList();
                for (bookingChangeDetails bookingChangeDetails1 : bookingChangeDetails) {
                    System.out.println(bookingChangeDetails1.getId());
                    if (Objects.equals(bookingChangeDetails1.getId(), booKingChangeId)) {
                        switch (status) {
                            case "TUCHOI":
                                System.out.println("go");
                                bookingChangeDetails1.setStatus(status);
                                bookingChangeDetailsRepository.save(bookingChangeDetails1);
                                String Content = "Đơn" + " " + booking.getId().toString() + " " + "của" + " " + booking.getActor().getUsername() + " " + "ngày check in" + bookingChangeDetails1.getCheckInDate().toString() + " ngày Check out" + " " + bookingChangeDetails1.getCheckOutDate().toString() + " " + " Từ Chối yêu cầu";
                                String userEmail = bookingChangeDetails1.getBooking().getActor().getEmail();
                                emailServiceimpl.sendAsyncEmail(userEmail, "xử lý yêu cầu", Content);
                                break;
                            case "XACNHAN":
                                if(!Check){
                                    System.out.println("go");
                                    bookingChangeDetails1.setStatus(bookingStatus.CHUA_THANH_TOAN.getMessage());
                                    bookingChangeDetailsRepository.save(bookingChangeDetails1);
                                    String Content2 = "Đơn" + " " + booking.getId().toString() + " " + "của" + " " + booking.getActor().getUsername() + " " + "ngày check in" + bookingChangeDetails1.getCheckInDate().toString() + " ngày Check out" + " " + bookingChangeDetails1.getCheckOutDate().toString() + " " +"/n"+
                                            "Mời thanh toán qua số tài khoản" +booking.getBookingdetailsList().get(0).getHotelRoom().getHotel().getBankaccountnumber()+"Ngân hàng"+booking.getBookingdetailsList().get(0).getHotelRoom().getHotel().getBankName();
                                    String userEmail2 = bookingChangeDetails1.getBooking().getActor().getEmail();
                                    emailServiceimpl.sendAsyncEmail(userEmail2, "xử lý yêu cầu", Content2);
                                }else {
                                    System.out.println("go");
                                    bookingChangeDetails1.setStatus(status);
                                    bookingChangeDetailsRepository.save(bookingChangeDetails1);
                                    List<bookingdetails> bookingdetailsList = booking.getBookingdetailsList();
                                    for (bookingdetails bookingdetails : bookingdetailsList) {
                                        bookingDetailsService.ChangeScheduleBookingDetails(bookingdetails.getHotelRoom().getTypeRoom(), bookingChangeDetails1.getBooking().getBookingdetailsList().get(0).getHotelRoom().getHotel().getId(), bookingChangeDetails1.getCheckOutDate(), bookingChangeDetails1.getCheckInDate(), booking.getId());
                                    }
                                    String Content2 = "Đơn" + " " + booking.getId().toString() + " " + "của" + " " + booking.getActor().getUsername() + " " + "ngày check in" + bookingChangeDetails1.getCheckInDate().toString() + " ngày Check out" + " " + bookingChangeDetails1.getCheckOutDate().toString() + " " + "Xác nhận yêu cầu";
                                    String userEmail2 = bookingChangeDetails1.getBooking().getActor().getEmail();
                                    booking.setStatus(bookingStatus.THAYDOI.getMessage());
                                    booking.setChangeCount(booking.getChangeCount()+1);
                                    bookingRepository.save(booking);
                                    emailServiceimpl.sendAsyncEmail(userEmail2, "xử lý yêu cầu", Content2);
                                }
                                break;
                            case "DATHANHTOAN":
                                bookingChangeDetails1.setStatus(status);
                                bookingChangeDetailsRepository.save(bookingChangeDetails1);
                                List<bookingdetails> bookingdetailsList = booking.getBookingdetailsList();
                                for (bookingdetails bookingdetails : bookingdetailsList) {
                                    bookingDetailsService.ChangeScheduleBookingDetails(bookingdetails.getHotelRoom().getTypeRoom(), bookingChangeDetails1.getBooking().getBookingdetailsList().get(0).getHotelRoom().getHotel().getId(), bookingChangeDetails1.getCheckOutDate(), bookingChangeDetails1.getCheckInDate(), booking.getId());
                                }
                                String Content2 = "Đơn" + " " + booking.getId().toString() + " " + "của" + " " + booking.getActor().getUsername() + " " + "ngày check in" + bookingChangeDetails1.getCheckInDate().toString() + " ngày Check out" + " " + bookingChangeDetails1.getCheckOutDate().toString() + " " + "đã xử lý đổi lịch";
                                String userEmail2 = bookingChangeDetails1.getBooking().getActor().getEmail();
                                booking.setStatus(bookingStatus.THAYDOI.getMessage());
                                booking.setChangeCount(booking.getChangeCount()+1);
                                bookingRepository.save(booking);
                                emailServiceimpl.sendAsyncEmail(userEmail2, "xử lý yêu cầu", Content2);
                                break;
                            default:
                                break;
                        }
                    }
                }
            }
        }
        MyApiResponse myApiResponse = new MyApiResponse().builder()
                .bookingChangeId(booKingChangeId)
                .message("UpdateSuccess")
                .Check(true)
                .build();
        return myApiResponse;
    }

    @Override
    public List<bookingChangeDetailsDto> getListBookingChange() {
        List<bookingChangeDetails> bookingChangeDetails = bookingChangeDetailsRepository.findAll();
        List<bookingChangeDetailsDto> bookingChangDetailsDtos = new ArrayList<>();
        for (bookingChangeDetails bookingChangeDetails1 : bookingChangeDetails) {
            bookingChangeDetailsDto bookingChangeDetailsDto = new bookingChangeDetailsDto(bookingChangeDetails1);
            bookingChangDetailsDtos.add(bookingChangeDetailsDto);
        }
        return bookingChangDetailsDtos;
    }

    @Override
    public List<bookingChangeDetailsDto> getListBookingChangeByUser(String userName) {
        actor actor = userRepository.findByUsername(userName);
        if (actor == null) {
            throw new ResourceNotFoundException("not found");
        }
        List<bookingChangeDetailsDto> bookingChangeDetailsDtos = new ArrayList<>();
        List<booking> bookingList = actor.getBookings();
        for (booking booking : bookingList) {
            List<bookingChangeDetails> bookingChangeDetails = booking.getBookingChangeDetailsList();
            for (bookingChangeDetails bookingChangeDetails1 : bookingChangeDetails) {
                bookingChangeDetailsDto bookingChangeDetailsDto = new bookingChangeDetailsDto(bookingChangeDetails1);
                bookingChangeDetailsDtos.add(bookingChangeDetailsDto);
            }
        }
        return bookingChangeDetailsDtos;
    }

    @Override
    public List<bookingChangeDetailsDto> getListBookingChangeByHotel(Long HotelId) {
        List<bookingChangeDetails> bookingChangeDetails = bookingChangeDetailsRepository.findAll();
        List<bookingChangeDetailsDto> bookingChangeDetailsDtos = new ArrayList<>();
        for (bookingChangeDetails bookingChangeDetails1 : bookingChangeDetails) {
            if (bookingChangeDetails1.getBooking().getBookingdetailsList() == null ||
                    bookingChangeDetails1.getBooking().getBookingdetailsList().isEmpty()) {
                continue;
            }
            if (bookingChangeDetails1.getBooking().getBookingdetailsList().get(0).getHotelRoom().getHotel().getId() == HotelId) {
                bookingChangeDetailsDto bookingChangeDetailsDto = new bookingChangeDetailsDto(bookingChangeDetails1);
                bookingChangeDetailsDtos.add(bookingChangeDetailsDto);
            }
        }
        return bookingChangeDetailsDtos;
    }
}
