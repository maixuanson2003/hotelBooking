package com.example.webHotelBooking.DTO.Response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ConversationResponse {
    private Long id;
    private String nameUserRecevive;
    private  List<Chatinfor> chatinfors;
}