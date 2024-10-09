package com.example.webHotelBooking.DTO.Response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HotelPolicyDTO {
    private String namePolicy;
    private String Description;
    private Boolean isRelatedFee;
}
