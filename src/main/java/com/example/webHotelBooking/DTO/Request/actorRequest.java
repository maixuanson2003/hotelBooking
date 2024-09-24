package com.example.webHotelBooking.DTO.Request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class actorRequest {
    private String fullName;
    private String username;
    private String password;
    private String Gender;
    private String phone;
    private String email;
    private String birthday;
    private String address;
}
