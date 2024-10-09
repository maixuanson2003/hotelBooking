package com.example.webHotelBooking.Service.impl;

import com.example.webHotelBooking.DTO.Request.HotelRequest;
import com.example.webHotelBooking.DTO.Request.HotelSearch;
import com.example.webHotelBooking.DTO.Request.RoomRequest;
import com.example.webHotelBooking.DTO.Response.HotelFacilityDTO;
import com.example.webHotelBooking.DTO.Response.HotelPolicyDTO;
import com.example.webHotelBooking.DTO.Response.HotelResonse;
import com.example.webHotelBooking.DTO.Response.HotelRoomDTO;
import com.example.webHotelBooking.Entity.*;
import com.example.webHotelBooking.Enums.HotelStatus;
import com.example.webHotelBooking.Repository.*;
import com.example.webHotelBooking.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

class sortByPrice implements Comparator{
    public int compare(Object obj1, Object obj2) {
        // Make sure that the objects are Car objects
        Hotel a = (Hotel) obj1;
        Hotel b = (Hotel) obj2;
        if (a.getStarPoint() > b.getStarPoint()) return 1;
        return 0;
    }
}
@Service
public class HotelServiceimpl implements HotelService {
    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private bookingRepository bookingRepository;
    @Autowired
    private HotelFacilityRepository hotelFacilityRepository;
    @Autowired
    private HotelPolicyRepository hotelPolicyRepository;
    @Autowired
    private  HotelRoomRepository hotelRoomRepository;
    @Autowired
    private  CityRepository cityRepository;
    @Autowired
    private HotelRoomService hotelRoomService;
    @Autowired
    private AccountHotelService accountHotelService;
    @Autowired
    private CityService cityService;
    @Autowired
    private HotelPolicyService hotelPolicyService;
    @Autowired
    private HotelImageService hotelImageService;
    @Autowired
    private HotelFacilityService hotelFacilityService;
    private Long findPriceMin(Hotel hotel){
        List<HotelRoom> hotelRoomList=hotel.getHotelRoomList();
        Long pricemin=hotelRoomList.get(0).getPricePerNight();
        for (HotelRoom hotelRoom:hotelRoomList){
            if (hotelRoom.getPricePerNight()<=pricemin){
                pricemin=hotelRoom.getPricePerNight();
            }
        }
        return pricemin;
    }
//    private void createHotelFacility(List<HotelFacilityDTO> hotelFacilityDTOList,Long HotelID){
//        for (HotelFacilityDTO hotelFacilityDTO:hotelFacilityDTOList){
//           hotelFacilityService.createHotelFacility(hotelFacilityDTO,HotelID);
//        }
//    }
    private void createHotelRoom(List<HotelRoomDTO> hotelRoomDTOList,Long HotelId){
        for (HotelRoomDTO hotelRoomDTO:hotelRoomDTOList){
           hotelRoomService.CreateRoomHotel(hotelRoomDTO,HotelId);
        }
    }
    private void createHotelImage(List<String> linkImage,Long HotelId){
        for (String image:linkImage){
            hotelImageService.createHotelImage(image,HotelId);
        }
    }
//    private void createHotelPolicy(List<HotelPolicyDTO> hotelPolicyDTOList,Long HotelId){
//        for (HotelPolicyDTO hotelPolicyDTO:hotelPolicyDTOList){
//           hotelPolicyService.createHotelPolicy(hotelPolicyDTO,HotelId);
//        }
//    }
    private HotelResonse createHotelResponse(Hotel hotel){
        List<HotelRoom> hotelRoomList=hotel.getHotelRoomList();
        List<HotelRoomDTO> hotelRoomDTOList =new ArrayList<>();
        for (HotelRoom hotelRoom:hotelRoomList){
            HotelRoomDTO hotelRoomDTO =new HotelRoomDTO( hotelRoom.getAmountRoom(),hotelRoom.getTypeRoom(),hotelRoom.getStatus(),hotelRoom.getPricePerNight(),hotelRoom.getNumberPeople(),hotelRoom.getImage());
            hotelRoomDTOList.add(hotelRoomDTO);
        }
        List<HotelFacility> hotelFacilityList=hotel.getHotelFacilityList();
        List<HotelFacilityDTO> hotelFacilityDTOList =new ArrayList<>();
        for (HotelFacility hotelFacility:hotelFacilityList){
            HotelFacilityDTO hotelFacilityDTO =new HotelFacilityDTO(hotelFacility.getNameHotelFacility(),hotelFacility.getDesCription());
            hotelFacilityDTOList.add(hotelFacilityDTO);
        }
        List<HotelPolicy> hotelPolicyList=hotel.getHotelPolicyList();
        List<HotelPolicyDTO> hotelPolicyDTOList =new ArrayList<>();
        for (HotelPolicy hotelPolicy:hotelPolicyList){
            HotelPolicyDTO hotelPolicyDTO =new HotelPolicyDTO(hotelPolicy.getNamePolicy(),hotelPolicy.getDescription(),hotelPolicy.getIsRelatedFee());
            hotelPolicyDTOList.add(hotelPolicyDTO);
        }
        List<HotelImage> hotelImageList=hotel.getHotelImageList();
        List<String> imageList=new ArrayList<>();
        for (HotelImage hotelImage:hotelImageList){
            imageList.add(hotelImage.getLinkImage());
        }

        HotelResonse hotelResonse=new HotelResonse(hotel.getId(),hotel.getNameHotel(),hotel.getAddress(),hotel.getCity().getNameCity(), hotelFacilityDTOList, hotelPolicyDTOList, hotelRoomDTOList,imageList);
         return hotelResonse;
    }

