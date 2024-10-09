package com.example.webHotelBooking.Controller;

import com.example.webHotelBooking.DTO.Response.ReviewSponse;
import com.example.webHotelBooking.Service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    // Đăng bài đánh giá
    @PostMapping("/{hotelId}")
    public ResponseEntity<String> postReview(@PathVariable Long hotelId,
                                             @RequestParam String description,
                                             @RequestParam String username,
                                             @RequestParam int starPoint) {
        String response = reviewService.PosteReview(description, hotelId, username, starPoint);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Xóa một bài đánh giá
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable Long id) {
        String response = reviewService.DeleteComment(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Đặt trạng thái cho bài đánh giá (HIDE/SHOW)
    @PutMapping("/{id}/status")
    public ResponseEntity<String> setStatusComment(@PathVariable Long id,
                                                   @RequestParam String status) {
        reviewService.SetStatusComment(id, status);
        return new ResponseEntity<>("Comment status updated successfully", HttpStatus.OK);
    }

    // Lấy tất cả đánh giá cho một khách sạn
    @GetMapping("/{hotelId}")
    public ResponseEntity<List<ReviewSponse>> getAllReview(@PathVariable Long hotelId) {
        List<ReviewSponse> reviews = reviewService.GetAllReview(hotelId);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }
}
