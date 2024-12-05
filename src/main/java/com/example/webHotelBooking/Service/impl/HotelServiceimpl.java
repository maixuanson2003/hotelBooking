package com.example.webHotelBooking.Service.impl;

import com.example.webHotelBooking.DTO.Request.HotelRequest;
import com.example.webHotelBooking.DTO.Request.HotelSearch;
import com.example.webHotelBooking.DTO.Request.RoomRequest;
import com.example.webHotelBooking.DTO.Response.HotelFacilityDTO;
import com.example.webHotelBooking.DTO.Response.HotelPolicyDetailsDTO;
import com.example.webHotelBooking.DTO.Response.HotelResonse;
import com.example.webHotelBooking.DTO.Response.HotelRoomDTO;
import com.example.webHotelBooking.Entity.*;
import com.example.webHotelBooking.Enums.HotelStatus;
import com.example.webHotelBooking.Exception.ResourceNotFoundException;
import com.example.webHotelBooking.Repository.*;
import com.example.webHotelBooking.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

class sortByPrice implements Comparator<Hotel>{
    public int compare(Hotel a, Hotel b) {
        // Make sure that the objects are Car objects

        return Integer.compare(b.getStarPoint(), a.getStarPoint());
    }
}
@Service
public class HotelServiceimpl implements HotelService {
    private static final Logger logger = LoggerFactory.getLogger(HotelServiceimpl.class);
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
    private HotelPolicyDetailService hotelPolicyDetailService;
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
    private void createHotelFacility(List<HotelFacilityDTO> hotelFacilityDTOList,Long HotelID){
        for (HotelFacilityDTO hotelFacilityDTO:hotelFacilityDTOList){
           hotelFacilityService.createHotelFacility(hotelFacilityDTO,HotelID);
        }
    }
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
    private void createHotelPolicy(List<HotelPolicyDetailsDTO> hotelPolicyDTOList,Long HotelId){
        for (HotelPolicyDetailsDTO hotelPolicyDTO:hotelPolicyDTOList){
           hotelPolicyDetailService.CreateHotelPolicyDetails(hotelPolicyDTO,HotelId);
        }
    }
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
        List<HotelPolicyDetails> hotelPolicyList=hotel.getHotelPolicyDetailsList();
        List<HotelPolicyDetailsDTO> hotelPolicyDTOList =new ArrayList<>();
        for (HotelPolicyDetails hotelPolicy:hotelPolicyList){
            HotelPolicyDetailsDTO hotelPolicyDetailsDTO =new HotelPolicyDetailsDTO(hotelPolicy);
            hotelPolicyDTOList.add(hotelPolicyDetailsDTO);
        }
        List<HotelImage> hotelImageList=hotel.getHotelImageList();
        List<String> imageList=new ArrayList<>();
        for (HotelImage hotelImage:hotelImageList){
            imageList.add(hotelImage.getLinkImage());
        }

        HotelResonse hotelResonse=new HotelResonse(hotel.getId(),hotel.getNameHotel(),hotel.getAddress(),hotel.getCity().getNameCity(),hotel.getStarPoint(), hotelFacilityDTOList, hotelPolicyDTOList, hotelRoomDTOList,imageList);
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
        System.out.println("numberPeople"+numberPeopleCheck);
        System.out.println("number"+numberPeopleCheck);
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
    public List<HotelResonse> GetHotelByCity(Long cityId) {
        City city=cityRepository.findById(cityId).orElseThrow(()->new RuntimeException("not found"));
        List<Hotel> hotelList=city.getHotelList();
        List<HotelResonse> hotelResonseList=new ArrayList<>();
        for (Hotel hotel:hotelList){
            hotelResonseList.add(this.createHotelResponse(hotel));

        }
        return hotelResonseList;
    }

