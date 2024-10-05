package com.example.webHotelBooking.DTO.Response;

import com.example.webHotelBooking.Entity.HotelRoom;
import com.example.webHotelBooking.Entity.bookingdetails;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingDetailsResponse {
    private Long id;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private  long price;
    private  long amountRoom;
    private  String createAt;
    private String saleCode;
    private HotelRoomDTO hotelRoom;
    public  BookingDetailsResponse(bookingdetails booking){
        this.id=booking.getId();
        this.checkInDate=booking.getCheckInDate();
        this.checkOutDate=booking.getCheckOutDate();
        this.price=booking.getPrice();
        this.amountRoom=booking.getAmountRoom();
        this.createAt=booking.getCreateAt();
        this.saleCode=booking.getSaleCode().getCode();
        this.hotelRoom=new HotelRoomDTO(booking.getHotelRoom().getAmountRoom(),booking.getHotelRoom().getTypeRoom(),booking.getHotelRoom().getStatus(),booking.getHotelRoom().getNumberPeople(),booking.getHotelRoom().getPricePerNight(),booking.getHotelRoom().getImage());
    }
}
