package com.example.webHotelBooking.Controller;

import com.example.webHotelBooking.Exception.DuplicateRecordException;
import com.example.webHotelBooking.Exception.ResourceNotFoundException;
import com.example.webHotelBooking.Service.HotelPolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/hotel-policies")
public class HotelPolicyController {

    @Autowired
    private HotelPolicyService hotelPolicyService;

    // Create a new hotel policy
    @PostMapping("/create")
    public ResponseEntity<String> createHotelPolicy(@RequestParam String nameHotelPolicy) {
        try {
            hotelPolicyService.createHotelPolicy(nameHotelPolicy);
            return ResponseEntity.ok("Hotel policy created successfully.");
        } catch (DuplicateRecordException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Update an existing hotel policy
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateHotelPolicy(@PathVariable Long id, @RequestParam String nameHotelPolicy) {
        try {
            hotelPolicyService.updateHotelPolicy(nameHotelPolicy, id);
            return ResponseEntity.ok("Hotel policy updated successfully.");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Delete a hotel policy by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePolicyById(@PathVariable Long id) {
        String result = hotelPolicyService.deletePolicyById(id);
        if ("Success".equals(result)) {
            return ResponseEntity.ok("Hotel policy deleted successfully.");
        } else {
            return ResponseEntity.badRequest().body("Failed to delete hotel policy.");
        }
    }
}
