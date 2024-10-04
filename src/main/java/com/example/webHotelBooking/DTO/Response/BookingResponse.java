package com.example.webHotelBooking.DTO.Response;

import com.example.webHotelBooking.Entity.actor;
import com.example.webHotelBooking.Entity.booking;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingResponse {
    private Long id;
    private int totalRoom;
    private String username;
    private long totalPrice;
    private String CreateAt;
    private String status;
    private int numberPeople;
    public  BookingResponse(booking booking2){
        this.id=booking2.getId();
        this.totalRoom=booking2.getTotalRoom();
        this.username=booking2.getActor().getUsername();
        this.totalPrice=booking2.getTotalPrice();
        this.CreateAt=booking2.getCreateAt();
        this.status=booking2.getStatus();
        this.numberPeople=booking2.getNumberPeople();
    }
}
