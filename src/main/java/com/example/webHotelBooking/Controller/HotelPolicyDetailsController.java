package com.example.webHotelBooking.Controller;

import com.example.webHotelBooking.DTO.Response.HotelPolicyDetailsDTO;
import com.example.webHotelBooking.Service.HotelPolicyDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hotelPolicyDetails")
public class HotelPolicyDetailsController {

    @Autowired
    private HotelPolicyDetailService hotelPolicyDetailsService;

    // Tạo mới HotelPolicyDetails cho một khách sạn
    @PostMapping("/hotel/{hotelId}")
    public ResponseEntity<String> createHotelPolicyDetails(
            @PathVariable Long hotelId,
            @RequestBody HotelPolicyDetailsDTO hotelPolicyDetailsDTO) {
        hotelPolicyDetailsService.CreateHotelPolicyDetails(hotelPolicyDetailsDTO, hotelId);
        return new ResponseEntity<>("Hotel policy details created successfully", HttpStatus.CREATED);
    }

    // Lấy tất cả HotelPolicyDetails của hệ thống
    @GetMapping
    public ResponseEntity<List<HotelPolicyDetailsDTO>> getAllHotelPolicyDetails() {
        List<HotelPolicyDetailsDTO> policyDetailsList = hotelPolicyDetailsService.GetAllHotelPolicyDetils();
        return new ResponseEntity<>(policyDetailsList, HttpStatus.OK);
    }

    // Lấy tất cả HotelPolicyDetails của một khách sạn dựa trên hotelId
    @GetMapping("/hotel/{hotelId}")
    public ResponseEntity<List<HotelPolicyDetailsDTO>> getAllHotelPolicyByHotelId(@PathVariable Long hotelId) {
        List<HotelPolicyDetailsDTO> policyDetailsList = hotelPolicyDetailsService.GetAllHotelPolicyByHotelId(hotelId);
        return new ResponseEntity<>(policyDetailsList, HttpStatus.OK);
    }

    // Xoá một HotelPolicyDetails theo policyDetailsId
    @DeleteMapping("/{policyDetailsId}")
    public ResponseEntity<String> deleteHotelPolicyDetails(@PathVariable Long policyDetailsId) {
        hotelPolicyDetailsService.DeleteHotelPolicyDetails(policyDetailsId);
        return new ResponseEntity<>("Hotel policy details deleted successfully", HttpStatus.NO_CONTENT);
    }

    // Cập nhật HotelPolicyDetails của một khách sạn dựa trên hotelId và policyDetailsId
    @PutMapping("/hotel/{hotelId}/policy/{policyDetailsId}")
    public ResponseEntity<String> updateHotelPolicyDetails(
            @PathVariable Long hotelId,
            @PathVariable Long policyDetailsId,
            @RequestBody HotelPolicyDetailsDTO hotelPolicyDetailsDTO) {
        hotelPolicyDetailsService.UpdateHotelpolicyDetails(hotelId, policyDetailsId, hotelPolicyDetailsDTO);
        return new ResponseEntity<>("Hotel policy details updated successfully", HttpStatus.OK);
    }
}

