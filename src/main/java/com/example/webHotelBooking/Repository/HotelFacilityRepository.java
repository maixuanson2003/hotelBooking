package com.example.webHotelBooking.Repository;

import com.example.webHotelBooking.Entity.HotelFacility;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
@Repository
public interface HotelFacilityRepository extends JpaRepository<HotelFacility,Long> {
    @Transactional
    @Modifying
    @Query("DELETE FROM HotelFacility hf WHERE :Hotel_Id MEMBER OF hf.Hotel")
    void deleteByHotelId(Long Hotel_Id);
}
