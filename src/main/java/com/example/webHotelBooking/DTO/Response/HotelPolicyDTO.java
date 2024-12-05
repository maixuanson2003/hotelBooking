package com.example.webHotelBooking.DTO.Response;

import com.example.webHotelBooking.Entity.HotelPolicy;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class HotelPolicyDTO {
    private Long id;
    private String nameHotelPolicy;
    public  HotelPolicyDTO(HotelPolicy hotelPolicy){
        this.id= hotelPolicy.getId();
        this.nameHotelPolicy=hotelPolicy.getNamePolicy();
    }
}
