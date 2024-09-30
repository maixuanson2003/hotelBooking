package com.example.webHotelBooking.DTO.Response;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountHotelDTO {

    private Long id;
    private String username;
    private String HotelName;
}
