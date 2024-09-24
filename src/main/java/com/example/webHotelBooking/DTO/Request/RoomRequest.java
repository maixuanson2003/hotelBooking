package com.example.webHotelBooking.DTO.Request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomRequest {
    private Long numberPeople;
    private String CheckinDate;
    private String checkOutDate;
    private Long AmountRoom;
}
