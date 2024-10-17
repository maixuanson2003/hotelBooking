package com.example.webHotelBooking.DTO.Request;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ChatRequest {
    private Long ConversationId;
    private String userSender;
    private String Content;
    private String userRceive;
}