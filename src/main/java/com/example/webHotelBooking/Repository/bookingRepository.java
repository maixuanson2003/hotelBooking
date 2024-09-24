package com.example.webHotelBooking.Repository;

import com.example.webHotelBooking.Entity.booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface bookingRepository extends JpaRepository<booking,Long> {
}
