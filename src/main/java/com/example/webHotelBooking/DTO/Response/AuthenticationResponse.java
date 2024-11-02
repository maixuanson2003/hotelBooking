package com.example.webHotelBooking.DTO.Response;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthenticationResponse {
    private String token;
    private Boolean Authenticated;
    private String Type;
    private String username;
    private Long HotelId;

}
