package com.example.webHotelBooking.Repository;

import com.example.webHotelBooking.Entity.bookingChangeDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface bookingChangeDetailsRepository extends JpaRepository<bookingChangeDetails,Long> {
}