    private boolean CheckRoomRequest(RoomRequest roomRequest,Hotel hotel){
        Long numberRoomLast= 0L;
        Long numberPeople=0L;
        List<HotelRoom> hotelRoomList=hotel.getHotelRoomList();
        for (HotelRoom hotelRoom:hotelRoomList){
            if (hotelRoom.getStatus().equals(HotelStatus.CONPHONG.getMessage())){
                numberRoomLast+=hotelRoom.getNumbeRoomLast();
                numberPeople+=hotelRoom.getNumbeRoomLast()*hotelRoom.getNumberPeople();
            }
        }
        Long numberRoomCheck=numberRoomLast-roomRequest.getAmountRoom();
        Long numberPeopleCheck= numberPeople-roomRequest.getNumberPeople();
        if (numberRoomCheck>=0||numberPeopleCheck>=0){
            return true;
        }else{
            return false;
        }
    }
    @Override
    public List<HotelResonse> GetAllHotel() {
        List<Hotel> hotelList=hotelRepository.findAll();
        List<HotelResonse> hotelResonseList=new ArrayList<>();
        for (Hotel hotel:hotelList){
            hotelResonseList.add(this.createHotelResponse(hotel));
        }
        return hotelResonseList;
    }

    @Override
    public HotelResonse GetHotelById(Long id) {
        Hotel hotel=hotelRepository.findById(id).orElseThrow(()->new RuntimeException("not found"));
        return  this.createHotelResponse(hotel);
    }
    @Override
    public List<HotelResonse> GetHotelsuit(String address, RoomRequest roomRequest) {
        List<Hotel> hotelList=hotelRepository.findAllByCity(address);
        List<HotelResonse> hotelResonseList=new ArrayList<>();
        for (Hotel hotel:hotelList){
            if (CheckRoomRequest(roomRequest,hotel)){
                hotelResonseList.add(this.createHotelResponse(hotel));
            }
        }
        return hotelResonseList;
    }
    private boolean CheckCoditionResponse(List<String> hotelPolicyList,Hotel hotel){
        Integer Value=0;
        HashMap<Integer,String> Check=new HashMap<>();
        for (String h:hotelPolicyList){
            Value++;
            Check.put(Value,h);
        }
        Value=0;
        int result=0;
        List<HotelPolicy> hotelPolicyList1=hotel.getHotelPolicyList();
        for (HotelPolicy hotelPolicy:hotelPolicyList1){

            for (int i=0;i<Check.size();i++){
                Value++;
                if (hotelPolicy.getNamePolicy().equals(Check.get(Value))){
                    result++;
                }
            }
            Value=0;
        }
        if (result==hotelPolicyList.size()){
            return true;
        }else {
            return  false;
        }
    }

    @Override
    public List<HotelResonse> GetHotelByCodition(List<String> hotelPolicyList, String address, RoomRequest roomRequest,Integer starpoint) {
        List<Hotel> hotelList=hotelRepository.findAllByCity(address);
        List<HotelResonse> hotelResonseList=new ArrayList<>();
        boolean CheckCodition1= hotelPolicyList.isEmpty() &&starpoint==null;
        boolean CheckCodition2= (!hotelPolicyList.isEmpty()) && (starpoint == null);
        boolean CheckCodition3= !hotelPolicyList.isEmpty() &&starpoint!=null;
        if(CheckCodition1){
            for (Hotel hotel:hotelList){
                if (CheckRoomRequest(roomRequest,hotel)){
                    hotelResonseList.add(this.createHotelResponse(hotel));
                }
            }
        }
        if (CheckCodition2){
            for (Hotel hotel:hotelList){
                if (CheckRoomRequest(roomRequest,hotel)){
                    if (CheckCoditionResponse(hotelPolicyList,hotel)){
                        hotelResonseList.add(this.createHotelResponse(hotel));
                    }
                }
            }
        }
        if (CheckCodition3){
            for (Hotel hotel:hotelList){
                if (CheckRoomRequest(roomRequest,hotel)){
                    if (CheckCoditionResponse(hotelPolicyList,hotel)&&hotel.getStarPoint().equals(starpoint)){
                        hotelResonseList.add(this.createHotelResponse(hotel));
                    }
                }
            }
        }
        return hotelResonseList;
    }

