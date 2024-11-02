package com.example.webHotelBooking.Repository;

import com.example.webHotelBooking.Entity.HotelPolicy;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
@Repository
public interface HotelPolicyRepository extends JpaRepository<HotelPolicy,Long> {

    HotelPolicy findHotelPolicyByNamePolicy(String namePolicy);
}
