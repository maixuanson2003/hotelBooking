package com.example.webHotelBooking.Service.impl;

import com.example.webHotelBooking.DTO.Response.HotelRoomDTO;
import com.example.webHotelBooking.Entity.Hotel;
import com.example.webHotelBooking.Entity.HotelRoom;
import com.example.webHotelBooking.Enums.HotelStatus;
import com.example.webHotelBooking.Repository.HotelRepository;
import com.example.webHotelBooking.Repository.HotelRoomRepository;
import com.example.webHotelBooking.Service.HotelRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class HotelRoomServiceimpl implements HotelRoomService {
    @Autowired
    private HotelRoomRepository hotelRoomRepository;
    @Autowired
    private HotelRepository hotelRepository;
    private List<HotelRoom> findByFloorNumber(int number){
        List<HotelRoom> hotelRoomList=hotelRoomRepository.findAll();
        List<HotelRoom> hotelRooms=new ArrayList<>();
        for (HotelRoom hotelRoom:hotelRoomList){
            if (hotelRoom.getFloorNumber()==number){
                hotelRooms.add(hotelRoom);
            }
        }
        return hotelRooms;
    }
    @Override
    public void CreateRoomHotel(HotelRoomDTO hotelRoomDTO,Long Hotelid) {
        Hotel hotel=hotelRepository.findById(Hotelid).orElseThrow(()->new RuntimeException("not found"));
        if (hotelRoomDTO.getFloorNumber()>hotel.getNumberFloor()){
            throw new RuntimeException("vượt quán số tầng");
        }
        if (findByFloorNumber(hotelRoomDTO.getFloorNumber()).size()>hotel.getMaxRoomEachFloor()){
            throw new RuntimeException("Vượt Quá số phòng Quy định");
        }
       HotelRoom hotelRoom=new HotelRoom().builder()
               .roomNumber(hotelRoomDTO.getRoomNumber())
               .typeRoom(hotelRoomDTO.getTypeRoom())
               .Hotel(hotel)
               .typeRoom(hotelRoomDTO.getTypeRoom())
               .floorNumber(hotelRoomDTO.getFloorNumber())
               .pricePerNight(hotelRoomDTO.getPricePerNight())
               .numberPeople(hotelRoomDTO.getNumberPeople())
               .numberOfBooking(0)
               .status(HotelStatus.CHUADAT.getMessage())
               .build();
        hotelRoomRepository.save(hotelRoom);
    }

    @Override
    public void UpdateRoomHotel(HotelRoomDTO hotelRoomDTO, Long Hotelid) {
        Hotel hotel=hotelRepository.findById(Hotelid).orElseThrow(()->new RuntimeException("not found"));
        HotelRoom hotelRoom=new HotelRoom(hotelRoomDTO.getRoomNumber(),hotelRoomDTO.getTypeRoom(),hotelRoomDTO.getStatus(),hotelRoomDTO.getNumberPeople(),hotelRoomDTO.getPricePerNight(),hotelRoomDTO.getFloorNumber(),hotel);
        hotelRoomRepository.save(hotelRoom);
    }
}
