package com.example.webHotelBooking.DTO.Response;

import com.example.webHotelBooking.Entity.Hotel;
import lombok.*;

import java.util.ArrayList;
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
    private String Hotline;
    private String Email;
    private int starpoint;
    private String desCription;
    private Integer  TotalRoom;
    private Long  Bankaccountnumber;
    private String  BankName;
    private List<HotelFacilityDetailsDTO> hotelFacilityList;
    private List<HotelPolicyDetailsDTO> hotelPolicyDTOList;
    private List<HotelRoomDTO> hotelRoomDTOList;
    private List<String> imageList;
    public  HotelResonse(Hotel hotel){
        this.id=hotel.getId();
        this.name=hotel.getNameHotel();
        this.address=hotel.getAddress();
        this.City=hotel.getCity().getNameCity();
        this.Hotline=hotel.getHotline();
        this.Email=hotel.getEmail();
        this.starpoint=hotel.getStarPoint();
        this.desCription=hotel.getDescription();
        this.TotalRoom=hotel.getTotalRoom();
        this.Bankaccountnumber=hotel.getBankaccountnumber();
        this.BankName=hotel.getBankName();

    }


}
