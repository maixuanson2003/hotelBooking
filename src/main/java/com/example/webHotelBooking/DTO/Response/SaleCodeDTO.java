package com.example.webHotelBooking.DTO.Response;

import jakarta.persistence.Column;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaleCodeDTO {
    private Long id;
    private String Code;
    private String DateStart;
    private String DateEnd;
    private String Description;
    private long discountPercentage;
    private String image;
    private  String title;
}
