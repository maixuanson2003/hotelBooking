package com.example.webHotelBooking.Service.impl;

import com.example.webHotelBooking.DTO.Response.HotelRoomDTO;
import com.example.webHotelBooking.Entity.*;
import com.example.webHotelBooking.Enums.HotelStatus;
import com.example.webHotelBooking.Exception.ResourceNotFoundException;
import com.example.webHotelBooking.Repository.HotelRepository;
import com.example.webHotelBooking.Repository.HotelRoomFeaturesRepository;
import com.example.webHotelBooking.Repository.HotelRoomRepository;
import com.example.webHotelBooking.Service.HotelRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
@Service
public class HotelRoomServiceimpl implements HotelRoomService {
    private final SimpMessagingTemplate messagingTemplate;

    public HotelRoomServiceimpl(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }
    @Autowired
    private HotelRoomRepository hotelRoomRepository;
    @Autowired
    private HotelRoomFeaturesRepository RoomFeaturRepo;
    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private JwtDecoder jwtDecoder;


    @Override
    public void CreateRoomHotel(HotelRoomDTO hotelRoomDTO,Long Hotelid) {
        Hotel hotel=hotelRepository.findById(Hotelid).orElseThrow(()->new RuntimeException("not found"));
        HotelRoom hotelRoom=new HotelRoom().builder()
               .AmountRoom(hotelRoomDTO.getAmount())
               .typeRoom(hotelRoomDTO.getTypeRoom())
               .Hotel(hotel)
               .pricePerNight(hotelRoomDTO.getPricePerNight())
               .numberPeople(hotelRoomDTO.getNumberPeople())
               .numberOfBooking(0)
               .image(hotelRoomDTO.getImage())
               .status(HotelStatus.CONPHONG.getMessage())
               .build();
        hotelRoomRepository.save(hotelRoom);
    }

    @Override
    public void UpdateRoomHotel(HotelRoomDTO hotelRoomDTO, Long Hotelid,Long roomId) {
        Hotel hotel=hotelRepository.findById(Hotelid).orElseThrow(()->new RuntimeException("not found"));
        List<HotelRoom> hotelRoomList=hotel.getHotelRoomList();
        for (HotelRoom hotelRoom:hotelRoomList){
            if (hotelRoom.getId()==roomId){
                hotelRoom.setAmountRoom(hotelRoomDTO.getAmount());
                hotelRoom.setTypeRoom(hotelRoomDTO.getTypeRoom());
                hotelRoomRepository.save(hotelRoom);
                break;
            }
        }
    }

