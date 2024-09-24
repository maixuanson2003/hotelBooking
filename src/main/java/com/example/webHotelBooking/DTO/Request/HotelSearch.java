package com.example.webHotelBooking.DTO.Request;

import lombok.*;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HotelSearch {
    private List<String> hotelPolicyList;
    private Integer starpoint;
}
