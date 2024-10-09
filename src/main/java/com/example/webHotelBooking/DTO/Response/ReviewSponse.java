package com.example.webHotelBooking.DTO.Response;

import com.example.webHotelBooking.Entity.Review;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewSponse {
    private String username;
    private String Description;
    private LocalDateTime createAt;
    public ReviewSponse(Review review){
        this.username=review.getActor().getUsername();
        this.Description=review.getDescription();
        this.createAt=review.getCreatAt();
    }
}
