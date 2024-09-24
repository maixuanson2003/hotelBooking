package com.example.webHotelBooking.DTO.Response;

import lombok.*;

@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class verifyTokenResponse {
    private boolean valid;
}
