package com.example.webHotelBooking.Repository;

import com.example.webHotelBooking.Entity.actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface userRepository extends JpaRepository<actor, Long> {
    public actor findByUsername(String username);
}
