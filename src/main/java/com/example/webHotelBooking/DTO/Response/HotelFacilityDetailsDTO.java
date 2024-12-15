package com.example.webHotelBooking.DTO.Response;

import com.example.webHotelBooking.Entity.HotelFacilityDetails;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HotelFacilityDetailsDTO {
    private String nameHotelFacility;
    private String desCription;
    public  HotelFacilityDetailsDTO(HotelFacilityDetails hotelFacilityDetails){
        this.nameHotelFacility=hotelFacilityDetails.getHotelFacility().getNameHotelFacility();
        this.desCription=hotelFacilityDetails.getDesCription();
    }

}
