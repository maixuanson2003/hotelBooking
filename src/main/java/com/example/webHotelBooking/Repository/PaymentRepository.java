package com.example.webHotelBooking.Repository;

import com.example.webHotelBooking.Entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
public interface PaymentRepository extends JpaRepository<Payment,Long> {
}
