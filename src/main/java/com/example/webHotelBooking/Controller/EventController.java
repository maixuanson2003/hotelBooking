package com.example.webHotelBooking.Controller;

import com.example.webHotelBooking.DTO.Request.EventDTO;
import com.example.webHotelBooking.Service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {

    @Autowired
    private EventService eventService;

    // Thêm sự kiện vào một thành phố
    @PostMapping("/city/add/{cityId}")
    public ResponseEntity<String> addEventToCity(@PathVariable Long cityId, @RequestBody EventDTO eventDTO) {
        eventService.addEventToCity(cityId, eventDTO);
        return ResponseEntity.ok("Event added to city successfully");
    }

    // Xóa sự kiện khỏi thành phố
    @DeleteMapping("/city/{cityId}/event/{eventId}")
    public ResponseEntity<String> deleteEventFromCity(@PathVariable Long cityId, @PathVariable Long eventId) {
        String response = eventService.DeleteEventFromCity(cityId, eventId);
        return ResponseEntity.ok(response);
    }

    // Cập nhật sự kiện
    @PutMapping("/update/{eventId}")
    public ResponseEntity<String> updateEvent(@PathVariable Long eventId, @RequestBody EventDTO eventDTO) {
        eventService.UpdateEvent(eventId, eventDTO);
        return ResponseEntity.ok("Event updated successfully");
    }

    // Lấy tất cả sự kiện của một thành phố
    @GetMapping("/city/{cityId}/all")
    public ResponseEntity<List<EventDTO>> getAllEventsByCity(@PathVariable Long cityId) {
        List<EventDTO> events = eventService.GetAllEventByCity(cityId);
        return ResponseEntity.ok(events);
    }

    // Lấy tất cả sự kiện
    @GetMapping("/all")
    public ResponseEntity<List<EventDTO>> getAllEvents() {
        List<EventDTO> events = eventService.GetAllEvent();
        return ResponseEntity.ok(events);
    }
    @GetMapping("Search/{CityId}")
    public ResponseEntity<List<EventDTO>> SearchEvent(@RequestParam(required = false) String DateStart,@RequestParam(required = false) String DateEnd,@PathVariable Long CityId){
        List<EventDTO> events=eventService.SearChEvent(DateStart,DateEnd,CityId);
        return ResponseEntity.ok(events);
    }
}

