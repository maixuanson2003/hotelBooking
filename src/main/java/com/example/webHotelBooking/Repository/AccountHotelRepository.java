package com.example.webHotelBooking.Repository;

import com.example.webHotelBooking.Entity.AccountHotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountHotelRepository extends JpaRepository<AccountHotel,Long> {
   AccountHotel findByUsername(String  username);
}
