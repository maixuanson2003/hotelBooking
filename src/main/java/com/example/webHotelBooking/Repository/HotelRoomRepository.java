package com.example.webHotelBooking.Repository;

import com.example.webHotelBooking.Entity.Hotel;
import com.example.webHotelBooking.Entity.HotelRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface HotelRoomRepository extends JpaRepository<HotelRoom,Long> {
     void deleteByHotel(Hotel hotel);
}
