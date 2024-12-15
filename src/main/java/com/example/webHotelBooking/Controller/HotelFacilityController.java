package com.example.webHotelBooking.Controller;

import com.example.webHotelBooking.DTO.Response.HotelFacilityDTO;
import com.example.webHotelBooking.Exception.DuplicateRecordException;
import com.example.webHotelBooking.Exception.ResourceNotFoundException;
import com.example.webHotelBooking.Service.HotelFacilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/hotel-facilities")
public class HotelFacilityController {
    @Autowired
    private HotelFacilityService hotelFacilityService;

    // API để tạo mới một HotelFacility
    @PostMapping
    public ResponseEntity<String> createHotelFacility(@RequestParam String request) {
        String nameHotelFacility = request;
        if (nameHotelFacility == null || nameHotelFacility.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Name of Hotel Facility is required");
        }
        try {
            hotelFacilityService.createHotelFacility(nameHotelFacility);
            return ResponseEntity.ok("Hotel Facility created successfully");
        } catch (DuplicateRecordException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }
    }
    @GetMapping("/all")
    public  ResponseEntity<List<HotelFacilityDTO>> GetAllFacility(){
        List<HotelFacilityDTO> hotelFacilityDTOList=hotelFacilityService.GetAllFacility();
        return ResponseEntity.ok(hotelFacilityDTOList);
    }

    // API để cập nhật một HotelFacility
    @PutMapping("/{hotelFacilityId}")
    public ResponseEntity<String> updateHotelFacility(
            @PathVariable Long hotelFacilityId,
            @RequestParam String request) {
        String nameHotelPolicy = request;
        if (nameHotelPolicy == null || nameHotelPolicy.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Name of Hotel Facility is required");
        }
        try {
            hotelFacilityService.updateHotelFacility(nameHotelPolicy, hotelFacilityId);
            return ResponseEntity.ok("Hotel Facility updated successfully");
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    // API để xóa một HotelFacility theo ID
    @DeleteMapping("/{hotelFacilityId}")
    public ResponseEntity<String> deleteHotelFacility(@PathVariable Long hotelFacilityId) {
        String result = hotelFacilityService.deletePolicyById(hotelFacilityId);
        if ("Success".equals(result)) {
            return ResponseEntity.ok("Hotel Facility deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete Hotel Facility");
        }
    }
}
