package com.example.webHotelBooking.Repository;

import com.example.webHotelBooking.Entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment,Long> {
}
