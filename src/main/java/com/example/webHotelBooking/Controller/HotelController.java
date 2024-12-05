package com.example.webHotelBooking.Controller;

import com.example.webHotelBooking.DTO.Request.HotelRequest;
import com.example.webHotelBooking.DTO.Request.HotelSearch;
import com.example.webHotelBooking.DTO.Request.RoomRequest;
import com.example.webHotelBooking.DTO.Response.HotelResonse;
import com.example.webHotelBooking.Service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/hotels")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    // Get all hotels
    @GetMapping
    public ResponseEntity<List<HotelResonse>> getAllHotels() {
        List<HotelResonse> hotels = hotelService.GetAllHotel();
        return ResponseEntity.ok(hotels);
    }

    // Get hotel by ID
    @GetMapping("/{id}")
    public ResponseEntity<HotelResonse> getHotelById(@PathVariable Long id) {
        HotelResonse hotelResponse = hotelService.GetHotelById(id);
        return ResponseEntity.ok(hotelResponse);
    }
    @GetMapping("HotelBycity/{cityid}")
    public ResponseEntity<List<HotelResonse>> getHotelByCityId(@PathVariable Long cityid) {
        List<HotelResonse> hotelResonseList = hotelService.GetHotelByCity(cityid);
        return ResponseEntity.ok(hotelResonseList);
    }

    // Get suitable hotels by address and room request
    @PostMapping("/suit")
    public ResponseEntity<List<HotelResonse>> getSuitableHotels(@RequestParam String address, @RequestBody RoomRequest roomRequest) {
        List<HotelResonse> hotels = hotelService.GetHotelsuit(address, roomRequest);
        return ResponseEntity.ok(hotels);
    }

    // Get suitable hotels by condition
    @PostMapping("/conditional")
    public ResponseEntity<List<HotelResonse>> getSuitableHotelsCodition(
           @RequestParam(required = false) List<String> hotelPolicyList,@RequestParam String address,@RequestBody RoomRequest roomRequest,@RequestParam(required = false) Integer starpoint) {
        List<HotelResonse> hotels=new ArrayList<>();
        if(hotelPolicyList!=null){
            hotels = hotelService.GetHotelByCodition(hotelPolicyList, address, roomRequest, starpoint);
        }else {
            hotels=hotelService.GetHotelByCodition(new ArrayList<>(), address, roomRequest, starpoint);
        }
        return ResponseEntity.ok(hotels);
    }

    // Sort hotels by price
    @GetMapping("/sortByPrice")
    public ResponseEntity<List<HotelResonse>> sortByPrice() {
        List<HotelResonse> sortedHotels = hotelService.SortByPrice();
        return ResponseEntity.ok(sortedHotels);
    }

    // Create a new hotel
    @PostMapping
    public void createHotel(@RequestBody HotelRequest hotelRequest) {
        hotelService.createHotel(hotelRequest);
    }

    // Update an existing hotel
    @PutMapping("/{id}")
    public ResponseEntity<HotelResonse> updateHotel(@PathVariable Long id, @RequestBody HotelRequest hotelRequest) {
        HotelResonse updatedHotel = hotelService.Update(id, hotelRequest);
        return ResponseEntity.ok(updatedHotel);
    }

    // Delete hotel by ID
    @DeleteMapping("/{id}")
    public void deleteHotel(@PathVariable Long id) {
         hotelService.DeleteHotel(id);

    }

    // Delete hotel by name
    @DeleteMapping("/deleteByName/{name}")
    public void deleteHotelByName(@PathVariable String name) {
         hotelService.deleteHotelByName(name);

    }
}


