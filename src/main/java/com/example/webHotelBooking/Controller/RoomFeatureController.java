package com.example.webHotelBooking.Controller;

import com.example.webHotelBooking.DTO.Request.RoomFeatureDTO;
import com.example.webHotelBooking.Service.RoomFeatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/room-features")
public class RoomFeatureController {

    @Autowired
    private RoomFeatureService roomFeatureService;

    // Thêm tính năng phòng
    @PostMapping("/add")
    public ResponseEntity<String> addHotelRoomFeature(@RequestParam Long roomId,
                                                      @RequestParam Long hotelId,
                                                      @RequestBody RoomFeatureDTO roomFeatureDTO) {
        roomFeatureService.addHotelRoomFeature(roomId, hotelId, roomFeatureDTO);
        return ResponseEntity.ok("Room feature added successfully.");
    }
    @PostMapping("/add/admin")
    public ResponseEntity<String> addHotelRoomFeaturAll(@RequestBody RoomFeatureDTO roomFeatureDTO) {
        roomFeatureService.addHotelRoomFeatureAll(roomFeatureDTO);
        return ResponseEntity.ok("Room feature added successfully.");
    }

    // Xóa tính năng phòng
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteHotelRoomFeature(@RequestParam Long roomId,
                                                         @RequestParam Long hotelId,
                                                         @RequestParam String roomFeatureName) {
        roomFeatureService.deleteHotelRoomFeatur(roomId, hotelId, roomFeatureName);
        return ResponseEntity.ok("Room feature deleted successfully.");
    }

    // Lấy tất cả các tính năng phòng của một phòng cụ thể trong một khách sạn
    @GetMapping("/hotel/room")
    public ResponseEntity<List<RoomFeatureDTO>> getAllRoomFeaturesByHotel(@RequestParam Long hotelId,
                                                                          @RequestParam String roomNumber) {
        List<RoomFeatureDTO> roomFeatures = roomFeatureService.getAllRoomFeatureByHotel(hotelId, roomNumber);
        return ResponseEntity.ok(roomFeatures);
    }

    // Lấy tất cả các tính năng phòng
    @GetMapping("/all")
    public ResponseEntity<List<RoomFeatureDTO>> getAllRoomFeatures() {
        List<RoomFeatureDTO> roomFeatures = roomFeatureService.getAllRoomFeature();
        return ResponseEntity.ok(roomFeatures);
    }
}
