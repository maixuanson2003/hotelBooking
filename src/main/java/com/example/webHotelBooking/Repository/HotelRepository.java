package com.example.webHotelBooking.Repository;

import com.example.webHotelBooking.Entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import org.springframework.stereotype.Repository;
@Repository
public interface HotelRepository extends JpaRepository<Hotel,Long> {
    @Query("SELECT h FROM Hotel h WHERE h.City.nameCity = :cityName")
    List<Hotel> findAllByCity(@Param("cityName") String cityName);
    Hotel findByNameHotel(String  nameHotel);
}