    @Override
    public void DeleteRoomHotelByRoomType(Long HotelId, String roomType) {
        Hotel hotel=hotelRepository.findById(HotelId).orElseThrow(()->new RuntimeException("not found"));
        List<HotelRoom> hotelRoomList=hotel.getHotelRoomList();
        for (HotelRoom hotelRoom:hotelRoomList){
            if (hotelRoom.getTypeRoom().equals(roomType)){
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
                    hotelRoom.getNumbeRoomLast(), hotelRoom.getTypeRoom(),hotelRoom.getStatus(),hotelRoom.getNumberPeople(),hotelRoom.getPricePerNight(),hotelRoom.getImage()
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
                            hotelRoom.getAmountRoom(),hotelRoom.getTypeRoom(),hotelRoom.getStatus(),hotelRoom.getNumberPeople(),hotelRoom.getPricePerNight(),hotelRoom.getImage()
                    );
                    hotelRoomDTOList.add(hotelRoomDTO);
                }
            }
        }
        if (CheckCodition2){
            for (HotelRoom hotelRoom:hotelRoomList){
                if (this.CheckCoditionResponse(NameRoomFeature,hotelRoom)&&this.CheckResponseByPrice(priceStart,PriceEnd,hotelRoom)){
                    HotelRoomDTO hotelRoomDTO=new HotelRoomDTO(
                            hotelRoom.getAmountRoom(),hotelRoom.getTypeRoom(),hotelRoom.getStatus(),hotelRoom.getNumberPeople(),hotelRoom.getPricePerNight(),hotelRoom.getImage()
                    );
                    hotelRoomDTOList.add(hotelRoomDTO);
                }
            }
        }
        if (CheckCodition3){
            for (HotelRoom hotelRoom:hotelRoomList){
                if (this.CheckCoditionResponse(NameRoomFeature,hotelRoom)&&this.CheckResponseByPrice(priceStart,PriceEnd,hotelRoom)&&hotelRoom.getTypeRoom().equals(roomType)){
                    HotelRoomDTO hotelRoomDTO=new HotelRoomDTO(
                            hotelRoom.getAmountRoom(), hotelRoom.getTypeRoom(),hotelRoom.getStatus(),hotelRoom.getNumberPeople(),hotelRoom.getPricePerNight(),hotelRoom.getImage()
                    );
                    hotelRoomDTOList.add(hotelRoomDTO);
                }
            }
        }
        if (CheckCodition4){
            for (HotelRoom hotelRoom:hotelRoomList){
                if (this.CheckResponseByPrice(priceStart,PriceEnd,hotelRoom)&&hotelRoom.getTypeRoom().equals(roomType)){
                    HotelRoomDTO hotelRoomDTO=new HotelRoomDTO(
                            hotelRoom.getAmountRoom(), hotelRoom.getTypeRoom(),hotelRoom.getStatus(),hotelRoom.getNumberPeople(),hotelRoom.getPricePerNight(),hotelRoom.getImage()
                    );
                    hotelRoomDTOList.add(hotelRoomDTO);
                }
            }
        }
        if (CheckCodition5){
            for (HotelRoom hotelRoom:hotelRoomList){
                if (hotelRoom.getTypeRoom().equals(roomType)){
                    HotelRoomDTO hotelRoomDTO=new HotelRoomDTO(
                            hotelRoom.getAmountRoom(),hotelRoom.getTypeRoom(),hotelRoom.getStatus(),hotelRoom.getNumberPeople(),hotelRoom.getPricePerNight(),hotelRoom.getImage()
                    );
                    hotelRoomDTOList.add(hotelRoomDTO);
                }
            }
        }
        return  hotelRoomDTOList;
    }

    @Override
    public List<HotelRoomDTO> GetRoomByHotel(String token) {
        Jwt jwt=jwtDecoder.decode(token);
        Long HotelId = jwt.getClaim("HotelId");
        Hotel hotel=hotelRepository.findById(HotelId).orElseThrow(()->new ResourceNotFoundException("not found"));
        List<HotelRoom> hotelRoomList=hotel.getHotelRoomList();
        List<HotelRoomDTO> hotelRoomDTOList=new ArrayList<>();
        for (HotelRoom hotelRoom:hotelRoomList){
            HotelRoomDTO hotelRoomDTO=new HotelRoomDTO(
                    hotelRoom.getNumbeRoomLast(), hotelRoom.getTypeRoom(),hotelRoom.getStatus(),hotelRoom.getNumberPeople(),hotelRoom.getPricePerNight(),hotelRoom.getImage()
            );
            hotelRoomDTOList.add(hotelRoomDTO);
        }
        return hotelRoomDTOList;
    }
    private HotelRoom findHotelRoomByRoomType(String roomType,Long Hotelid){
        Hotel hotel=hotelRepository.findById(Hotelid).orElseThrow(()->new ResourceNotFoundException("not found"));
        List<HotelRoom> hotelRoomList=hotel.getHotelRoomList();
        for (HotelRoom hotelRoom:hotelRoomList){
            if (hotelRoom.getTypeRoom().equals(roomType)){
                return hotelRoom;
            }
        }
        return null;
    }

    @Override
    public void setAmountRoom(String TypeRoom,Long Hotelid,int Amount) {
        HotelRoom hotelRoom=findHotelRoomByRoomType(TypeRoom,Hotelid);
        String status=hotelRoom.getStatus();
        switch (status){
            case "HETPHONG":
                hotelRoom.setNumbeRoomLast(Amount);
                hotelRoom.setStatus(HotelStatus.CONPHONG.getMessage());
                hotelRoomRepository.save(hotelRoom);
                String hotelId=hotelRoom.getHotel().getId().toString();
                System.out.println(hotelId);
                String message="Change"+hotelId;
                messagingTemplate.convertAndSend("/updateHotel/"+ hotelId, message);
                break;
            case "CONPHONG":
                hotelRoom.setNumbeRoomLast(Amount);;
                hotelRoomRepository.save(hotelRoom);
                String HotelIds=hotelRoom.getHotel().getId().toString();
                String messages="Change"+ HotelIds;
                messagingTemplate.convertAndSend("/updateHotel/"+  HotelIds, messages);
                break;
        }

    }

}
