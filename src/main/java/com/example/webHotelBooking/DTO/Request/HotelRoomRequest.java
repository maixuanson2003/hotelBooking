package com.example.webHotelBooking.DTO.Request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HotelRoomRequest {
    private int Amount;
    private String typeRoom;
    private String status;
    private Long numberPeople;
    private long pricePerNight;
    private String image;
}
