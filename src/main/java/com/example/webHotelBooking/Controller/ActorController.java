package com.example.webHotelBooking.controller;


import com.example.webHotelBooking.DTO.Request.actorRequest;
import com.example.webHotelBooking.DTO.Request.actorRequestADmin;
import com.example.webHotelBooking.DTO.Response.actorResponse;
import com.example.webHotelBooking.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/actors")
public class ActorController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<actorResponse> registerActor(@RequestBody actorRequest request) {
        actorResponse response = userService.UserregisterAcount(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/create")
    public ResponseEntity<actorResponse> createActor(@RequestBody actorRequestADmin request) {
        actorResponse response = userService.CreateActor(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<actorResponse>> getAllActors() {
        List<actorResponse> responses = userService.getAllActor();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<actorResponse> getActorById(@PathVariable Long id) {
        actorResponse response = userService.getActorById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<actorResponse> searchActor(
            @RequestParam(required = false) String fullName,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String birthday,
            @RequestParam(required = false) String address) {

        actorResponse response = userService.searchActor(fullName, username, phone, email, birthday, address);
        return response != null ? ResponseEntity.ok(response) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/username/{username}")
    public ResponseEntity<String> deleteActorByUsername(@PathVariable String username) {
        String result = userService.DeleteActorByUsername(username);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<String> deleteActorById(@PathVariable Long id) {
        String result = userService.DeleteActorById(id);
        return ResponseEntity.ok(result);
    }
}
