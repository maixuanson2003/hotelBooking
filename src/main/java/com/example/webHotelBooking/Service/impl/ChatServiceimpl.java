package com.example.webHotelBooking.Service.impl;

import com.example.webHotelBooking.DTO.Request.ChatRequest;
import com.example.webHotelBooking.DTO.Response.Chatinfor;
import com.example.webHotelBooking.Entity.Chat;
import com.example.webHotelBooking.Entity.Conversations;
import com.example.webHotelBooking.Entity.actor;
import com.example.webHotelBooking.Repository.ChatRepository;
import com.example.webHotelBooking.Repository.ConversationRepository;
import com.example.webHotelBooking.Repository.userRepository;
import com.example.webHotelBooking.Service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Service
public class ChatServiceimpl implements ChatService {
    @Autowired
    private final SimpMessagingTemplate messagingTemplate;
    @Autowired
    private ChatRepository chatRepository;
    @Autowired
    private userRepository actorRepository;
    @Autowired
    private JwtDecoder jwtDecoder;
    @Autowired
    private ConversationRepository conversationRepository;

    public ChatServiceimpl(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }
    private actor findByName(String name){
        List<actor> actors=actorRepository.findAll();
        for (actor actor:actors){
            if (actor.getUsername().equals(name)){
                return actor;
            }
        }
        return null;
    }
    @Override
    public ChatRequest SendMessage(ChatRequest message,String token){
        Jwt jwt=jwtDecoder.decode(token);
        Long useridLong = jwt.getClaim("userid");
        actor actors=actorRepository.findById(useridLong).orElseThrow(()->new RuntimeException("notfound"));
        Conversations conversations=conversationRepository.findById(message.getConversationId()).orElseThrow(()->new RuntimeException("sss"));
        Chat chat=new Chat().builder()
                .actor(actors)
                .Content(message.getContent())
                .userSend(message.getUserSender())
                .Conversation(conversations)
                .create_at(LocalDate.now().toString())
                .userRceive(message.getUserRceive())
                .build();
        messagingTemplate.convertAndSend("/topic/coversation/" +  message.getConversationId().toString(), message);
        chatRepository.save(chat);
        return message;
    }

    @Override
    public List<Chatinfor> GetMessageByconversations(String token, Long ConversationId) {
        Jwt jwt=jwtDecoder.decode(token);
        Long userid = jwt.getClaim("userid");
        List<Chatinfor> chatinforList=new ArrayList<>();
        actor actor=actorRepository.findById(userid).orElseThrow(()->new RuntimeException("notfound"));
        List<Conversations> conversationsList=actor.getConservations();
        for (Conversations conversations:conversationsList){
            if (conversations.getId()==ConversationId){
                List<Chat> chatList=conversations.getChats();
                for (Chat chat:chatList){
                    Chatinfor chatinfor=new Chatinfor().builder()
                            .Messageid(chat.getId())
                            .userSender(chat.getUserSend())
                            .Content(chat.getContent())
                            .userRceive(chat.getUserRceive())
                            .build();
                    chatinforList.add(chatinfor);
                }
                break;
            }
        }
        return chatinforList;
    }

    @Override
    public void DeleteMessageByid(Long id) {
        chatRepository.deleteById(id);
    }
    @Scheduled(fixedDelay = 1000)
    private void send() {
        messagingTemplate.convertAndSend("/Topic/Conversation/" +  1, "HeLoo");
    }
}
