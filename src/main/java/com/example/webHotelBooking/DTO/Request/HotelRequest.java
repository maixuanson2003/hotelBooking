package com.example.webHotelBooking.DTO.Request;

import com.example.webHotelBooking.DTO.Response.HotelFacilityDTO;

import com.example.webHotelBooking.DTO.Response.HotelFacilityDetailsDTO;
import com.example.webHotelBooking.DTO.Response.HotelPolicyDetailsDTO;
import com.example.webHotelBooking.DTO.Response.HotelRoomDTO;
import com.example.webHotelBooking.Entity.HotelPolicyDetails;
import jakarta.persistence.Column;
import lombok.*;

import javax.lang.model.element.Name;
import java.util.ArrayList;
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
    private Long  Bankaccountnumber;
    private String  BankName;
    private List<HotelFacilityDetailsDTO> hotelFacilityList=new ArrayList<>();
    private List<HotelPolicyDetailsDTO> hotelPolicyDTOList=new ArrayList<>();
    private List<HotelRoomDTO> hotelRoomDTOList;
    private List<String> imageList;
}
