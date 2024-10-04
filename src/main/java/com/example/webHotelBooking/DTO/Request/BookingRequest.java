package com.example.webHotelBooking.DTO.Request;

import com.example.webHotelBooking.Entity.HotelRoom;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingRequest {
   private List<RoombookRequest> typeRoom;
   private LocalDate CheckinDate;
   private LocalDate CheckOutDate;
   private String saleCode;
   private int numberPeople;
   private Long HotelId;
}
