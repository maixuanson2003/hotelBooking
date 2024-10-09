package com.example.webHotelBooking.Service.impl;

import com.example.webHotelBooking.DTO.Response.ReviewSponse;
import com.example.webHotelBooking.Entity.Hotel;
import com.example.webHotelBooking.Entity.Review;
import com.example.webHotelBooking.Entity.actor;
import com.example.webHotelBooking.Enums.ReviewStatus;
import com.example.webHotelBooking.Exception.ResourceNotFoundException;
import com.example.webHotelBooking.Repository.HotelRepository;
import com.example.webHotelBooking.Repository.ReviewRepository;
import com.example.webHotelBooking.Repository.userRepository;
import com.example.webHotelBooking.Service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private userRepository userRepository;
    @Autowired
    private HotelRepository hotelRepository;
    @Override
    public String PosteReview(String Description, Long HotelId, String username,int StarPont) {
        Hotel hotel=hotelRepository.findById(HotelId).orElseThrow(()->new ResourceNotFoundException("not found"));
        hotel.setStarPoint(StarPont);
        hotelRepository.save(hotel);
        actor actor=userRepository.findByUsername(username);
        if (actor==null){
            throw new ResourceNotFoundException("not found");
        }
        Review review =new Review().builder()
                .actor(actor)
                .CreatAt(LocalDateTime.now())
                .Description(Description)
                .Hotel(hotel)
                .build();
        reviewRepository.save(review);
        return "success";
    }

    @Override
    public String DeleteComment(Long id) {
        reviewRepository.deleteById(id);
        return "success";
    }

    @Override
    public void SetStatusComment(Long id, String status) {
        Review review=reviewRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("not found"));
        switch (status){
            case "HIDE":
                review.setStatus(ReviewStatus.HIDE.getMessage());
                reviewRepository.save(review);
                break;
            case "SHOW":
                review.setStatus(ReviewStatus.SHOW.getMessage());
                reviewRepository.save(review);
                break;
        }

    }

    @Override
    public List<ReviewSponse> GetAllReview(Long HotelId) {
        Hotel hotel=hotelRepository.findById(HotelId).orElseThrow(()->new ResourceNotFoundException("not found"));
        List<Review> reviewList=hotel.getReviewList();
        List<ReviewSponse> reviewSponseList=new ArrayList<>();
        for (Review review:reviewList){
            ReviewSponse reviewSponse=new ReviewSponse(review);
            reviewSponseList.add(reviewSponse);
        }
        return reviewSponseList;
    }
}
