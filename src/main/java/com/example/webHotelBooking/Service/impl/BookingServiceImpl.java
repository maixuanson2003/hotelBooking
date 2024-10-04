package com.example.webHotelBooking.Service.impl;

import com.example.webHotelBooking.DTO.Request.BookingRequest;
import com.example.webHotelBooking.DTO.Request.RoombookRequest;
import com.example.webHotelBooking.DTO.Response.BookingResponse;
import com.example.webHotelBooking.Entity.actor;
import com.example.webHotelBooking.Entity.booking;
import com.example.webHotelBooking.Entity.bookingdetails;
import com.example.webHotelBooking.Enums.bookingStatus;
import com.example.webHotelBooking.Exception.ResourceNotFoundException;
import com.example.webHotelBooking.Repository.bookingRepository;
import com.example.webHotelBooking.Repository.userRepository;
import com.example.webHotelBooking.Service.BookingService;
import com.example.webHotelBooking.Service.bookingDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
@Service
public class BookingServiceImpl implements BookingService {
    @Autowired
    private bookingRepository bookingRepository;
    @Autowired
    private bookingDetailsService bookingDetailsService;
    @Autowired
    private userRepository userRepository;
    @Override
    public BookingResponse CreateBooking(BookingRequest bookingRequest, String username) {
        actor actor=userRepository.findByUsername(username);
        if (actor==null){
            throw new ResourceNotFoundException("not found");
        }
        int totalRoom=0;
        List<RoombookRequest> roombookRequestList=bookingRequest.getTypeRoom();
        for (RoombookRequest roombookRequest:roombookRequestList){
            totalRoom+=roombookRequest.getAmountRoom();
        }
        booking booking=new booking().builder()
                .CreateAt(LocalDate.now().toString())
                .actor(actor)
                .totalRoom(totalRoom)
                .numberPeople(bookingRequest.getNumberPeople())
                .status(bookingStatus.CHUA_THANH_TOAN.getMessage())
               .build();
        booking booking1= bookingRepository.save(booking);
        for (RoombookRequest roombookRequest:roombookRequestList){
           bookingDetailsService.CreateBookingDetails(roombookRequest,bookingRequest.getHotelId(),bookingRequest.getCheckinDate(),bookingRequest.getCheckOutDate(),bookingRequest.getSaleCode(),booking1.getId());
        }
        List<bookingdetails> bookingList=booking1.getBookingdetailsList();
        long totalPrice=0;
        for (bookingdetails bookingdetails:bookingList){
            totalPrice+=bookingdetails.getPrice();
        }
        booking1.setTotalPrice(totalPrice);
        booking booking2= bookingRepository.save(booking1);
        BookingResponse bookingResponse=new BookingResponse(booking2);
        return bookingResponse;
    }

    @Override
    public List<BookingResponse> GetAllBookingResponse() {
        return List.of();
    }

    @Override
    public List<BookingResponse> GetBookingByUser(String username) {
        return List.of();
    }

    @Override
    public BookingResponse GetBookingById(String username, Long bookingId) {
        return null;
    }

    @Override
    public void ChangeScheduleBooking(Long BookingId, String CheckoutDate, String CheckInDate) {

    }

    @Override
    public void CancleBooking(Long BookingId, String username) {

    }
}
