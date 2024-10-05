package com.example.webHotelBooking.DTO.Request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoombookRequest {
    private String TypeRoom;
    private int AmountRoom;
}
