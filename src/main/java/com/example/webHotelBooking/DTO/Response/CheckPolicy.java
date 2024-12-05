package com.example.webHotelBooking.DTO.Response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CheckPolicy {
    private boolean Check;
    private String message;
}
