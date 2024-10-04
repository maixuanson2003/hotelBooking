package com.example.webHotelBooking.Service.impl;

import com.example.webHotelBooking.DTO.Request.RoombookRequest;
import com.example.webHotelBooking.DTO.Response.BookingDetailsResponse;
import com.example.webHotelBooking.Entity.Hotel;
import com.example.webHotelBooking.Entity.HotelRoom;
import com.example.webHotelBooking.Entity.booking;
import com.example.webHotelBooking.Entity.bookingdetails;
import com.example.webHotelBooking.Entity.saleCode;
import com.example.webHotelBooking.Exception.ResourceNotFoundException;
import com.example.webHotelBooking.Repository.*;
import com.example.webHotelBooking.Service.bookingDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
@Service
public class BookingDetailsServiceImpl implements bookingDetailsService {
    private final long VAT_PERCENTAGE = 10L;
    @Autowired
    private bookingdetailsRepository bookingdetailsRepository;
    @Autowired
    private HotelRoomRepository hotelRoomRepository;
    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private saleCodeRepository saleCodeRepository;
    @Autowired
    private bookingRepository bookingRepository;
    private HotelRoom findHotelRoomByRoomType(String roomType, Long Hotelid){
        Hotel hotel=hotelRepository.findById(Hotelid).orElseThrow(()->new ResourceNotFoundException("not found"));
        List<HotelRoom> hotelRoomList=hotel.getHotelRoomList();
        for (HotelRoom hotelRoom:hotelRoomList){
            if (hotelRoom.getTypeRoom().equals(roomType)){
                return hotelRoom;
            }
        }
        return null;
    }
    private saleCode findSaleCode(String saleCode){
        List<saleCode> SaleCodes=saleCodeRepository.findAll();
        for (saleCode saleCode1:SaleCodes){
            if (saleCode1.getCode().equals(saleCode)){
                return saleCode1;
            }
        }
        return null;
    }
    @Override
    public void CreateBookingDetails(RoombookRequest typeRoom, Long HotelId, LocalDate CheckinDate, LocalDate CheckoutDate, String saleCode, Long bookingid) {
        HotelRoom hotelRoom=findHotelRoomByRoomType(typeRoom.getTypeRoom(),HotelId);
        booking booking=bookingRepository.findById(bookingid).orElseThrow(()->new ResourceNotFoundException("not found"));
        saleCode salecode=findSaleCode(saleCode);
        long totalPrice = hotelRoom.getPricePerNight();
        long daysBetween = ChronoUnit.DAYS.between(CheckinDate,  CheckoutDate);
        long originalPrice = totalPrice * daysBetween;
        if (salecode != null) {
            double discount = salecode.getDiscountPercentage();
            originalPrice -= (originalPrice * discount);
        }
        long priceWithVAT = originalPrice + (long)(originalPrice *  VAT_PERCENTAGE);
        if (hotelRoom==null){
            throw new ResourceNotFoundException("not found");
        }
        if (salecode==null){
            throw new ResourceNotFoundException("not found");
        }
        bookingdetails bookingdetails=new bookingdetails().builder()
                .booking(booking)
                .createAt(LocalDate.now().toString())
                .checkInDate(CheckinDate)
                .checkOutDate(CheckoutDate)
                .price(priceWithVAT)
                .amountRoom(typeRoom.getAmountRoom())
                .hotelRoom(hotelRoom)
                .build();
        bookingdetailsRepository.save(bookingdetails);
    }

    @Override
    public void DeleteBookingDetails(String typeRoom, Long bookingId) {
        booking booking=bookingRepository.findById(bookingId).orElseThrow(()->new ResourceNotFoundException("not found"));
        List<bookingdetails> bookingdetailsList=booking.getBookingdetailsList();
        boolean found = false;
        for (bookingdetails bookingdetails:bookingdetailsList){
            if (bookingdetails.getHotelRoom().getTypeRoom().equals(typeRoom)){
                bookingdetailsRepository.delete(bookingdetails);
                found = true;
                break;
            }
        }
        if (!found) {
            throw new ResourceNotFoundException("Booking details with room type " + typeRoom + " not found");
        }
    }

    @Override
    public List<BookingDetailsResponse> getBookingDetailsByBooking(Long bookingId) {
        booking booking=bookingRepository.findById(bookingId).orElseThrow(()->new ResourceNotFoundException("not found"));
        List<bookingdetails> bookingdetailsList=booking.getBookingdetailsList();
        List<BookingDetailsResponse> bookingDetailsResponses=new ArrayList<>();
        for (bookingdetails bookingdetails:bookingdetailsList){
            BookingDetailsResponse bookingDetailsResponse=new BookingDetailsResponse(bookingdetails);
            bookingDetailsResponses.add(bookingDetailsResponse);

        }
        return bookingDetailsResponses;
    }
}
