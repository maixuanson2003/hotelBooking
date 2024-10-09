package com.example.webHotelBooking.Controller;

import com.example.webHotelBooking.DTO.Response.HotelFacilityDTO;
import com.example.webHotelBooking.Service.HotelFacilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hotel-facilities")
public class HotelFacilityController {
    @Autowired
    private HotelFacilityService hotelFacilityService;

    // Tạo mới một khách sạn facility
    @PostMapping("/{hotelId}")
    public ResponseEntity<String> createHotelFacility(@PathVariable Long hotelId,
                                                      @RequestBody HotelFacilityDTO hotelFacilityDTO) {
        hotelFacilityService.createHotelFacility(hotelFacilityDTO, hotelId);
        return new ResponseEntity<>("Hotel facility created successfully", HttpStatus.CREATED);
    }

    // Cập nhật thông tin khách sạn facility
    @PutMapping("/{facilityId}")
    public ResponseEntity<String> updateHotelFacility(@PathVariable Long facilityId,
                                                      @RequestBody HotelFacilityDTO hotelFacilityDTO) {
        hotelFacilityService.updateHotelFacility(hotelFacilityDTO, facilityId);
        return new ResponseEntity<>("Hotel facility updated successfully", HttpStatus.OK);
    }

    // Xóa một khách sạn facility
    @DeleteMapping("/{hotelId}/{facilityId}")
    public ResponseEntity<String> deleteHotelFacility(@PathVariable Long hotelId,
                                                      @PathVariable Long facilityId) {
        String response = hotelFacilityService.deleteFacilityHotel(hotelId, facilityId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Lấy danh sách tất cả khách sạn facility theo id khách sạn
    @GetMapping("/{hotelId}")
    public ResponseEntity<List<HotelFacilityDTO>> getAllHotelFacilityByHotel(@PathVariable Long hotelId) {
        List<HotelFacilityDTO> facilities = hotelFacilityService.GetAllHotelFacilityByHotel(hotelId);
        return new ResponseEntity<>(facilities, HttpStatus.OK);
    }

    // Lấy danh sách tất cả khách sạn facility
    @GetMapping
    public ResponseEntity<List<HotelFacilityDTO>> getAllHotelFacility() {
        List<HotelFacilityDTO> facilities = hotelFacilityService.GetAllHotelFacility();
        return new ResponseEntity<>(facilities, HttpStatus.OK);
    }
}
