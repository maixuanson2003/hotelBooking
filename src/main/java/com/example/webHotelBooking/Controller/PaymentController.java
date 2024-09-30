package com.example.webHotelBooking.Controller;

import com.example.webHotelBooking.DTO.Request.PaymentRequest;
import com.example.webHotelBooking.Exception.ResourceNotFoundException;
import com.example.webHotelBooking.Service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;

@RestController
@RequestMapping("/pay")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;
    @PostMapping("/payment")
    public String CreatePayment(@RequestBody PaymentRequest paymentRequest, HttpServletRequest req) throws IOException {
        return paymentService.CreatePayment(paymentRequest,req);
    }
    @GetMapping("/info/{bookingId}")
    public ResponseEntity<String> getPaymentInfo(@PathVariable Long bookingId, HttpServletRequest req) {
        try {
            String paymentInfo = paymentService.GetInforPayment(bookingId, req);
            if (!"invalid".equals(paymentInfo)) {
                return new ResponseEntity<>(paymentInfo, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Invalid payment information", HttpStatus.BAD_REQUEST);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to retrieve payment information", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

}
