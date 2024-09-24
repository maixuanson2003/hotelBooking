package com.example.webHotelBooking.Controller;

import com.example.webHotelBooking.DTO.Response.HotelPolicyDTO;
import com.example.webHotelBooking.Service.HotelPolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hotel-policies")
public class HotelPolicyController {

    @Autowired
    private HotelPolicyService hotelPolicyService;

    // Tạo chính sách khách sạn
    @PostMapping("/{hotelId}")
    public ResponseEntity<String> createHotelPolicy(
            @PathVariable Long hotelId,
            @RequestBody HotelPolicyDTO hotelPolicyDTO) {
        hotelPolicyService.createHotelPolicy(hotelPolicyDTO, hotelId);
        return ResponseEntity.status(HttpStatus.CREATED).body("Hotel policy created successfully.");
    }

    // Cập nhật chính sách khách sạn
    @PutMapping("/{hotelPolicyId}")
    public ResponseEntity<String> updateHotelPolicy(
            @PathVariable Long hotelPolicyId,
            @RequestBody HotelPolicyDTO hotelPolicyDTO) {
        hotelPolicyService.updateHotelPolicy(hotelPolicyDTO, hotelPolicyId);
        return ResponseEntity.ok("Hotel policy updated successfully.");
    }

    // Xóa chính sách khách sạn
    @DeleteMapping("/{hotelPolicyId}/hotel/{hotelId}")
    public ResponseEntity<String> deletePolicyById(
            @PathVariable Long hotelPolicyId,
            @PathVariable Long hotelId) {
        String response = hotelPolicyService.deletePolicyById(hotelId, hotelPolicyId);
        return ResponseEntity.ok(response);
    }

    // Lấy chính sách của khách sạn theo ID khách sạn
    @GetMapping("/hotel/{hotelId}")
    public ResponseEntity<List<HotelPolicyDTO>> getHotelPolicyByHotel(@PathVariable Long hotelId) {
        List<HotelPolicyDTO> policies = hotelPolicyService.getHotelPolicyByHotel(hotelId);
        return ResponseEntity.ok(policies);
    }

    // Lấy tất cả chính sách khách sạn
    @GetMapping
    public ResponseEntity<List<HotelPolicyDTO>> getAllHotelPolicy() {
        List<HotelPolicyDTO> policies = hotelPolicyService.getAllHotelPolicy();
        return ResponseEntity.ok(policies);
    }
}
