package com.example.webHotelBooking.DTO.Request;

import com.example.webHotelBooking.DTO.Response.HotelFacilityDTO;

import com.example.webHotelBooking.DTO.Response.HotelPolicyDetailsDTO;
import com.example.webHotelBooking.DTO.Response.HotelRoomDTO;
import com.example.webHotelBooking.Entity.HotelPolicyDetails;
import lombok.*;

import javax.lang.model.element.Name;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HotelRequest {
    private String name;
    private String address;
    private String City;
    private String Hotline;
    private String Email;
    private int starpoint;
    private String desCription;
    private Integer  TotalRoom;
    private Long cancelfee;
    private Long ChaneFee;
    private List<HotelFacilityDTO> hotelFacilityList;
    private List<HotelPolicyDetailsDTO> hotelPolicyDTOList;
    private List<HotelRoomDTO> hotelRoomDTOList;
    private List<String> imageList;
}
