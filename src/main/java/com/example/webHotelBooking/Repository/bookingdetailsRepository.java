package com.example.webHotelBooking.Repository;

import com.example.webHotelBooking.Entity.bookingdetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface bookingdetailsRepository extends JpaRepository<bookingdetails,Long> {

}
