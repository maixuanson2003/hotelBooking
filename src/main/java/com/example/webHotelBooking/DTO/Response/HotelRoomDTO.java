package com.example.webHotelBooking.DTO.Response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HotelRoomDTO {
    private int Amount;
    private String typeRoom;
    private String status;
    private Long numberPeople;
    private long pricePerNight;
    private String image;
}
