package com.example.webHotelBooking.Service;

import com.example.webHotelBooking.DTO.Response.ConversationResponse;

import java.util.List;

public interface ConversationService {
    public void CreateConversation(Long userid);
    public ConversationResponse findConversationById(Long id,String token);
    public ConversationResponse findConversationByUserName(String name);
    public List<ConversationResponse> findAllConversation(String token);
    public void DeleteConversationById(Long id);
}
