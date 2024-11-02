package com.example.webHotelBooking.DTO.Response;

import com.example.webHotelBooking.Entity.HotelPolicyDetails;
import lombok.*;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HotelResonse {
    private Long id;
    private String name;
    private String address;
    private String City;
    private Integer  starPoint;
    private List<HotelFacilityDTO> hotelFacilityList;
    private List<HotelPolicyDetailsDTO> hotelPolicyDTOList;
    private List<HotelRoomDTO> hotelRoomDTOList;
    private List<String> imageList;

}
