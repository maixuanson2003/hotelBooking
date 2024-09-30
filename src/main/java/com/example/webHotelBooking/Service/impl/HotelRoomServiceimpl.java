package com.example.webHotelBooking.Service.impl;

import com.example.webHotelBooking.DTO.Response.HotelRoomDTO;
import com.example.webHotelBooking.Entity.Hotel;
import com.example.webHotelBooking.Entity.HotelPolicy;
import com.example.webHotelBooking.Entity.HotelRoom;
import com.example.webHotelBooking.Entity.HotelRoomFeatures;
import com.example.webHotelBooking.Enums.HotelStatus;
import com.example.webHotelBooking.Exception.ResourceNotFoundException;
import com.example.webHotelBooking.Repository.HotelRepository;
import com.example.webHotelBooking.Repository.HotelRoomFeaturesRepository;
import com.example.webHotelBooking.Repository.HotelRoomRepository;
import com.example.webHotelBooking.Service.HotelRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
@Service
public class HotelRoomServiceimpl implements HotelRoomService {
    @Autowired
    private HotelRoomRepository hotelRoomRepository;
    @Autowired
    private HotelRoomFeaturesRepository RoomFeaturRepo;
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
    public void UpdateRoomHotel(HotelRoomDTO hotelRoomDTO, Long Hotelid,Long roomId) {
        Hotel hotel=hotelRepository.findById(Hotelid).orElseThrow(()->new RuntimeException("not found"));
        List<HotelRoom> hotelRoomList=hotel.getHotelRoomList();
        for (HotelRoom hotelRoom:hotelRoomList){
            if (hotelRoom.getId()==roomId){
                hotelRoom.setRoomNumber(hotelRoomDTO.getRoomNumber());
                hotelRoom.setTypeRoom(hotelRoomDTO.getTypeRoom());
                hotelRoom.setFloorNumber(hotelRoomDTO.getFloorNumber());
                hotelRoomRepository.save(hotelRoom);
                break;
            }
        }
    }

    @Override
    public void DeleteRoomHotelByRoomNumber(Long HotelId, String roomNumber) {
        Hotel hotel=hotelRepository.findById(HotelId).orElseThrow(()->new RuntimeException("not found"));
        List<HotelRoom> hotelRoomList=hotel.getHotelRoomList();
        for (HotelRoom hotelRoom:hotelRoomList){
            if (hotelRoom.getRoomNumber().equals(roomNumber)){
                List<HotelRoomFeatures> hotelRoomFeaturesList=hotelRoom.getHotelRoomFeaturesList();
                hotelRoomFeaturesList.clear();
                hotelRoomRepository.save(hotelRoom);
                hotelRoomRepository.delete(hotelRoom);
                break;
            }
        }
    }

    @Override
    public List<HotelRoomDTO> GetAllHotelRoomByHotelId(Long HotelId) {
        Hotel hotel=hotelRepository.findById(HotelId).orElseThrow(()->new RuntimeException("not found"));
        List<HotelRoom> hotelRoomList=hotel.getHotelRoomList();
        List<HotelRoomDTO> hotelRoomDTOList=new ArrayList<>();
        for (HotelRoom hotelRoom:hotelRoomList){
            HotelRoomDTO hotelRoomDTO=new HotelRoomDTO(
              hotelRoom.getRoomNumber(), hotelRoom.getTypeRoom(),hotelRoom.getStatus(),hotelRoom.getNumberPeople(),hotelRoom.getFloorNumber(),hotelRoom.getPricePerNight()
            );
            hotelRoomDTOList.add(hotelRoomDTO);
        }
        return hotelRoomDTOList;
    }
    private boolean CheckCoditionResponse(List<String> NameRoomFeature,HotelRoom hotelRoom){
        Integer Value=0;
        HashMap<Integer,String> Check=new HashMap<>();
        for (String h:NameRoomFeature){
            Value++;
            Check.put(Value,h);
        }
        Value=0;
        int result=0;
        List<HotelRoomFeatures> hotelFeatureList1=hotelRoom.getHotelRoomFeaturesList();
        for (HotelRoomFeatures hotelFeature:hotelFeatureList1){

            for (int i=0;i<Check.size();i++){
                Value++;
                if (hotelFeature.getNameFeatures().equals(Check.get(Value))){
                    result++;
                }
            }
            Value=0;
        }
        if (result== NameRoomFeature.size()){
            return true;
        }else {
            return  false;
        }
    }
    private Boolean CheckResponseByPrice(Long priceStart,Long priceEnd,HotelRoom hotelRoom){
        if (hotelRoom.getPricePerNight()<=priceEnd&&hotelRoom.getPricePerNight()>=priceStart){
            return true;
        }else {
            return false;
        }
    }


