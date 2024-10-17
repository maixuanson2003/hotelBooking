package com.example.webHotelBooking.DTO.Response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Chatinfor {
    private Long Messageid;
    private String userSender;
    private String Content;
    private String userRceive;
}
