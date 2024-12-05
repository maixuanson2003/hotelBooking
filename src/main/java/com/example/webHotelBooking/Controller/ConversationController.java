package com.example.webHotelBooking.Controller;

import com.example.webHotelBooking.DTO.Response.ConversationResponse;
import com.example.webHotelBooking.Service.ConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/conversations/API")

public class ConversationController {
    @Autowired
    private ConversationService conversationService;

    // Tạo một cuộc trò chuyện mới
    @PostMapping("/create")
    public void createConversation(@RequestParam Long userid) {
        conversationService.CreateConversation(userid);
    }

    // Tìm cuộc trò chuyện theo ID
    @GetMapping("/{id}")
    public ResponseEntity<ConversationResponse> findConversationById(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        String tokens = token.replace("Bearer ", "");
        ConversationResponse conversationResponse = conversationService.findConversationById(id,tokens);
        return ResponseEntity.ok(conversationResponse);
    }

    // Tìm tất cả các cuộc trò chuyện của một người dùng
    @GetMapping("/user")
    public ResponseEntity<List<ConversationResponse>> findAllConversations(@RequestHeader("Authorization") String token) {
        String tokens = token.replace("Bearer ", "");
        List<ConversationResponse> conversationResponses = conversationService.findAllConversation(tokens);
        return ResponseEntity.ok(conversationResponses);
    }
    @GetMapping("/user/username")
    public ResponseEntity<ConversationResponse> findConversationByUserName(@RequestParam(name="username") String username) {

        ConversationResponse conversationResponses = conversationService.findConversationByUserName(username);
        return ResponseEntity.ok(conversationResponses);
    }

    // Xóa cuộc trò chuyện theo ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteConversationById(@PathVariable Long id) {
        conversationService.DeleteConversationById(id);
        return ResponseEntity.ok().build();
    }
}
