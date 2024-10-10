package com.example.webHotelBooking.Repository;

import com.example.webHotelBooking.Entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface EventRepository extends JpaRepository<Event,Long> {
    Event findEventByNameEvent(String nameEvent);
}
