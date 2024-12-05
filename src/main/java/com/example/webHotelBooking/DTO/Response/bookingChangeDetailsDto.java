package com.example.webHotelBooking.DTO.Response;

import com.example.webHotelBooking.Entity.bookingChangeDetails;
import jakarta.persistence.Column;
import lombok.*;

import java.time.LocalDate;
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class bookingChangeDetailsDto {
    private actorResponse actorResponse;
    private Long bookingId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Long Price;
    private String status;
    private boolean Check;
    public  bookingChangeDetailsDto(bookingChangeDetails bookingChangeDetails){
        this.actorResponse=new actorResponse(bookingChangeDetails.getBooking().getActor());
        this.checkInDate=bookingChangeDetails.getCheckInDate();
        this.checkOutDate=bookingChangeDetails.getCheckOutDate();
        this.bookingId=bookingChangeDetails.getBooking().getId();
        this.Price=bookingChangeDetails.getPrice();
        this.status=bookingChangeDetails.getStatus();
        this.Check=bookingChangeDetails.isChecks();
    }
}
