package com.example.webHotelBooking.DTO.Response;

import com.example.webHotelBooking.Entity.HotelPolicyDetails;
import jakarta.persistence.Column;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HotelPolicyDetailsDTO {
    private String namePolicy;
    private String description;
    private Long fee=null;
    private String coditionalInfo=null;
    private String note;
    private Long beforeDayAmount;
    public HotelPolicyDetailsDTO(HotelPolicyDetails hotelPolicyDetails){
        namePolicy=hotelPolicyDetails.getHotelPolicy().getNamePolicy();
        description= hotelPolicyDetails.getDescription();
        fee= hotelPolicyDetails.getFee();
        coditionalInfo= hotelPolicyDetails.getCoditionalInfo();
        note=hotelPolicyDetails.getNote();
        beforeDayAmount=hotelPolicyDetails.getBeforeDayAmount();
    }
}
