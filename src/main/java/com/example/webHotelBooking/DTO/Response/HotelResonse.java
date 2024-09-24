package com.example.webHotelBooking.DTO.Response;

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
    private List<HotelFacilityDTO> hotelFacilityList;
    private List<HotelPolicyDTO> hotelPolicyDTOList;
    private List<HotelRoomDTO> hotelRoomDTOList;
    private List<String> imageList;

}
