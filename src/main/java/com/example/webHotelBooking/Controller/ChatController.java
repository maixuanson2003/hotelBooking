package com.example.webHotelBooking.Controller;

import com.example.webHotelBooking.DTO.Request.ChatRequest;
import com.example.webHotelBooking.DTO.Response.Chatinfor;
import com.example.webHotelBooking.Service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat/API")
public class ChatController {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private ChatService chatService;
    @PostMapping("/sendMessage")
    public ChatRequest sendMessage(@RequestBody ChatRequest message, @RequestHeader("Authorization") String token){
        String tokens = token.replace("Bearer ", "");
        return chatService.SendMessage(message,tokens);
    }
    @MessageMapping("/realtime/Conversation/{ConversationId}")
    public void sendGroupMessage(@DestinationVariable Integer ConversationId, ChatRequest message) {

       messagingTemplate.convertAndSend("/topic/coversation/"+ConversationId.toString(),message);
    }

    @GetMapping("/conversations")
    public List<Chatinfor> getMessagesByConversation(@RequestHeader("Authorization") String token, @PathVariable Long ConversationId) {
        String tokens = token.replace("Bearer ", "");
        return chatService.GetMessageByconversations(tokens, ConversationId);
    }

    // Xóa tin nhắn theo ID
    @DeleteMapping("/delete/{id}")
    public void deleteMessageById(@PathVariable Long id) {
        chatService.DeleteMessageByid(id);
    }
}
