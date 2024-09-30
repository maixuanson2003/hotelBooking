package com.example.webHotelBooking.Service.impl;

import com.example.webHotelBooking.DTO.Request.RoomFeatureDTO;
import com.example.webHotelBooking.Entity.Hotel;
import com.example.webHotelBooking.Entity.HotelRoom;
import com.example.webHotelBooking.Entity.HotelRoomFeatures;
import com.example.webHotelBooking.Exception.DuplicateRecordException;
import com.example.webHotelBooking.Exception.ResourceNotFoundException;
import com.example.webHotelBooking.Repository.HotelRepository;
import com.example.webHotelBooking.Repository.HotelRoomFeaturesRepository;
import com.example.webHotelBooking.Repository.HotelRoomRepository;
import com.example.webHotelBooking.Service.RoomFeatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class RoomFeatureServiceImpl implements RoomFeatureService {
    @Autowired
    private HotelRoomRepository hotelRoomRepository;
    @Autowired
    private HotelRoomFeaturesRepository RoomFeaturRepo;
    @Autowired
    private HotelRepository hotelRepository;
    @Override
    public void addHotelRoomFeature(Long roomId, Long HotelId, RoomFeatureDTO roomFeatureDTO) {
        Optional<HotelRoom> hotelRoom = hotelRoomRepository.findById(roomId);
        Optional<Hotel> hotel=hotelRepository.findById(HotelId);
        Boolean CheckExsits=false;
        if (!hotelRoom.isPresent()){
            throw new ResourceNotFoundException("not found");
        }
        if (!hotel.isPresent()){
            throw  new ResourceNotFoundException("not found");
        }
       Optional<HotelRoomFeatures> hotelRoomFeatures= Optional.ofNullable(RoomFeaturRepo.findByNameFeatures(roomFeatureDTO.getNameFeatures()));
        if (hotelRoomFeatures.isPresent()){
            List<HotelRoom> hotelRoomList=hotelRoomFeatures.get().getHotelRoom();
            if (hotelRoomList.contains(hotelRoom)){
                throw new ResourceNotFoundException("Exsist");
            }else {
                hotelRoomList.add(hotelRoom.get());
                hotelRoomFeatures.get().setHotelRoom(hotelRoomList);
                RoomFeaturRepo.save(hotelRoomFeatures.get());
            }
        }else {
            List<HotelRoom> hotelRoomList=new ArrayList<>();
            hotelRoomList.add(hotelRoom.get());
            HotelRoomFeatures newFeature = new HotelRoomFeatures().builder()
                    .nameFeatures(roomFeatureDTO.getNameFeatures())
                    .description(roomFeatureDTO.getDescription())
                    .HotelRoom(hotelRoomList)
                    .build();
            RoomFeaturRepo.save(newFeature);
        }
    }

    @Override
    public void deleteHotelRoomFeatur(Long roomId, Long HotelId, String roomFeatureName) {
        HotelRoom hotelRoom = hotelRoomRepository.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel room not found"));
        Hotel hotel = hotelRepository.findById(HotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found"));
        Boolean CheckExsist=false;
        List<HotelRoomFeatures> hotelRoomFeaturesList=hotelRoom.getHotelRoomFeaturesList();
        for (HotelRoomFeatures hotelRoomFeatures:hotelRoomFeaturesList){
            if (hotelRoomFeatures.getNameFeatures().equals(roomFeatureName)){
                CheckExsist=true;
                break;
            }
        }
        if ( CheckExsist){
            HotelRoomFeatures hotelRoomFeatures=RoomFeaturRepo.findByNameFeatures(roomFeatureName);
            hotelRoomFeaturesList.remove(hotelRoomFeatures);
            List<HotelRoom> hotelRoomList=hotelRoomFeatures.getHotelRoom();
            hotelRoomList.remove(hotelRoom);
            hotelRoomFeatures.setHotelRoom(hotelRoomList);
            hotelRoom.setHotelRoomFeaturesList(hotelRoomFeaturesList);
            RoomFeaturRepo.save(hotelRoomFeatures);
            hotelRoomRepository.save(hotelRoom);
        }else {
            throw new ResourceNotFoundException("not found");
        }
    }

    @Override
    public List<RoomFeatureDTO> getAllRoomFeatureByHotel(Long HotelId,String roomNumber) {
        Hotel hotel = hotelRepository.findById(HotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found"));
        List<HotelRoom> hotelRoomList=hotel.getHotelRoomList();
        List<RoomFeatureDTO> roomFeatureDTOS=new ArrayList<>();
        for (HotelRoom hotelRoom:hotelRoomList){
            if (hotelRoom.getRoomNumber().equals(roomNumber)){
                List<HotelRoomFeatures> hotelRoomFeaturesList=hotelRoom.getHotelRoomFeaturesList();
                for (HotelRoomFeatures hotelRoomFeatures:hotelRoomFeaturesList){
                    RoomFeatureDTO roomFeatureDTO=new RoomFeatureDTO().builder()
                            .nameFeatures(hotelRoomFeatures.getNameFeatures())
                            .description(hotelRoomFeatures.getDescription())
                            .build();
                    roomFeatureDTOS.add(roomFeatureDTO);

                }
            }
        }
        return roomFeatureDTOS;
    }

    @Override
    public List<RoomFeatureDTO> getAllRoomFeature() {
        List<HotelRoomFeatures> hotelRoomFeaturesList=RoomFeaturRepo.findAll();
        List<RoomFeatureDTO> roomFeatureDTOS=new ArrayList<>();
        for (HotelRoomFeatures hotelRoomFeatures:hotelRoomFeaturesList){
            RoomFeatureDTO roomFeatureDTO=new RoomFeatureDTO().builder()
                    .nameFeatures(hotelRoomFeatures.getNameFeatures())
                    .description(hotelRoomFeatures.getDescription())
                    .build();
            roomFeatureDTOS.add(roomFeatureDTO);
        }
        return roomFeatureDTOS;
    }
}
