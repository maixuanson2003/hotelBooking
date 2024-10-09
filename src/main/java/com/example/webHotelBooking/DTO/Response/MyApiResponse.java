package com.example.webHotelBooking.DTO.Response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyApiResponse {
    private Long bookingChangeId;
    private String message;
    private Boolean Check;
}
