package com.example.webHotelBooking.DTO.Request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentRequest {
    private Long BookingId;
    private long price;
    private String actorname;
    private String phoneNumber;
}
