package com.example.webHotelBooking.Repository;

import com.example.webHotelBooking.Entity.booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
public interface bookingRepository extends JpaRepository<booking,Long> {
}