    @Override
    public List<HotelRoomDTO> searChRoomByCodition(List<String> NameRoomFeature, Long priceStart, Long PriceEnd, String roomType,Long Hotelid) {
        boolean CheckCodition1= !NameRoomFeature.isEmpty() &&priceStart==null&&PriceEnd==null&&roomType==null;
        boolean CheckCodition2=!NameRoomFeature.isEmpty()&&priceStart!=null&&PriceEnd!=null&&roomType==null;
        boolean CheckCodition3=!NameRoomFeature.isEmpty()&&priceStart!=null&&PriceEnd!=null&&roomType!=null;
        boolean CheckCodition4=NameRoomFeature.isEmpty()&&priceStart!=null&&PriceEnd!=null&&roomType!=null;
        boolean CheckCodition5=NameRoomFeature.isEmpty()&&priceStart==null&&PriceEnd==null&&roomType!=null;
        Hotel hotel=hotelRepository.findById(Hotelid).orElseThrow(()->new ResourceNotFoundException("not found"));
        List<HotelRoom> hotelRoomList=hotel.getHotelRoomList();
        List<HotelRoomDTO> hotelRoomDTOList=new ArrayList<>();
        if (CheckCodition1){
            for (HotelRoom hotelRoom:hotelRoomList){
                if (this.CheckCoditionResponse(NameRoomFeature,hotelRoom)){
                    HotelRoomDTO hotelRoomDTO=new HotelRoomDTO(
                            hotelRoom.getRoomNumber(), hotelRoom.getTypeRoom(),hotelRoom.getStatus(),hotelRoom.getNumberPeople(),hotelRoom.getFloorNumber(),hotelRoom.getPricePerNight()
                    );
                    hotelRoomDTOList.add(hotelRoomDTO);
                }
            }
        }
        if (CheckCodition2){
            for (HotelRoom hotelRoom:hotelRoomList){
                if (this.CheckCoditionResponse(NameRoomFeature,hotelRoom)&&this.CheckResponseByPrice(priceStart,PriceEnd,hotelRoom)){
                    HotelRoomDTO hotelRoomDTO=new HotelRoomDTO(
                            hotelRoom.getRoomNumber(), hotelRoom.getTypeRoom(),hotelRoom.getStatus(),hotelRoom.getNumberPeople(),hotelRoom.getFloorNumber(),hotelRoom.getPricePerNight()
                    );
                    hotelRoomDTOList.add(hotelRoomDTO);
                }
            }
        }
        if (CheckCodition3){
            for (HotelRoom hotelRoom:hotelRoomList){
                if (this.CheckCoditionResponse(NameRoomFeature,hotelRoom)&&this.CheckResponseByPrice(priceStart,PriceEnd,hotelRoom)&&hotelRoom.getTypeRoom().equals(roomType)){
                    HotelRoomDTO hotelRoomDTO=new HotelRoomDTO(
                            hotelRoom.getRoomNumber(), hotelRoom.getTypeRoom(),hotelRoom.getStatus(),hotelRoom.getNumberPeople(),hotelRoom.getFloorNumber(),hotelRoom.getPricePerNight()
                    );
                    hotelRoomDTOList.add(hotelRoomDTO);
                }
            }
        }
        if (CheckCodition4){
            for (HotelRoom hotelRoom:hotelRoomList){
                if (this.CheckResponseByPrice(priceStart,PriceEnd,hotelRoom)&&hotelRoom.getTypeRoom().equals(roomType)){
                    HotelRoomDTO hotelRoomDTO=new HotelRoomDTO(
                            hotelRoom.getRoomNumber(), hotelRoom.getTypeRoom(),hotelRoom.getStatus(),hotelRoom.getNumberPeople(),hotelRoom.getFloorNumber(),hotelRoom.getPricePerNight()
                    );
                    hotelRoomDTOList.add(hotelRoomDTO);
                }
            }
        }
        if (CheckCodition5){
            for (HotelRoom hotelRoom:hotelRoomList){
                if (hotelRoom.getTypeRoom().equals(roomType)){
                    HotelRoomDTO hotelRoomDTO=new HotelRoomDTO(
                            hotelRoom.getRoomNumber(), hotelRoom.getTypeRoom(),hotelRoom.getStatus(),hotelRoom.getNumberPeople(),hotelRoom.getFloorNumber(),hotelRoom.getPricePerNight()
                    );
                    hotelRoomDTOList.add(hotelRoomDTO);
                }
            }
        }
        return  hotelRoomDTOList;
    }
}