    @Override
    public List<HotelResonse> SortByPrice() {
        List<Hotel> hotelList=hotelRepository.findAll();
        List<HotelResonse> hotelResonseList=new ArrayList<>();
        Comparator mycomparator=new sortByPrice();
        Collections.sort(hotelList, mycomparator);
        for (Hotel hotel:hotelList){
            hotelResonseList.add(this.createHotelResponse(hotel));
        }
        return hotelResonseList;
    }

    @Override
    public void createHotel(HotelRequest hotelRequest) {
        Hotel hotel = new Hotel();
        boolean checkExists = false;
        List<Hotel> hotelList = hotelRepository.findAll();
        for (Hotel hotel1 : hotelList) {
            if (hotel1.getCity().getNameCity().equals(hotelRequest.getName())) {
                checkExists = true;
            }
        }
        if (!checkExists) {
            int ResultForCancelation=0;
            int ResultForChange=0;
            hotel.setAddress(hotelRequest.getAddress());
            hotel.setNameHotel(hotelRequest.getName());
            hotel.setDescription(hotelRequest.getDesCription());
            hotel.setHotline(hotelRequest.getHotline());
            hotel.setEmail(hotelRequest.getEmail());
            List<HotelPolicyDTO> hotelPolicyDTOList=hotelRequest.getHotelPolicyDTOList();
            for (HotelPolicyDTO hotelPolicyDTO:hotelPolicyDTOList){
                if (hotelPolicyDTO.getIsRelatedFee()){
                        if (hotelPolicyDTO.getNamePolicy().equals("Hỗ trợ Đổi Lịch")){
                            ResultForChange++;
                        }
                        if (hotelPolicyDTO.getNamePolicy().equals("Hỗ trợ Hủy Lịch")){
                            ResultForCancelation++;
                        }
                }
            }
            if (ResultForCancelation == 1) {
                hotel.setCancelfee(hotelRequest.getCancelfee());
            } else {
                hotel.setCancelfee(0L);
            }

            // Cài đặt phí đổi nếu có chính sách đổi
            if (ResultForChange == 1) {
                hotel.setChangefee(hotelRequest.getChaneFee());
            } else {
                hotel.setChangefee(0L);
            }
            cityService.createCity(hotelRequest.getCity());
            hotel.setCity(cityRepository.findCityByNameCity(hotelRequest.getCity().getNameCity()));
            hotelRepository.save(hotel);
            Hotel hotel1=hotelRepository.findByNameHotel(hotelRequest.getName());
            System.out.println(hotel1);
//            createHotelPolicy(hotelRequest.getHotelPolicyDTOList(),hotel1.getId());
//            createHotelFacility(hotelRequest.getHotelFacilityList(), hotel1.getId());
            createHotelRoom(hotelRequest.getHotelRoomDTOList(),hotel1.getId());
            createHotelImage(hotelRequest.getImageList(), hotel1.getId());
            accountHotelService.CreatAccountByHotel(hotel1.getId());

        }
    }

    @Override
    public HotelResonse Update(Long HotelId,HotelRequest hotelRequest) {
        Hotel hotel=hotelRepository.findById(HotelId).orElseThrow(()->new RuntimeException("not found"));
        hotel.setNameHotel(hotelRequest.getName());
        hotel.setAddress(hotelRequest.getAddress());
        hotel.setAddress(hotelRequest.getAddress());
        hotelRepository.save(hotel);
        return this.createHotelResponse(hotel);
    }

    @Override
    public String DeleteHotel(Long id) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));

        // Xóa khách sạn khỏi các tiện ích
        for (HotelFacility facility : hotel.getHotelFacilityList()) {
            facility.getHotel().remove(hotel);
        }
        for (HotelPolicy Policy : hotel.getHotelPolicyList()) {
            Policy.getHotelList().remove(hotel);
        }
        hotel.getHotelPolicyList().clear();
        hotel.getHotelFacilityList().clear();
        hotelRepository.save(hotel);
        hotelRepository.deleteById(id);
        return "";
    }

    @Override
    public String deleteHotelByName(String name) {
        //delete
        return "";
    }
}
