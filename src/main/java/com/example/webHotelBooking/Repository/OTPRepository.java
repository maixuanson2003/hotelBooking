package com.example.webHotelBooking.Repository;

import com.example.webHotelBooking.Entity.OTP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface OTPRepository extends JpaRepository<OTP,Long> {
}
