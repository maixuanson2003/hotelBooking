package com.example.webHotelBooking.Repository;

import com.example.webHotelBooking.Entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review,Long> {
}
