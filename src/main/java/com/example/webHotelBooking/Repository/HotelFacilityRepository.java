package com.example.webHotelBooking.Repository;

import com.example.webHotelBooking.Entity.HotelFacility;
import com.example.webHotelBooking.Entity.HotelPolicy;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
@Repository
public interface HotelFacilityRepository extends JpaRepository<HotelFacility,Long> {

    HotelFacility findHotelPolicyByNameHotelFacility(String NameHotelFacility);
}
