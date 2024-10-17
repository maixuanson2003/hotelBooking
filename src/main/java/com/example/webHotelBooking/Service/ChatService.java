package com.example.webHotelBooking.Service;

import com.example.webHotelBooking.DTO.Request.ChatRequest;
import com.example.webHotelBooking.DTO.Response.Chatinfor;

import java.util.List;

public interface  ChatService {
    public ChatRequest SendMessage(ChatRequest message, String token);
    public List<Chatinfor> GetMessageByconversations(String token, Long ConversationId);
    public void DeleteMessageByid(Long id);
}
