package com.example.webHotelBooking.Repository;

import com.example.webHotelBooking.Entity.HotelFacility;
import com.example.webHotelBooking.Entity.HotelFacilityDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelFacilityDetaiRepository extends JpaRepository<HotelFacilityDetails,Long> {
}
