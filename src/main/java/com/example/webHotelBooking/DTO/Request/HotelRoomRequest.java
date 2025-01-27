package com.example.webHotelBooking.DTO.Request;

import lombok.*;

import java.util.List;

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
    private List<RoomFeatureDTO> roomFeatureDTOS;
    private String image;
}
