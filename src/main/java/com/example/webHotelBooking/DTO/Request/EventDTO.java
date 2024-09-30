package com.example.webHotelBooking.DTO.Request;

import jakarta.persistence.Column;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventDTO {
    private String nameEvent;
    private String description;
    private String DateStart;
    private String DateEnd;
    private String image;
}
