package com.example.webHotelBooking.Service;

import com.example.webHotelBooking.DTO.Request.actorRequest;
import com.example.webHotelBooking.DTO.Request.actorRequestADmin;
import com.example.webHotelBooking.DTO.Response.actorResponse;

import java.util.List;

public interface UserService {
    public actorResponse UserregisterAcount(actorRequest request);
    public actorResponse CreateActor(actorRequestADmin request);
    public String ChangePassword(String password,String oldPassWord,String username);
    public boolean VerifyInformation(String fullName,String email,String phoneNumber);
    public List<actorResponse> getAllActor();
    public actorResponse searchActor(String fullName,String username,String phone,String email,String birthday,String address);
    public actorResponse getActorById(Long id);
    public String DeleteActorByUsername(String username);
    public String DeleteActorById(Long id);
}
