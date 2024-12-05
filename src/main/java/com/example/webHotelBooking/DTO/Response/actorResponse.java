package com.example.webHotelBooking.DTO.Response;

import com.example.webHotelBooking.Entity.actor;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class actorResponse {
    private Long id;
    private String fullName;
    private String username;
    private String phone;
    private String email;
    private String birthday;
    private String address;
    private String gender;
    public actorResponse(actor actor){
        this.id=actor.getId();
        this.fullName=actor.getFullname();
        this. username=actor.getUsername();
        this.phone=actor.getPhone();
        this.email=actor.getEmail();
        this.birthday=actor.getBirthday();
        this.address=actor.getAddress();
        this.gender=actor.getGender();
    }
}
