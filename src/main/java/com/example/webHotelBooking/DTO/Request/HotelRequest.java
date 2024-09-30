package com.example.webHotelBooking.DTO.Request;

import com.example.webHotelBooking.DTO.Response.HotelFacilityDTO;
import com.example.webHotelBooking.DTO.Response.HotelPolicyDTO;
import com.example.webHotelBooking.DTO.Response.HotelRoomDTO;
import lombok.*;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HotelRequest {
    private String name;
    private String address;
    private cityDTO City;
    private Integer  NumberFloor;
    private String Hotline;
    private String Email;
    private String desCription;
    private Integer  TotalRoom;
    private Integer  MaxRoomEachFloor;
    private List<HotelFacilityDTO> hotelFacilityList;
    private List<HotelPolicyDTO> hotelPolicyDTOList;
    private List<HotelRoomDTO> hotelRoomDTOList;
    private List<String> imageList;
}
