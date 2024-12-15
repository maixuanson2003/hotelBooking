package com.example.webHotelBooking.Controller;

import com.example.webHotelBooking.DTO.Response.HotelFacilityDetailsDTO;
import com.example.webHotelBooking.Exception.ResourceNotFoundException;
import com.example.webHotelBooking.Service.HotelFacilityDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/hotel-facility-details")
public class HotelFacilityDetailController {

    @Autowired
    private HotelFacilityDetailService hotelFacilityDetailService;

    // API để tạo mới một Hotel Facility Details
    @PostMapping("/{hotelId}")
    public ResponseEntity<String> createHotelFacilityDetails(
            @PathVariable Long hotelId,
            @RequestBody HotelFacilityDetailsDTO hotelFacilityDetailsDTO) {
        try {
            hotelFacilityDetailService.CreateHotelFacilityDetails(hotelFacilityDetailsDTO, hotelId);
            return ResponseEntity.status(HttpStatus.CREATED).body("Hotel Facility Details created successfully");
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }

    // API để lấy tất cả Hotel Facility Details
    @GetMapping
    public ResponseEntity<List<HotelFacilityDetailsDTO>> getAllHotelFacilityDetails() {
        List<HotelFacilityDetailsDTO> facilityDetails = hotelFacilityDetailService.GetAllHotelFacilityDetils();
        return ResponseEntity.ok(facilityDetails);
    }

    // API để lấy tất cả Hotel Facility Details theo HotelId
    @GetMapping("/{hotelId}")
    public ResponseEntity<List<HotelFacilityDetailsDTO>> getAllHotelFacilityDetailsByHotelId(@PathVariable Long hotelId) {
        try {
            List<HotelFacilityDetailsDTO> facilityDetails = hotelFacilityDetailService.GetAllHotelFacilityDetilsByHotelId(hotelId);
            return ResponseEntity.ok(facilityDetails);
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // API để cập nhật một Hotel Facility Details
    @PutMapping("/{hotelId}/{facilityDetailsId}")
    public ResponseEntity<String> updateHotelFacilityDetails(
            @PathVariable Long hotelId,
            @PathVariable Long facilityDetailsId,
            @RequestBody HotelFacilityDetailsDTO hotelFacilityDetailsDTO) {
        try {
            hotelFacilityDetailService.UpdateHotelFacilityDetails(hotelId, facilityDetailsId, hotelFacilityDetailsDTO);
            return ResponseEntity.ok("Hotel Facility Details updated successfully");
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    // API để xóa một Hotel Facility Details theo ID
    @DeleteMapping("/{facilityDetailsId}")
    public ResponseEntity<String> deleteHotelFacilityDetails(@PathVariable Long facilityDetailsId) {
        try {
            hotelFacilityDetailService.DeleteHotelFacilityDetails(facilityDetailsId);
            return ResponseEntity.ok("Hotel Facility Details deleted successfully");
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }
}
