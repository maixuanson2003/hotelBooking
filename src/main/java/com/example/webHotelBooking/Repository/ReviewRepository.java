package com.example.webHotelBooking.Repository;

import com.example.webHotelBooking.Entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface ReviewRepository extends JpaRepository<Review,Long> {
}
