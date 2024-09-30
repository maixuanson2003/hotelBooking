package com.example.webHotelBooking.Repository;

import com.example.webHotelBooking.Entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event,Long> {
    Event findEventByNameEvent(String nameEvent);
}
