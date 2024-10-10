package com.example.webHotelBooking.Repository;

import com.example.webHotelBooking.Entity.HotelRoomFeatures;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface HotelRoomFeaturesRepository extends JpaRepository<HotelRoomFeatures,Long> {
    HotelRoomFeatures findByNameFeatures(String nameFeatures);
}
