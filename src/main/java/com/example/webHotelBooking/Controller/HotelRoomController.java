package com.example.webHotelBooking.Controller;
import com.example.webHotelBooking.DTO.Response.HotelRoomDTO;
import com.example.webHotelBooking.Service.HotelRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/hotels/rooms")
public class HotelRoomController {

    @Autowired
    private HotelRoomService hotelRoomService;

    // 1. Tạo một phòng khách sạn mới
    @PostMapping("/create")
    public ResponseEntity<String> createRoomHotel(@RequestParam("hotelId") Long hotelId,
                                                  @RequestBody HotelRoomDTO hotelRoomDTO) {
        hotelRoomService.CreateRoomHotel(hotelRoomDTO, hotelId);
        return ResponseEntity.ok("Hotel room created successfully.");
    }

    // 2. Cập nhật phòng khách sạn
    @PutMapping("/update/{roomId}")
    public ResponseEntity<String> updateRoomHotel(@PathVariable("hotelId") Long hotelId,
                                                  @PathVariable("roomId") Long roomId,
                                                  @RequestBody HotelRoomDTO hotelRoomDTO) {
        hotelRoomService.UpdateRoomHotel(hotelRoomDTO, hotelId, roomId);
        return ResponseEntity.ok("Hotel room updated successfully.");
    }

    // 3. Xóa phòng khách sạn bằng số phòng
    @DeleteMapping("/delete/roomNumber")
    public ResponseEntity<String> deleteRoomHotel(@RequestParam("hotelId") Long hotelId,
                                                  @RequestParam("roomNumber") String roomNumber) {
        hotelRoomService.DeleteRoomHotelByRoomType(hotelId, roomNumber);
        return ResponseEntity.ok("Hotel room deleted successfully.");
    }

    // 4. Lấy danh sách tất cả các phòng của khách sạn theo hotelId
    @GetMapping("/all/{hotelId}")
    public ResponseEntity<List<HotelRoomDTO>> getAllHotelRooms(@PathVariable("hotelId") Long hotelId) {
        List<HotelRoomDTO> hotelRoomDTOList = hotelRoomService.GetAllHotelRoomByHotelId(hotelId);
        return ResponseEntity.ok(hotelRoomDTOList);
    }

    // 5. Tìm kiếm phòng theo điều kiện
    @GetMapping("/search")
    public ResponseEntity<List<HotelRoomDTO>> searchRooms(@PathVariable("hotelId") Long hotelId,
                                                          @RequestParam(required = false) List<String> features,
                                                          @RequestParam(required = false) Long priceStart,
                                                          @RequestParam(required = false) Long priceEnd,
                                                          @RequestParam(required = false) String roomType) {
        if (features == null) {
            features = new ArrayList<>();
        }
        List<HotelRoomDTO> hotelRoomDTOList = hotelRoomService.searChRoomByCodition(features, priceStart, priceEnd, roomType, hotelId);
        return ResponseEntity.ok(hotelRoomDTOList);
    }
    @GetMapping("/Hotel")
    public ResponseEntity<List<HotelRoomDTO>> getHotelRoomByHotel(@RequestHeader String token){
        String tokens = token.replace("Bearer ", "");
        List<HotelRoomDTO> hotelRoomDTOList=hotelRoomService.GetRoomByHotel(tokens);
        return ResponseEntity.ok(hotelRoomDTOList);
    }
    @PutMapping("/setStatus")
    public void setStatusRoom(@RequestParam String roomNumber,@RequestParam Long HotelId,@RequestParam int amount){
        hotelRoomService.setAmountRoom(roomNumber,HotelId,amount);
    }

}

