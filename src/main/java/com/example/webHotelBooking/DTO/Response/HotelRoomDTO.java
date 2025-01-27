package com.example.webHotelBooking.DTO.Response;

import com.example.webHotelBooking.DTO.Request.RoomFeatureDTO;
import com.example.webHotelBooking.Entity.HotelRoom;
import com.example.webHotelBooking.Entity.HotelRoomFeatures;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HotelRoomDTO {
    private Long id;
    private int Amount;
    private String typeRoom;
    private String status;
    private Long numberPeople;
    private long pricePerNight;
    private List<String> nameFeature;

    private String image;
    public  HotelRoomDTO(HotelRoom hotelRoom){
        this.id=hotelRoom.getId();
        this.Amount=hotelRoom.getAmountRoom();
        this.typeRoom=hotelRoom.getTypeRoom();
        List<String> namefeatureList=new ArrayList<>();
        for (HotelRoomFeatures hotelRoomFeatures:hotelRoom.getHotelRoomFeaturesList()){
            namefeatureList.add(hotelRoomFeatures.getNameFeatures());
        }
        this.nameFeature=namefeatureList;
        this.status=hotelRoom.getStatus();
        this.numberPeople=hotelRoom.getNumberPeople();
        this.pricePerNight=hotelRoom.getPricePerNight();
        this.image=hotelRoom.getImage();
    }
}
