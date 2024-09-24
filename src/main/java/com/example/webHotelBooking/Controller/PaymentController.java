package com.example.webHotelBooking.Controller;

import com.example.webHotelBooking.DTO.Request.PaymentRequest;
import com.example.webHotelBooking.Service.PaymentService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/pay")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;
    @GetMapping("/payment")
    public String CreatePayment(@RequestBody PaymentRequest paymentRequest, HttpServletRequest req) throws UnsupportedEncodingException {
        return paymentService.CreatePayment(paymentRequest,req);
    }
}
