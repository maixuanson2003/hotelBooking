package com.example.webHotelBooking.DTO.Request;

import jakarta.persistence.Column;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class cityDTO {
    private Long CodeCity;
    private String nameCity;
    private String Descrription;
    private String image;
}
