package com.example.webHotelBooking.Repository;

import com.example.webHotelBooking.Entity.saleCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface saleCodeRepository extends JpaRepository<saleCode,Long> {
}
