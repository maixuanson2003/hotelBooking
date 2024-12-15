package com.example.webHotelBooking.Controller;

import com.example.webHotelBooking.Enums.PaymentStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.webHotelBooking.Entity.*;
import com.example.webHotelBooking.Repository.*;
import com.example.webHotelBooking.Service.*;

import java.util.List;

@RestController
@RequestMapping("/api/DashBoard")
public class AdminDashBoardController {
    @Autowired
    private bookingRepository bookingRepository;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private userRepository userRepository;
    @Autowired
    private bookingChangeDetailsRepository bookingChangeDetailsRepository;
    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private HotelRoomRepository hotelRoomRepository;
     @GetMapping("/amountPayment")
    public ResponseEntity<Long> CountNumberOfPayment(){
         List<Payment> payments=paymentRepository.findAll();
         Long number= 0L;
         for (Payment payment:payments){
             if(payment.getStatus()==null){
                 continue;
             }
             if(payment.getStatus().equals(PaymentStatus.SUCCESS.getMessage())){
                 number+=payment.getTotalPrice();

             }
         }
         return  new ResponseEntity<>(number, HttpStatus.OK);
     }
    @GetMapping("/amountuser")
    public ResponseEntity<Long> CountNumberOfUser(){
        List<actor> users=userRepository.findAll();
        Long number= (long) users.size();
        return  new ResponseEntity<>(number, HttpStatus.OK);
    }

}
