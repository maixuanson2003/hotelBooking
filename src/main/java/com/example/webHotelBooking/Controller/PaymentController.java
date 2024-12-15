package com.example.webHotelBooking.Controller;

import com.example.webHotelBooking.DTO.Request.PaymentRequest;
import com.example.webHotelBooking.DTO.Response.AlertPayment;
import com.example.webHotelBooking.DTO.Response.PaymentResponse;
import com.example.webHotelBooking.Service.PaymentService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/pay")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    // Tạo thanh toán
    @PostMapping("/create")
    public ResponseEntity<String> createPayment(@RequestBody PaymentRequest paymentRequest, HttpServletRequest req) {
        try {
            String response = paymentService.CreatePayment(paymentRequest, req);
            return ResponseEntity.ok(response);
        } catch (IOException | ParseException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi trong quá trình thanh toán");
        }
    }
    @PostMapping("/create/Change")
    public ResponseEntity<String> createPaymentChange(@RequestBody PaymentRequest paymentRequest, HttpServletRequest req) {
        try {
            String response = paymentService.CreatePaymentChange(paymentRequest, req);
            return ResponseEntity.ok(response);
        } catch (IOException | ParseException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi trong quá trình thanh toán");
        }
    }
    @PostMapping("/Refund")
    public ResponseEntity<AlertPayment> RefundPayments(@RequestParam  Long bookingId, HttpServletRequest req) throws ServletException, IOException, ParseException {
            AlertPayment response = paymentService.RefundPayment(bookingId, req);
            return ResponseEntity.ok(response);
    }

    // Lấy thông tin thanh toán
    @GetMapping("/{bookingId}")
    public ResponseEntity<AlertPayment> getInfoPayment(@PathVariable Long bookingId, HttpServletRequest req) {
        try {
            AlertPayment alertPayment = paymentService.GetInforPayment(bookingId, req);
            return ResponseEntity.ok(alertPayment);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new AlertPayment("Không tìm thấy thông tin thanh toán", "Thất bại"));
        }
    }
    @GetMapping("/Change/{bookingId}")
    public ResponseEntity<AlertPayment> getInfoPaymentChange(@PathVariable Long bookingId, HttpServletRequest req) throws IOException, ParseException {

            AlertPayment alertPayment = paymentService.GetInforPaymentChange(bookingId, req);
            return ResponseEntity.ok(alertPayment);

    }

    // Lấy tất cả thông tin thanh toán
    @GetMapping("/all")
    public ResponseEntity<List<PaymentResponse>> getAllPaymentResponse() {
        List<PaymentResponse> payments = paymentService.GetAllPayMentResponse();
        return ResponseEntity.ok(payments);
    }

    // Lấy tất cả thông tin thanh toán của người dùng
    @GetMapping("/user/{username}")
    public ResponseEntity<List<PaymentResponse>> getAllPaymentByUser(@PathVariable String username) {
        List<PaymentResponse> payments = paymentService.GetAllPayMentByUser(username);
        return ResponseEntity.ok(payments);
    }

}
