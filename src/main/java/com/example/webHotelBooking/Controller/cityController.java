package com.example.webHotelBooking.Controller;

import com.example.webHotelBooking.DTO.Request.cityDTO;
import com.example.webHotelBooking.Service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/city")
public class cityController {
    @Autowired
    public CityService cityService;
    @GetMapping("/GetAll")
    public ResponseEntity<List<cityDTO>> GetAllCity(){
        List<cityDTO> cityDTOList=cityService.GetAllCity();
        return ResponseEntity.ok(cityDTOList);
    }

}
