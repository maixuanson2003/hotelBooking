package com.example.webHotelBooking.Repository;

import com.example.webHotelBooking.Entity.HotelImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface HotelImagRepository extends JpaRepository<HotelImage,Long> {
}
