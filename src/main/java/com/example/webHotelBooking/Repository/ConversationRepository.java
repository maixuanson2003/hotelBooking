package com.example.webHotelBooking.Repository;

import com.example.webHotelBooking.Entity.Conversations;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConversationRepository extends JpaRepository<Conversations,Long> {
}
