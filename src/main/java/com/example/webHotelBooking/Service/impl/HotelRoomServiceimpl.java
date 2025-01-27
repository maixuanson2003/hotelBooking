package com.example.webHotelBooking.Service.impl;

import com.example.webHotelBooking.DTO.Request.HotelRoomRequest;
import com.example.webHotelBooking.DTO.Request.RoomFeatureDTO;
import com.example.webHotelBooking.DTO.Response.HotelRoomDTO;
import com.example.webHotelBooking.Entity.*;
import com.example.webHotelBooking.Enums.HotelStatus;
import com.example.webHotelBooking.Exception.ResourceNotFoundException;
import com.example.webHotelBooking.Repository.HotelRepository;
import com.example.webHotelBooking.Repository.HotelRoomFeaturesRepository;
import com.example.webHotelBooking.Repository.HotelRoomRepository;
import com.example.webHotelBooking.Repository.bookingdetailsRepository;
import com.example.webHotelBooking.Service.HotelRoomService;
import com.example.webHotelBooking.Service.RoomFeatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
    private bookingdetailsRepository bookingdetailsRepository;
    @Autowired
    private HotelRoomFeaturesRepository RoomFeaturRepo;
    @Autowired
    private RoomFeatureService roomFeatureService;
    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private JwtDecoder jwtDecoder;


    @Override
    public void CreateRoomHotel(HotelRoomDTO hotelRoomDTO, Long Hotelid) {
        Hotel hotel = hotelRepository.findById(Hotelid).orElseThrow(() -> new RuntimeException("not found"));
        HotelRoom hotelRoom = new HotelRoom().builder()
                .AmountRoom(hotelRoomDTO.getAmount())
                .numbeRoomLast(hotelRoomDTO.getAmount())
                .typeRoom(hotelRoomDTO.getTypeRoom())
                .hotel(hotel)
                .pricePerNight(hotelRoomDTO.getPricePerNight())
                .numberPeople(hotelRoomDTO.getNumberPeople())
                .numberOfBooking(0)
                .image(hotelRoomDTO.getImage())
                .status(HotelStatus.CONPHONG.getMessage())
                .build();
        hotelRoomRepository.save(hotelRoom);
    }
    private void MappingHotelRoomFeature( RoomFeatureDTO roomFeatureDTO,Long HotelRoomId){
        HotelRoom hotelRoo=hotelRoomRepository.findById(HotelRoomId).orElseThrow(()->new RuntimeException("not found"));
       HotelRoomFeatures hotelRoomFeatures=RoomFeaturRepo.findByNameFeatures(roomFeatureDTO.getNameFeatures());
       List<HotelRoomFeatures> hotelRoomFeaturesList=hotelRoo.getHotelRoomFeaturesList();
       if(hotelRoomFeaturesList==null){
           hotelRoomFeaturesList=new ArrayList<>();
       }
       List<HotelRoom> hotelRoomList=hotelRoomFeatures.getHotelRoom();
       hotelRoomFeaturesList.add( hotelRoomFeatures);
       hotelRoomList.add(hotelRoo);
       RoomFeaturRepo.save(hotelRoomFeatures);
       hotelRoomRepository.save(hotelRoo);
    }
    private void MappingHotelRoomFeatureUpdate(String request,Long HotelRoomId){
        HotelRoom hotelRoo=hotelRoomRepository.findById(HotelRoomId).orElseThrow(()->new RuntimeException("not found"));
        HotelRoomFeatures hotelRoomFeatures=RoomFeaturRepo.findByNameFeatures(request);
        List<HotelRoomFeatures> hotelRoomFeaturesList=hotelRoo.getHotelRoomFeaturesList();
        if(hotelRoomFeaturesList==null){
            hotelRoomFeaturesList=new ArrayList<>();
        }
        List<HotelRoom> hotelRoomList=hotelRoomFeatures.getHotelRoom();
        hotelRoomFeaturesList.add( hotelRoomFeatures);
        hotelRoomList.add(hotelRoo);
        RoomFeaturRepo.save(hotelRoomFeatures);
        hotelRoomRepository.save(hotelRoo);
    }

    @Override
    public void CreateRoomHotelForHotel(HotelRoomRequest request, Long HotelID) {
        Hotel hotel = hotelRepository.findById(HotelID).orElseThrow(() -> new RuntimeException("not found"));
        HotelRoom hotelRoom = new HotelRoom().builder()
                .AmountRoom(request.getAmount())
                .numbeRoomLast(request.getAmount())
                .typeRoom(request.getTypeRoom())
                .hotel(hotel)
                .pricePerNight(request.getPricePerNight())
                .numberPeople(request.getNumberPeople())
                .numberOfBooking(0)
                .image(request.getImage())
                .status(HotelStatus.CONPHONG.getMessage())
                .build();
       HotelRoom hotelRoom1= hotelRoomRepository.save(hotelRoom);
       List<RoomFeatureDTO> roomFeatureDTOList=request.getRoomFeatureDTOS();
       for (RoomFeatureDTO c:roomFeatureDTOList){
           MappingHotelRoomFeature(c,hotelRoom1.getId());
       }
    }

    @Override
    @Transactional
    public void UpdateRoomHotel(HotelRoomRequest request, Long Hotelid, Long roomId) {
        Hotel hotel = hotelRepository.findById(Hotelid)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));

        List<HotelRoom> hotelRoomList = hotel.getHotelRoomList();
        boolean roomFound = false; // Biến kiểm tra xem phòng có tồn tại không

        for (HotelRoom hotelRoom : hotelRoomList) {
            if (hotelRoom.getId().equals(roomId)) {
                roomFound = true;

                // Cập nhật thông tin phòng
                hotelRoom.setAmountRoom(request.getAmount());
                hotelRoom.setNumbeRoomLast(request.getAmount());
                hotelRoom.setImage(request.getImage());
                hotelRoom.setStatus(request.getStatus());
                hotelRoom.setTypeRoom(request.getTypeRoom());
                List<RoomFeatureDTO> roomFeatureDTOS = request.getRoomFeatureDTOS();
                List<HotelRoomFeatures> hotelRoomFeaturesList = hotelRoom.getHotelRoomFeaturesList();
                if (hotelRoomFeaturesList != null) {
                    List<String> featureNamesToDelete = new ArrayList<>();
                    for (HotelRoomFeatures roomFeature : hotelRoomFeaturesList) {
                        featureNamesToDelete.add(roomFeature.getNameFeatures());
                    }
                    for (String featureName : featureNamesToDelete) {
                        roomFeatureService.deleteHotelRoomFeatur(hotelRoom.getId(), Hotelid, featureName);
                    }
                }
                if (roomFeatureDTOS != null && !roomFeatureDTOS.isEmpty()) {
                    System.out.println("Processing new features...");
                    for (RoomFeatureDTO feature : roomFeatureDTOS) {
                        MappingHotelRoomFeatureUpdate(feature.getNameFeatures(), hotelRoom.getId());
                    }
                }

                // Lưu lại phòng đã cập nhật
                hotelRoomRepository.save(hotelRoom);
                break;
            }
        }

        if (!roomFound) {
            throw new RuntimeException("Room not found");
        }
    }



    @Override
    public void DeleteRoomHotelByRoomType(Long HotelId, String roomType) {
        Hotel hotel = hotelRepository.findById(HotelId).orElseThrow(() -> new RuntimeException("not found"));
        List<HotelRoom> hotelRoomList = hotel.getHotelRoomList();
        HotelRoom hotelRooms = null;
        for (HotelRoom hotelRoom : hotelRoomList) {
            if (hotelRoom.getTypeRoom().equals(roomType)) {
                hotelRooms = hotelRoom;
            }
        }
        List<HotelRoomFeatures> hotelRoomFeaturesList = hotelRooms.getHotelRoomFeaturesList();
        for (HotelRoomFeatures hotelRoomFeatures :hotelRoomFeaturesList){
            List<HotelRoom> hotelRoomList1=hotelRoomFeatures.getHotelRoom();
            hotelRoomList1.remove(hotelRooms);

        }
        hotelRoomFeaturesList.clear();
        HotelRoom hotelRoomSave = hotelRoomRepository.save(hotelRooms);
        hotelRoomList.remove(hotelRooms);
        System.out.println(hotelRoomSave.getId());
        hotelRoomRepository.deleteById(hotelRoomSave.getId());
        hotelRepository.save(hotel);
    }

    @Override
    @Transactional
    public void DeleteRoomHotelByHotel(Hotel hotel) {
        List<HotelRoom> hotelRoomList = hotel.getHotelRoomList();
        for (HotelRoom hotelRoom : hotelRoomList) {
            List<bookingdetails> bookingdetails = hotelRoom.getBookingdetails();
            bookingdetails.removeIf(c->c.getHotelRoom().getId()==hotelRoom.getId());
            List<HotelRoomFeatures> hotelRoomFeaturesList = hotelRoom.getHotelRoomFeaturesList();
            hotelRoomFeaturesList.removeIf(c->c.getHotelRoom().equals(hotelRoom));
            hotelRoomRepository.save(hotelRoom);
        }

    }
    @Override
    public List<HotelRoomDTO> GetAllHotelRoomByHotelId(Long HotelId) {
        Hotel hotel = hotelRepository.findById(HotelId).orElseThrow(() -> new RuntimeException("not found"));
        List<HotelRoom> hotelRoomList = hotel.getHotelRoomList();
        List<HotelRoomDTO> hotelRoomDTOList = new ArrayList<>();
        for (HotelRoom hotelRoom : hotelRoomList) {
            HotelRoomDTO hotelRoomDTO=new HotelRoomDTO(hotelRoom);
            hotelRoomDTOList.add(hotelRoomDTO);
        }
        return hotelRoomDTOList;
    }

    private boolean CheckCoditionResponse(List<String> NameRoomFeature, HotelRoom hotelRoom) {
        Integer Value = 0;
        HashMap<Integer, String> Check = new HashMap<>();
        for (String h : NameRoomFeature) {
            Value++;
            Check.put(Value, h);
        }
        Value = 0;
        int result = 0;
        List<HotelRoomFeatures> hotelFeatureList1 = hotelRoom.getHotelRoomFeaturesList();
        for (HotelRoomFeatures hotelFeature : hotelFeatureList1) {

            for (int i = 0; i < Check.size(); i++) {
                Value++;
                if (hotelFeature.getNameFeatures().equals(Check.get(Value))) {
                    result++;
                }
            }
            Value = 0;
        }
        if (result == NameRoomFeature.size()) {
            return true;
        } else {
            return false;
        }
    }

    private Boolean CheckResponseByPrice(Long priceStart, Long priceEnd, HotelRoom hotelRoom) {
        if (hotelRoom.getPricePerNight() <= priceEnd && hotelRoom.getPricePerNight() >= priceStart) {
            return true;
        } else {
            return false;
        }
    }


    @Override
    public List<HotelRoomDTO> searChRoomByCodition(List<String> NameRoomFeature, Long priceStart, Long PriceEnd, Long Hotelid) {
        boolean CheckCodition1 = !NameRoomFeature.isEmpty() && priceStart == null && PriceEnd == null ;
        boolean CheckCodition2 = !NameRoomFeature.isEmpty() && priceStart != null && PriceEnd != null ;
        boolean CheckCodition3 = !NameRoomFeature.isEmpty() && priceStart != null && PriceEnd != null;
        boolean CheckCodition4 = NameRoomFeature.isEmpty() && priceStart != null && PriceEnd != null;

        Hotel hotel = hotelRepository.findById(Hotelid).orElseThrow(() -> new ResourceNotFoundException("not found"));
        List<HotelRoom> hotelRoomList = hotel.getHotelRoomList();
        List<HotelRoomDTO> hotelRoomDTOList = new ArrayList<>();
        if (CheckCodition1) {
            for (HotelRoom hotelRoom : hotelRoomList) {
                if (this.CheckCoditionResponse(NameRoomFeature, hotelRoom)) {
                    HotelRoomDTO hotelRoomDTO=new HotelRoomDTO(hotelRoom);
                    hotelRoomDTOList.add(hotelRoomDTO);
                }
            }
        }
        if (CheckCodition2) {
            for (HotelRoom hotelRoom : hotelRoomList) {
                if (this.CheckCoditionResponse(NameRoomFeature, hotelRoom) && this.CheckResponseByPrice(priceStart, PriceEnd, hotelRoom)) {
                    HotelRoomDTO hotelRoomDTO=new HotelRoomDTO(hotelRoom);
                    hotelRoomDTOList.add(hotelRoomDTO);
                }
            }
        }
        if (CheckCodition3) {
            for (HotelRoom hotelRoom : hotelRoomList) {
                if (this.CheckCoditionResponse(NameRoomFeature, hotelRoom) && this.CheckResponseByPrice(priceStart, PriceEnd, hotelRoom)) {
                    HotelRoomDTO hotelRoomDTO=new HotelRoomDTO(hotelRoom);
                    hotelRoomDTOList.add(hotelRoomDTO);
                }
            }
        }
        if (CheckCodition4) {
            for (HotelRoom hotelRoom : hotelRoomList) {
                if (this.CheckResponseByPrice(priceStart, PriceEnd, hotelRoom) ) {
                    HotelRoomDTO hotelRoomDTO=new HotelRoomDTO(hotelRoom);
                    hotelRoomDTOList.add(hotelRoomDTO);
                }
            }
        }

        return hotelRoomDTOList;
    }

    @Override
    public List<HotelRoomDTO> GetRoomByHotel(String token) {
        Jwt jwt = jwtDecoder.decode(token);
        Long HotelId = jwt.getClaim("HotelId");
        Hotel hotel = hotelRepository.findById(HotelId).orElseThrow(() -> new ResourceNotFoundException("not found"));
        List<HotelRoom> hotelRoomList = hotel.getHotelRoomList();
        List<HotelRoomDTO> hotelRoomDTOList = new ArrayList<>();
        for (HotelRoom hotelRoom : hotelRoomList) {
            HotelRoomDTO hotelRoomDTO=new HotelRoomDTO(hotelRoom);
            hotelRoomDTOList.add(hotelRoomDTO);
        }
        return hotelRoomDTOList;
    }

    private HotelRoom findHotelRoomByRoomType(String roomType, Long Hotelid) {
        Hotel hotel = hotelRepository.findById(Hotelid).orElseThrow(() -> new ResourceNotFoundException("not found"));
        List<HotelRoom> hotelRoomList = hotel.getHotelRoomList();
        for (HotelRoom hotelRoom : hotelRoomList) {
            if (hotelRoom.getTypeRoom().equals(roomType)) {
                return hotelRoom;
            }
        }
        return null;
    }

    @Override
    public void setAmountRoom(String TypeRoom, Long Hotelid, int Amount) {
        HotelRoom hotelRoom = findHotelRoomByRoomType(TypeRoom, Hotelid);
        String status = hotelRoom.getStatus();
        switch (status) {
            case "HETPHONG":
                hotelRoom.setNumbeRoomLast(Amount);
                hotelRoom.setStatus(HotelStatus.CONPHONG.getMessage());
                hotelRoomRepository.save(hotelRoom);
                String hotelId = hotelRoom.getHotel().getId().toString();
                System.out.println(hotelId);
                String message = "Change" + hotelId;
                messagingTemplate.convertAndSend("/updateHotel/" + hotelId, message);
                break;
            case "CONPHONG":
                hotelRoom.setNumbeRoomLast(Amount);
                hotelRoomRepository.save(hotelRoom);
                String HotelIds = hotelRoom.getHotel().getId().toString();
                String messages = "Change" + HotelIds;
                messagingTemplate.convertAndSend("/updateHotel/" + HotelIds, messages);
                break;
        }

    }

}