    @Override
    public List<HotelResonse> GetHotelsuit(String address, RoomRequest roomRequest) {
        List<Hotel> hotelList=hotelRepository.findAllByCity(address);
        List<HotelResonse> hotelResonseList=new ArrayList<>();
        for (Hotel hotel:hotelList){
         logger.debug("s"+CheckRoomRequest(roomRequest,hotel));
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
        List<HotelPolicyDetails> hotelPolicyList1=hotel.getHotelPolicyDetailsList();
        List<String> hotelPolicyName=new ArrayList<>();
        for (HotelPolicyDetails hotelPolicyDetails :hotelPolicyList1){
            hotelPolicyName.add(hotelPolicyDetails.getHotelPolicy().getNamePolicy());
        }
        for (String hotelPolicy:hotelPolicyName){

            for (int i=0;i<Check.size();i++){
                Value++;
                if (hotelPolicy.equals(Check.get(Value))){
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
        if (starpoint == 0) {
            starpoint = -1;
        }

        boolean CheckCodition1 = hotelPolicyList.isEmpty() && starpoint == -1;
        boolean CheckCodition2 = (!hotelPolicyList.isEmpty()) && (starpoint == -1);
        boolean CheckCodition3 = !hotelPolicyList.isEmpty() && starpoint != -1;
        boolean CheckCodition4 = hotelPolicyList.isEmpty() && starpoint != -1;

        if(CheckCodition1){
            for (Hotel hotel:hotelList){
                if (CheckRoomRequest(roomRequest,hotel)){
                    System.out.println(CheckRoomRequest(roomRequest,hotel));
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
        if (CheckCodition4){
            for (Hotel hotel:hotelList){
                if (CheckRoomRequest(roomRequest,hotel)){
                    if (hotel.getStarPoint().equals(starpoint)){
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
        Optional<City> city1= Optional.ofNullable(cityRepository.findCityByNameCity(hotelRequest.getCity()));
        if (!city1.isPresent()){
            throw new ResourceNotFoundException("not found");
        }
        boolean checkExists = false;
        List<Hotel> hotelList = hotelRepository.findAll();
        for (Hotel hotel1 : hotelList) {
            if (hotel1.getCity().getNameCity().equals(hotelRequest.getName())) {
                checkExists = true;
            }
        }
        if (!checkExists) {
            hotel.setAddress(hotelRequest.getAddress());
            hotel.setNameHotel(hotelRequest.getName());
            hotel.setDescription(hotelRequest.getDesCription());
            hotel.setHotline(hotelRequest.getHotline());
            hotel.setEmail(hotelRequest.getEmail());
            hotel.setTotalRoom(hotelRequest.getTotalRoom());
            hotel.setStarPoint(hotelRequest.getStarpoint());
            hotel.setCity(city1.get());
            hotel.setBankaccountnumber(hotelRequest.getBankaccountnumber());
            hotel.setBankName(hotelRequest.getBankName());
            hotelRepository.save(hotel);
            Hotel hotel1=hotelRepository.findByNameHotel(hotelRequest.getName());
            createHotelPolicy(hotelRequest.getHotelPolicyDTOList(),hotel1.getId());
            createHotelFacility(hotelRequest.getHotelFacilityList(), hotel1.getId());
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
    @Transactional
    public void DeleteHotel(Long id) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));
        for (HotelFacility facility : hotel.getHotelFacilityList()) {
            facility.getHotel().remove(hotel);
        }
//        for (HotelPolicy Policy : hotel.getHotelPolicyList()) {
//            Policy.getHotelList().remove(hotel);
//        }
//        hotel.getHotelPolicyList().clear();
        hotel.getHotelFacilityList().clear();
        hotelRoomService.DeleteRoomHotelByHotel(hotel);
        hotel.getHotelRoomList().removeIf(c -> c.getHotel().getId()== id);
        hotel.getReviewList().removeIf(c->c.getHotel().getId()==id);
        hotel.getHotelImageList().clear();
        List<Hotel> hotelList=hotel.getCity().getHotelList();
        hotelList.remove(hotel);
        hotelRepository.deleteById(id);

    }

    @Override
    @Transactional
    public void deleteHotelByName(String name) {
        Hotel hotel=hotelRepository.findByNameHotel(name);
        if (hotel==null) throw new ResourceNotFoundException("not found");
        for (HotelFacility facility : hotel.getHotelFacilityList()) {
            facility.getHotel().remove(hotel);
        }
//        for (HotelPolicy Policy : hotel.getHotelPolicyList()) {
//            Policy.getHotelList().remove(hotel);
//        }
//        hotel.getHotelPolicyList().clear();
        hotel.getHotelFacilityList().clear();
        hotelRoomService.DeleteRoomHotelByHotel(hotel);
        hotel.getHotelRoomList().removeIf(c -> c.getHotel().getId()== hotel.getId());
        hotel.getReviewList().removeIf(c->c.getHotel().getId()==hotel.getId());
        hotel.getHotelImageList().clear();
        City city=hotel.getCity();
        List<Hotel> hotelList= city.getHotelList();
        hotelList.removeIf(c->c.getId()==hotel.getId());
        cityRepository.save(city);
        hotelRepository.save(hotel);
        hotelRepository.delete(hotel);
    }
}
