package com.example.webHotelBooking.Service;

import com.example.webHotelBooking.DTO.Response.ReviewSponse;
import com.example.webHotelBooking.Entity.Review;

import java.util.List;

public interface ReviewService {
    public String PosteReview(String Description,Long HotelId,String username,int StarPont);
    public String DeleteComment(Long id);
    public void SetStatusComment(Long id,String status);
    public List<ReviewSponse> GetAllReview(Long HotelId);
}
